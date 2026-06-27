package com.quizwiz.exception;

/**
 * Thrown when the application fails to establish a database connection.
 * Extends RuntimeException so callers are not forced to declare it,
 * while still carrying the original cause for debugging.
 */
public class DatabaseConnectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs exception with a descriptive message.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs exception with a message and the underlying cause.
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
