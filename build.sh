```bash
#!/bin/bash

# ============================================================
# QuizWiz Build Script
# Linux / macOS
# Java Servlet + JSP + JDBC + MySQL
# ============================================================

set -e

echo ""
echo "=========================================="
echo "        QuizWiz Build Script"
echo "=========================================="
echo ""

# ------------------------------------------------------------
# Configuration
# ------------------------------------------------------------

PROJECT_NAME="QuizWiz"

SRC_DIR="src"
WEB_DIR="webapp"

BUILD_DIR="build"
CLASSES_DIR="$BUILD_DIR/classes"

LIB_DIR="WEB-INF/lib"

# ------------------------------------------------------------
# Configure your Tomcat installation
# ------------------------------------------------------------

TOMCAT_HOME="/opt/tomcat"

# Example macOS:
# TOMCAT_HOME="/usr/local/Cellar/tomcat/10.1.0/libexec"

# ------------------------------------------------------------
# Check Java
# ------------------------------------------------------------

echo "Checking Java..."

if ! command -v java >/dev/null 2>&1; then
    echo ""
    echo "ERROR: Java is not installed."
    exit 1
fi

echo "Java detected."

echo ""

# ------------------------------------------------------------
# Clean previous build
# ------------------------------------------------------------

echo "Cleaning previous build..."

rm -rf "$BUILD_DIR"

mkdir -p "$CLASSES_DIR"

echo "Build directory created."

echo ""

# ------------------------------------------------------------
# Find Java source files
# ------------------------------------------------------------

find "$SRC_DIR" -name "*.java" > sources.txt

# ------------------------------------------------------------
# Compile
# ------------------------------------------------------------

echo "Compiling Java sources..."

javac \
-classpath "$TOMCAT_HOME/lib/servlet-api.jar:$LIB_DIR/*" \
-d "$CLASSES_DIR" \
@sources.txt

rm sources.txt

echo ""
echo "Compilation Successful."

echo ""

# ------------------------------------------------------------
# Copy Web Resources
# ------------------------------------------------------------

echo "Copying web resources..."

cp -R "$WEB_DIR" "$BUILD_DIR"

mkdir -p "$BUILD_DIR/WEB-INF/classes"

cp -R "$CLASSES_DIR/"* "$BUILD_DIR/WEB-INF/classes/" 2>/dev/null || true

echo "Resources copied."

echo ""

# ------------------------------------------------------------
# Build Summary
# ------------------------------------------------------------

echo "=========================================="
echo "        BUILD SUCCESSFUL"
echo "=========================================="

echo ""
echo "Project : $PROJECT_NAME"
echo "Output  : $BUILD_DIR"
echo ""

echo "Deploy the contents of:"
echo ""
echo "    build/"
echo ""
echo "to your Apache Tomcat webapps directory."
echo ""
echo "Done."
```
