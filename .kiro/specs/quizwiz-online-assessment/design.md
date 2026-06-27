# Design Document: QuizWiz Online Assessment Platform

## Project Overview

QuizWiz is a modern Java-based online assessment platform developed using **Java Servlets, JSP, JSTL, JDBC, MySQL, HTML5, CSS3, and JavaScript**. The application provides a secure, responsive, and user-friendly environment where users can register, log in, attempt timed quizzes, and instantly view their results.

The project follows the **Model–View–Controller (MVC)** architecture to ensure proper separation of concerns, maintainability, and scalability.

---

# Design Objectives

The primary goals of QuizWiz are:

* Develop a clean and responsive online assessment platform.
* Follow Java EE best practices using MVC architecture.
* Separate presentation, business logic, and database operations.
* Provide an engaging user experience through modern UI design.
* Ensure secure authentication and reliable quiz submission.
* Create reusable, maintainable, and scalable code.

---

# System Architecture

QuizWiz follows the MVC architecture.

```text
                    User
                      │
                      ▼
                 JSP Pages
              (Presentation Layer)
                      │
                      ▼
             Java Servlets (Controller)
                      │
                      ▼
             Business Logic Layer
                      │
                      ▼
                 DAO (JDBC Layer)
                      │
                      ▼
                MySQL Database
```

---

# MVC Components

## Model Layer

Responsible for representing application data.

Models include:

* User
* Question
* Result

Responsibilities

* Store entity data
* Transfer information between layers
* Represent database tables

---

## View Layer

Implemented using JSP and JSTL.

Pages include

* Login
* Register
* Quiz
* Result
* 404 Error
* 500 Error

Responsibilities

* Display information
* Collect user input
* Render quiz questions
* Display results
* Handle user interaction

---

## Controller Layer

Implemented using Servlets.

Controllers include

* LoginServlet
* RegisterServlet
* QuizServlet
* SubmitServlet
* LogoutServlet

Responsibilities

* Handle HTTP requests
* Validate user input
* Manage user sessions
* Call DAO methods
* Forward responses to JSP pages

---

# Database Design

## Users Table

| Field    | Type    |
| -------- | ------- |
| id       | INT     |
| username | VARCHAR |
| email    | VARCHAR |
| password | VARCHAR |

---

## Questions Table

| Field          | Type    |
| -------------- | ------- |
| id             | INT     |
| question_text  | TEXT    |
| option_a       | VARCHAR |
| option_b       | VARCHAR |
| option_c       | VARCHAR |
| option_d       | VARCHAR |
| correct_answer | CHAR(1) |

---

## Results Table

| Field        | Type      |
| ------------ | --------- |
| id           | INT       |
| user_id      | INT       |
| score        | INT       |
| total        | INT       |
| submitted_at | TIMESTAMP |

---

# User Interface Design

QuizWiz uses a modern card-based interface.

Design highlights include:

* Responsive layout
* Rounded cards
* Soft shadows
* Modern typography
* Animated gradient background
* Smooth transitions
* Hover animations
* Glassmorphism timer
* Interactive buttons
* Responsive forms

---

# CSS Design

The stylesheet has been organized into reusable sections.

## CSS Variables

Reusable variables include

* Primary color
* Secondary color
* Warning color
* Danger color
* Success color
* Surface color
* Shadow styles

Benefits

* Consistent styling
* Easier maintenance
* Future theme support

---

## Typography

Font Family

* Google Poppins

Reasons

* Improved readability
* Modern appearance
* Professional interface

---

## Animations

Implemented animations include

* Animated gradient background
* Fade-in page animation
* Floating quiz header
* Button hover animation
* Ripple button effect
* Card hover animation
* Timer pulse animation
* Critical timer flash
* Progress bar animation

Animations improve user engagement while maintaining smooth performance.

---

# Responsive Design

The application is optimized for

* Desktop
* Laptop
* Tablet
* Mobile

Responsive improvements

* Flexible containers
* Responsive buttons
* Optimized spacing
* Mobile typography
* Responsive error pages

---

# Quiz Design

Each quiz page contains

* Countdown timer
* Animated progress bar
* Dynamic question rendering
* Multiple-choice options
* Loading state
* Automatic submission
* Submit button protection

Questions are loaded dynamically from the database.

---

# Timer Design

The timer is implemented using JavaScript.

Features include

