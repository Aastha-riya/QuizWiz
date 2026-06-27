

# QuizWiz - Requirements

## Overview

This document describes the software, hardware, development tools, libraries, dependencies, and system requirements needed to build, deploy, and run the QuizWiz Online Assessment Platform.

---

# 1. Functional Requirements

The system shall provide the following functionalities:

## User Management

* User registration
* User login
* User logout
* Session management

---

## Quiz Management

* Load quiz questions from the database
* Display one or more multiple-choice questions
* Submit quiz answers
* Automatically submit when the timer expires
* Calculate quiz score
* Display final result

---

## Timer

* Countdown timer
* Progress bar
* Warning state
* Critical state
* Automatic submission

---

## Result Module

* Display score
* Display percentage
* Performance evaluation
* Retry quiz

---

## Error Handling

* Custom 404 page
* Custom 500 page
* Development mode debugging
* Error reference generation

---

# 2. Non-Functional Requirements

## Performance

* Fast page loading
* Smooth animations
* Efficient database queries
* Responsive user interface

---

## Security

* Session-based authentication
* Prepared SQL statements
* Input validation
* Output escaping using JSTL
* Protected server-side validation

---

## Reliability

* Proper exception handling
* Graceful error pages
* Automatic quiz submission
* Duplicate submission prevention

---

## Usability

* Responsive layout
* Easy navigation
* Modern UI
* Interactive feedback

---

## Maintainability

* MVC architecture
* Modular Java code
* Reusable CSS
* Organized project structure

---

# 3. Software Requirements

## Operating System

Supported operating systems

* Windows 10 or later
* Windows 11
* Ubuntu Linux
* macOS

---

## Java

* Java Development Kit (JDK) 17 or higher

---

## IDE

Recommended IDEs

* IntelliJ IDEA (Recommended)
* Eclipse IDE
* Apache NetBeans

---

## Application Server

* Apache Tomcat 10.1+

---

## Database

* MySQL 8.0+

---

## Build Tool

Current

* Manual build using IDE

Optional

* Maven
* Gradle

---

## Version Control

* Git
* GitHub

---

# 4. Frontend Requirements

Technologies

* HTML5
* CSS3
* JavaScript (ES6)

Libraries

* Google Fonts (Poppins)

Browser Support

* Google Chrome
* Mozilla Firefox
* Microsoft Edge
* Opera
* Safari

---

# 5. Backend Requirements

Technologies

* Java
* Jakarta Servlet API
* JSP
* JSTL
* JDBC

---

# 6. Database Requirements

Database Server

* MySQL

Required Tables

## Users

Stores registered users.

Fields include

* ID
* Username
* Email
* Password

---

## Questions

Stores quiz questions.

Fields include

* Question
* Option A
* Option B
* Option C
* Option D
* Correct Answer

---

## Results

Stores quiz attempts.

Fields include

* User ID
* Score
* Total Marks
* Submission Time

---

# 7. Hardware Requirements

## Minimum

Processor

* Dual Core CPU

Memory

* 4 GB RAM

Storage

* 2 GB free space

Network

* Internet connection for deployment and GitHub

---

## Recommended

Processor

* Intel Core i5 or equivalent

Memory

* 8 GB RAM or higher

Storage

* SSD

---

# 8. Project Structure

```text
QuizWiz/
│
├── src/
│   ├── controller/
│   ├── dao/
│   ├── model/
│   ├── util/
│   └── service/
│
├── webapp/
│   ├── css/
│   ├── js/
│   ├── images/
│   ├── WEB-INF/
│   ├── index.jsp
│   ├── quiz.jsp
│   ├── result.jsp
│   ├── error404.jsp
│   └── error500.jsp
│
├── database/
│
├── README.md
├── DESIGN.md
├── REQUIREMENTS.md
└── LICENSE
```

---

# 9. External Dependencies

The project requires the following libraries:

* Jakarta Servlet API
* Jakarta JSP API
* JSTL
* MySQL Connector/J

These JAR files should be included in the application's `WEB-INF/lib` directory or managed using Maven.

---

# 10. Browser Features Used

The application uses modern browser features including:

* CSS Variables
* Flexbox
* Media Queries
* CSS Animations
* CSS Transitions
* JavaScript DOM API
* HTML5 Form Validation

JavaScript provides compatibility fallbacks where necessary (such as browsers without CSS `:has()` support).

---

# 11. Security Requirements

Current implementation includes:

* Session-based authentication
* Prepared SQL statements
* Input validation
* JSTL output escaping
* Duplicate submission prevention
* Server-side quiz validation
* Error handling without exposing sensitive information

Recommended future enhancements:

* BCrypt password hashing
* CSRF protection
* Security headers
* HTTPS deployment
* Role-based access control

---

# 12. Deployment Requirements

Deployment environment:

* Apache Tomcat 10.1+
* Java 17+
* MySQL 8.0+

Deployment steps:

1. Clone the repository.
2. Import the project into IntelliJ IDEA or Eclipse.
3. Configure the MySQL database.
4. Import the SQL schema.
5. Update database credentials.
6. Add required libraries.
7. Deploy to Apache Tomcat.
8. Start the server.
9. Access the application in a web browser.

---

# 13. Development Standards

The project follows:

* MVC Architecture
* Object-Oriented Programming (OOP)
* Clean Code principles
* Responsive Web Design
* Separation of Concerns
* Modular CSS organization

---

# 14. Future Requirements

Potential future enhancements include:

* Admin dashboard
* Role-based authorization
* Question categories
* Difficulty levels
* Leaderboards
* Quiz history
* User profile management
* Email verification
* Password reset
* PDF result reports
* REST API integration
* Docker support
* Cloud deployment
* Analytics dashboard

---

# Summary

QuizWiz requires a Java development environment with Apache Tomcat, MySQL, and modern web technologies. The application has been designed to be modular, responsive, secure, and maintainable while following the MVC architectural pattern. These requirements provide a solid foundation for both academic learning and future production-level enhancements.
