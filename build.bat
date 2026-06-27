```bat
@echo off
setlocal EnableDelayedExpansion

:: ============================================================
:: QuizWiz Build Script (Windows)
:: Java Servlet + JSP + JDBC + MySQL
:: ============================================================

echo.
echo ==========================================
echo        QuizWiz Build Script
echo ==========================================
echo.

:: -----------------------------
:: Configuration
:: -----------------------------
set PROJECT_NAME=QuizWiz
set SRC_DIR=src
set WEB_DIR=webapp
set BUILD_DIR=build
set CLASSES_DIR=%BUILD_DIR%\classes
set LIB_DIR=WEB-INF\lib

:: Set your Tomcat installation path here
set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.1

:: -----------------------------
:: Check Java
:: -----------------------------
echo Checking Java...

java -version >nul 2>&1

if errorlevel 1 (
    echo.
    echo ERROR: Java is not installed or not added to PATH.
    pause
    exit /b
)

echo Java detected.
echo.

:: -----------------------------
:: Clean previous build
:: -----------------------------
echo Cleaning previous build...

if exist %BUILD_DIR% (
    rmdir /S /Q %BUILD_DIR%
)

mkdir %BUILD_DIR%
mkdir %CLASSES_DIR%

echo Build directory created.
echo.

:: -----------------------------
:: Compile Java files
:: -----------------------------
echo Compiling Java source files...

dir /s /b %SRC_DIR%\*.java > sources.txt

javac ^
-classpath "%TOMCAT_HOME%\lib\servlet-api.jar;%LIB_DIR%\*" ^
-d %CLASSES_DIR% ^
@sources.txt

if errorlevel 1 (
    echo.
    echo BUILD FAILED!
    del sources.txt
    pause
    exit /b
)

del sources.txt

echo.
echo Compilation Successful.
echo.

:: -----------------------------
:: Copy Web Resources
:: -----------------------------
echo Copying web resources...

xcopy %WEB_DIR% %BUILD_DIR% /E /I /Y >nul

:: Copy compiled classes

xcopy %CLASSES_DIR% %BUILD_DIR%\WEB-INF\classes /E /I /Y >nul

echo Resources copied.
echo.

:: -----------------------------
:: Build Summary
:: -----------------------------
echo ==========================================
echo Build Successful
echo ==========================================
echo.
echo Project : %PROJECT_NAME%
echo Output  : %BUILD_DIR%
echo Classes : %CLASSES_DIR%
echo.
echo Deploy the contents of:
echo.
echo    build\
echo.
echo into your Tomcat webapps directory.
echo.

pause
```
