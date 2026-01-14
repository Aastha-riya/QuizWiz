# QuizWiz - Online Assessment Platform

A Java-based web application for conducting timed online quizzes.

## Tech Stack
- Java Servlets, JSP, JDBC
- MySQL Database
- Apache Tomcat Server
- HTML5, CSS3, JavaScript

## Quick Start

### 1. Setup Database
```cmd
mysql -u root -p < database\schema.sql
```

### 2. Build Project
```cmd
build.bat
```

### 3. Deploy to Tomcat
Copy `webapp` folder to `C:\apache-tomcat-9.0.x\webapps\QuizWiz`

### 4. Start Tomcat
```cmd
cd C:\apache-tomcat-9.0.x\bin
startup.bat
```

### 5. Access Application
Open browser: `http://localhost:8080/QuizWiz/quiz`

## Features
- 5 technical questions (Java/Data Structures)
- 10-minute countdown timer with auto-submit
- Automatic score calculation
- Modern, responsive UI

## Correct Answers (for testing)
1. B (O(log n))
2. B (final)
3. B (Stack)
4. C (HashSet)
5. C (O(n²))

## Project Structure
```
QuizWiz/
├── src/                    # Java source files
│   └── com/quizwiz/
│       ├── model/          # Question.java
│       ├── servlet/        # QuizServlet, SubmitServlet
│       └── util/           # DBConnection
├── webapp/                 # Web application
│   ├── WEB-INF/
│   │   ├── web.xml        # Configuration
│   │   └── lib/           # MySQL connector JAR
│   ├── css/               # Styles
│   ├── js/                # Timer script
│   ├── quiz.jsp           # Quiz page
│   └── result.jsp         # Results page
├── database/              # schema.sql
└── build.bat              # Build script
```

## Troubleshooting

**Database Error**
- Check MySQL is running
- Verify password in DBConnection.java

**Build Error**
- Verify Java installed: `java -version`
- Check JAVA_HOME is set

**404 Error**
- Verify folder named "QuizWiz" in webapps
- Wait 15 seconds after starting Tomcat

**ClassNotFoundException**
- Verify mysql-connector JAR in webapp\WEB-INF\lib\
