@echo off
REM QuizWiz Build Script for Windows

echo ========================================
echo QuizWiz Build Script
echo ========================================
echo.

REM Create classes directory if it doesn't exist
if not exist "webapp\WEB-INF\classes" (
    echo Creating classes directory...
    mkdir webapp\WEB-INF\classes
)

REM Compile Java files
echo Compiling Java source files...
javac -cp "webapp\WEB-INF\lib\*" -d webapp\WEB-INF\classes src\com\quizwiz\model\*.java src\com\quizwiz\util\*.java src\com\quizwiz\servlet\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Build Successful!
    echo ========================================
    echo.
    echo Compiled classes are in: webapp\WEB-INF\classes
    echo.
    echo Next steps:
    echo 1. Ensure MySQL is running
    echo 2. Run database\schema.sql in MySQL
    echo 3. Deploy webapp folder to Tomcat
    echo 4. Access: http://localhost:8080/QuizWiz/quiz
    echo.
) else (
    echo.
    echo ========================================
    echo Build Failed!
    echo ========================================
    echo.
    echo Please check:
    echo 1. JDK is installed and in PATH
    echo 2. MySQL connector JAR is in webapp\WEB-INF\lib\
    echo 3. Source files have no syntax errors
    echo.
)

pause
