# QuizWiz - Run Application Guide

## Overview

This guide explains how to set up, build, and run the QuizWiz Online Assessment Platform on your local machine.

---

# Prerequisites

Ensure the following software is installed before running the project.

| Software                | Version                       |
| ----------------------- | ----------------------------- |
| Java JDK                | 17 or later                   |
| Apache Tomcat           | 10.1 or later                 |
| MySQL Server            | 8.0 or later                  |
| Git                     | Latest                        |
| IntelliJ IDEA / Eclipse | Latest                        |
| Web Browser             | Chrome, Firefox, Edge, Safari |

---

# Step 1: Clone the Repository

```bash
git clone https://github.com/Aastha-riya/QuizWiz.git
```

Navigate to the project directory.

```bash
cd QuizWiz
```

---

# Step 2: Create the Database

Start your MySQL server.

Open the MySQL client and create the database.

```sql
CREATE DATABASE quizwiz;
```

---

# Step 3: Import the Database

Import the provided SQL file.

```bash
mysql -u root -p quizwiz < database/quizwiz.sql
```

Verify that the following tables exist.

* Users
* Questions
* Results

---

# Step 4: Configure Database Connection

Open the database configuration file and update the following values.

```properties
db.url=jdbc:mysql://localhost:3306/quizwiz
db.username=root
db.password=your_password
```

Save the file.

---

# Step 5: Add Required Libraries

Ensure the following libraries are available.

* Jakarta Servlet API
* Jakarta JSP API
* JSTL
* MySQL Connector/J

If you are not using Maven, place the required JAR files inside:

```text
WEB-INF/lib/
```

---

# Step 6: Import the Project

Open your IDE.

### IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **Open**.
3. Choose the QuizWiz project folder.
4. Wait for indexing to complete.

### Eclipse

1. Open Eclipse.
2. Select **Import Existing Project**.
3. Choose the QuizWiz directory.
4. Finish the import.

---

# Step 7: Build the Project

## Windows

Run:

```bat
build.bat
```

## Linux

```bash
chmod +x build.sh
./build.sh
```

## macOS

```bash
chmod +x build.sh
./build.sh
```

A successful build creates the build output without compilation errors.

---

# Step 8: Configure Apache Tomcat

Locate your Tomcat installation.

Example locations

Windows

```text
C:\Program Files\Apache Software Foundation\Tomcat 10.1
```

Linux

```text
/opt/tomcat
```

macOS

```text
/usr/local/tomcat
```

Copy the application (or generated WAR file) into the Tomcat `webapps` directory.

---

# Step 9: Start Tomcat

## Windows

```bat
startup.bat
```

## Linux

```bash
./startup.sh
```

## macOS

```bash
./startup.sh
```

Wait until Tomcat finishes starting.

---

# Step 10: Open the Application

Open your browser and navigate to:

```text
http://localhost:8080/QuizWiz/
```

---

# Application Workflow

1. Register a new user.
2. Log in with your credentials.
3. Start the quiz.
4. Answer all questions.
5. Monitor the countdown timer.
6. Submit the quiz or allow auto-submit.
7. View your score and performance.

---

# Features to Verify

## Authentication

* User registration
* Login
* Logout
* Session handling

---

## Quiz

* Questions load correctly
* Radio button selection
* Countdown timer
* Progress bar
* Automatic submission
* Manual submission

---

## Results

* Score displayed
* Percentage calculated
* Performance message shown

---

## Error Handling

Verify custom pages.

* 404 Not Found
* 500 Internal Server Error

---

# Troubleshooting

## Java Not Found

Verify Java installation.

```bash
java -version
```

If Java is not found, configure the `JAVA_HOME` environment variable.

---

## MySQL Connection Failed

Check:

* MySQL service is running.
* Database exists.
* Username and password are correct.
* JDBC URL is correct.

---

## Tomcat Not Starting

Check:

* Port 8080 is available.
* Correct Tomcat version is installed.
* `JAVA_HOME` is configured.

View the Tomcat logs for details.

---

## CSS or JavaScript Not Loading

Verify:

* Files exist in the `webapp/css` and `webapp/js` directories.
* Static resource paths use the correct context path.
* Browser cache is cleared.

---

## Compilation Errors

Verify:

* Java 17+ is installed.
* Required libraries are present.
* Project dependencies are correctly configured.

---

# Build Scripts

Windows

```bat
build.bat
```

Linux / macOS

```bash
./build.sh
```

---

# Default URL

```text
http://localhost:8080/QuizWiz/
```

---

# Recommended Browser

* Google Chrome (Recommended)
* Mozilla Firefox
* Microsoft Edge
* Safari

---

# Useful Documentation

The repository includes:

* `README.md`
* `DESIGN.md`
* `REQUIREMENTS.md`
* `IMPLEMENTATION_PLAN.md`
* `DEPLOYMENT_CHECKLIST.md`
* `RUN_APP.md`

---

# Quick Start

```text
Clone Repository
        │
        ▼
Create Database
        │
        ▼
Import SQL Schema
        │
        ▼
Configure Database Credentials
        │
        ▼
Add Required Libraries
        │
        ▼
Build Project
        │
        ▼
Deploy to Apache Tomcat
        │
        ▼
Start Tomcat
        │
        ▼
Open:
http://localhost:8080/QuizWiz/
```

---

# Expected Result

If everything is configured correctly:

* The application opens successfully.
* Users can register and log in.
* Quiz questions load from the database.
* The timer starts automatically.
* Quiz submission works correctly.
* Results are calculated instantly.
* Custom error pages appear when appropriate.

Congratulations! 🎉 QuizWiz is now ready to use.
