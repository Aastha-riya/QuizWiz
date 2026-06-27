package com.quizwiz.util;

import com.quizwiz.exception.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection utility for the QuizWiz application.
 *
 * <h3>Configuration</h3>
 * All credentials are read from environment variables — no secrets in source code.
 * <pre>
 *   DB_URL      JDBC connection URL
 *               MySQL example:      jdbc:mysql://localhost:3306/quizwiz_db?useSSL=false&serverTimezone=UTC
 *               PostgreSQL example: jdbc:postgresql://localhost:5432/quizwiz_db
 *   DB_USER     Database username   (e.g. root)
 *   DB_PASSWORD Database password
 * </pre>
 * Local fallback defaults are provided so development works without setting env vars.
 * On production (Render, Railway, etc.) set the three env vars and no code changes are needed.
 *
 * <h3>Multi-database support</h3>
 * The JDBC driver is resolved automatically from the DB_URL prefix.
 * Supported: MySQL, PostgreSQL. Extend {@link #resolveDriver(String)} for others.
 *
 * <h3>Future upgrade path — HikariCP (Version 2)</h3>
 * Replace {@code DriverManager.getConnection()} with a {@code HikariDataSource}:
 * <pre>
 *   HikariConfig config = new HikariConfig();
 *   config.setJdbcUrl(DB_URL);
 *   config.setUsername(DB_USER);
 *   config.setPassword(DB_PASSWORD);
 *   HikariDataSource dataSource = new HikariDataSource(config);
 * </pre>
 * All servlet code stays the same since both return {@code java.sql.Connection}.
 *
 * @version 2.0
 */
public final class DBConnection {

    // ── Version ──────────────────────────────────────────────────────────────
    private static final String VERSION = "QuizWiz Database v2.0";

    // ── Timeouts ──────────────────────────────────────────────────────────────
    private static final int LOGIN_TIMEOUT      = 10; // seconds to wait for a connection
    private static final int VALIDATION_TIMEOUT =  5; // seconds to validate an existing connection

    // ── Private constructor — utility class, not instantiable ─────────────────
    private DBConnection() {
        throw new UnsupportedOperationException("Utility class");
    }

    // ── Logger ────────────────────────────────────────────────────────────────
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    // ── Credentials from environment variables with local fallbacks ───────────
    private static final String DB_URL      = buildDbUrl();
    private static final String DB_USER     = getEnv("DB_USER",     "root");
    private static final String DB_PASSWORD = getEnv("DB_PASSWORD", "");
    private static final String DB_DRIVER   = resolveDriver(DB_URL);

    /**
     * Builds the final JDBC URL.
     * Uses DB_URL env var directly if set (caller is responsible for SSL params).
     * Falls back to a local default with SSL controlled by DB_USE_SSL env var.
     */
    private static String buildDbUrl() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.trim().isEmpty()) {
            return envUrl; // production: caller owns the full URL including SSL params
        }
        // Local fallback — SSL off by default, overridable via DB_USE_SSL=true
        String useSSL = "true".equalsIgnoreCase(System.getenv("DB_USE_SSL")) ? "true" : "false";
        return "jdbc:mysql://localhost:3306/quizwiz_db?useSSL=" + useSSL + "&serverTimezone=UTC";
    }

    // ── Static initialiser — runs once at class load ───────────────────────────
    static {
        LOGGER.info(VERSION);

        // Load driver once — fail fast if JAR is missing
        try {
            Class.forName(DB_DRIVER);
            DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
            LOGGER.info("JDBC Driver loaded: " + DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "JDBC Driver not found: " + DB_DRIVER, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Returns a new database connection from the configured data source.
     *
     * Usage — always inside try-with-resources:
     * <pre>
     *   try (Connection conn = DBConnection.getConnection()) {
     *       // use conn
     *   }
     * </pre>
     *
     * @return an open {@link Connection}
     * @throws DatabaseConnectionException if the connection cannot be established
     */
    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            LOGGER.info("Connected to: " + DB_URL);
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection", e);
            throw new DatabaseConnectionException("Failed to connect to the database", e);
        }
    }

    /**
     * Tests whether the database is reachable and logs product/version/driver info.
     * Useful for admin dashboards and health-check endpoints.
     *
     * @return {@code true} if a connection can be opened, {@code false} otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            boolean valid = connection != null && connection.isValid(VALIDATION_TIMEOUT);
            if (valid) {
                java.sql.DatabaseMetaData meta = connection.getMetaData();
                LOGGER.info("Database health check: Connected"
                        + " | Product: "  + meta.getDatabaseProductName()
                        + " | Version: "  + meta.getDatabaseProductVersion()
                        + " | Driver: "   + meta.getDriverName()
                        + " "             + meta.getDriverVersion());
            } else {
                LOGGER.warning("Database health check: Unreachable");
            }
            return valid;
        } catch (SQLException | DatabaseConnectionException e) {
            LOGGER.log(Level.WARNING, "Database health check failed", e);
            return false;
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Returns the env var value, or {@code defaultValue} if not set.
     */
    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    /**
     * Resolves the JDBC driver class name from the DB_URL prefix.
     * Add entries here when supporting additional databases.
     *
     * @param url the JDBC connection URL
     * @return fully-qualified driver class name
     */
    private static String resolveDriver(String url) {
        if (url == null)                           return "com.mysql.cj.jdbc.Driver";
        if (url.startsWith("jdbc:mysql"))          return "com.mysql.cj.jdbc.Driver";
        if (url.startsWith("jdbc:mariadb"))        return "org.mariadb.jdbc.Driver";
        if (url.startsWith("jdbc:postgresql"))     return "org.postgresql.Driver";
        // extend here: jdbc:oracle, jdbc:sqlserver, etc.
        LOGGER.warning("Unrecognized DB_URL scheme — defaulting to MySQL driver: " + url);
        return "com.mysql.cj.jdbc.Driver";
    }
}