* Countdown timer
* Animated progress bar
* Warning mode
* Critical mode
* Flash animation
* Automatic submission
* Timer banner support

Server-side validation is also performed using the hidden quiz start timestamp.

---

# Result Page Design

The result page was redesigned using JSTL and EL.

Features

* Dynamic score calculation
* Percentage formatting
* Performance messages
* Dynamic result icons
* Responsive layout
* Error handling

Performance categories

* Excellent
* Good
* Needs Improvement

---

# Error Handling Design

## Custom 404 Page

Features

* Custom astronaut SVG illustration
* Requested URL display
* Navigation shortcuts
* Responsive design
* Professional styling

Navigation options

* Home
* Login
* Register
* Start Quiz

---

## Custom 500 Page

Features

* Error reference ID
* Timestamp
* Retry button
* Contact support section
* Development-mode stack trace
* Secure production mode

Stack traces remain hidden in production to avoid exposing sensitive server information.

---

# Security Design

Current implementation

* Session-based authentication
* JSTL output escaping using `<c:out>`
* Client-side validation
* Server-side validation
* Duplicate submission prevention
* Development mode protection
* Error reference IDs
* Hidden quiz start timestamp
* Prepared SQL statements

Future enhancements

* BCrypt password hashing
* CSRF protection
* Role-based authorization
* Security headers
* HTTPS enforcement
* Rate limiting

---

# Accessibility

Accessibility improvements include

* ARIA attributes for progress bar
* Keyboard-friendly controls
* Reduced motion support
* Focus states
* Semantic HTML structure
* Responsive typography

---

# Browser Compatibility

Implemented compatibility improvements

* JavaScript fallback for browsers without CSS `:has()` support
* Graceful degradation
* Responsive media queries

---

# Performance Optimizations

Implemented optimizations

* CSS variables
* Reusable components
* Lightweight SVG graphics
* Organized stylesheets
* Optimized transitions
* Reduced duplicated CSS

---

# Code Quality Improvements

The project has been progressively modernized.

Changes include

* Reduced JSP scriptlets
* Migration to JSTL
* Cleaner page structure
* Better code organization
* Reusable CSS architecture
* Modular JavaScript

---

# Quiz Workflow

```text
User Login
      │
      ▼
Load Quiz
      │
      ▼
Start Timer
      │
      ▼
Answer Questions
      │
      ▼
Submit Quiz
      │
      ▼
Server Validation
      │
      ▼
Score Calculation
      │
      ▼
Display Result
```

If the timer reaches zero, the quiz is submitted automatically.

---

# Error Workflow

```text
Unexpected Error
        │
        ▼
Generate Error ID
        │
        ▼
Log Exception
        │
        ▼
Display Custom Error Page
        │
        ▼
Retry or Contact Support
```

---

# Technologies Used

## Backend

* Java
* Jakarta Servlet
* JSP
* JSTL
* JDBC

## Frontend

* HTML5
* CSS3
* JavaScript

## Database

* MySQL

## Server

* Apache Tomcat

## Development Tools

* IntelliJ IDEA
* Git
* GitHub

---

# Design Decisions

## Why MVC?

* Better separation of concerns
* Easier maintenance
* Improved scalability
* Cleaner project structure

---

## Why JSTL?

* Cleaner JSP pages
* Reduced Java code
* Improved security
* Better readability

---

## Why CSS Variables?

* Consistent design
* Easier maintenance
* Simplified theme customization

---

## Why Glassmorphism?

* Modern visual appeal
* Improved UI aesthetics
* Better user engagement

---

## Why Custom Error Pages?

* Better user experience
* Professional appearance
* Easier debugging
* Improved navigation

---

# Future Enhancements

Planned features include

* Admin Dashboard
* Question Management
* User Management
* Password Reset
* Email Verification
* Quiz Categories
* Difficulty Levels
* Leaderboard
* Quiz History
* Detailed Result Analysis
* Downloadable PDF Certificates
* User Profile
* Dark Mode
* REST API
* Docker Deployment
* Cloud Deployment
* Analytics Dashboard

---

# Conclusion

QuizWiz has evolved from a basic online quiz application into a modern Java web application that emphasizes clean architecture, responsive design, maintainable code, accessibility, security, and user experience. The use of the MVC architecture, modern CSS techniques, JSTL, responsive layouts, custom error handling, and an interactive quiz interface makes the application suitable as a portfolio-quality project while providing a solid foundation for future enhancements and enterprise-level features.
