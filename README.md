# 🎓 QuizWiz - Online Assessment Platform

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge\&logo=openjdk)
![JSP](https://img.shields.io/badge/JSP-Jakarta-blue?style=for-the-badge)
![Servlet](https://img.shields.io/badge/Servlet-Jakarta-red?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=for-the-badge\&logo=mysql)
![Tomcat](https://img.shields.io/badge/Tomcat-10.1+-yellow?style=for-the-badge\&logo=apachetomcat)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

*A modern online quiz and assessment platform built using Java Servlets, JSP, JDBC, MySQL, HTML5, CSS3, and JavaScript.*

</div>

---

# 📖 Overview

QuizWiz is a full-stack Java web application that enables users to register, log in, participate in timed quizzes, and instantly receive their scores and performance analysis.

The project follows the **Model–View–Controller (MVC)** architecture, ensuring clean code organization, scalability, and maintainability. It combines a responsive user interface with secure backend processing to provide a professional online assessment experience.

---

# ✨ Features

## Authentication

* User Registration
* Secure Login
* Logout
* Session Management

---

## Quiz Module

* Dynamic quiz loading
* Multiple-choice questions
* Countdown timer
* Automatic quiz submission
* Timer progress bar
* Server-side validation
* Loading state during submission

---

## Result Module

* Instant score calculation
* Percentage calculation
* Performance feedback
* Dynamic result icons
* Responsive result page

---

## Modern User Interface

* Responsive design
* Animated gradient background
* Glassmorphism timer
* Interactive cards
* Hover animations
* Ripple buttons
* Loading animations
* Mobile-friendly layout

---

## Error Handling

* Custom 404 page
* Custom 500 page
* Error reference IDs
* Development-mode debugging
* User-friendly navigation

---

## Security Features

* Session-based authentication
* Prepared SQL statements
* Input validation
* Output escaping with JSTL
* Duplicate submission prevention
* Secure production error handling

---

# 🛠 Technology Stack

## Backend

* Java
* Jakarta Servlets
* JSP
* JSTL
* JDBC

## Frontend

* HTML5
* CSS3
* JavaScript (ES6)

## Database

* MySQL

## Web Server

* Apache Tomcat 10.1+

## Development Tools

* IntelliJ IDEA
* Git
* GitHub

---

# 🏗 Architecture

The application follows the **MVC (Model–View–Controller)** architecture.

```text
User
 │
 ▼
JSP Pages (View)
 │
 ▼
Servlets (Controller)
 │
 ▼
Business Logic
 │
 ▼
DAO Layer (JDBC)
 │
 ▼
MySQL Database
```

---

# 📂 Project Structure

```text
QuizWiz/
│
├── src/
│   ├── controller/
│   ├── dao/
│   ├── model/
│   ├── service/
│   └── util/
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
├── IMPLEMENTATION_PLAN.md
├── DEPLOYMENT_CHECKLIST.md
├── LICENSE
├── build.bat
├── build.sh
└── .gitignore
```

---

# 🚀 Installation

## Clone Repository

```bash
git clone https://github.com/your-username/QuizWiz.git
```

---

## Configure Database

Create a MySQL database.

```sql
CREATE DATABASE quizwiz;
```

Import the SQL schema.

```bash
mysql -u root -p quizwiz < database/quizwiz.sql
```

---

## Configure Database Connection

Update your database configuration with:

* Database URL
* Username
* Password

---

## Run the Application

### Windows

```bat
build.bat
```

### Linux/macOS

```bash
chmod +x build.sh
./build.sh
```

Deploy the application to **Apache Tomcat** and start the server.

Open:

```text
http://localhost:8080/QuizWiz/
```

---

# 📱 Screenshots

Add screenshots after deployment.

```
Home Page

Login Page

Registration Page

Quiz Page

Timer

Result Page

404 Page

500 Page
```

---

# 🎯 Highlights

* Modern responsive UI
* MVC architecture
* Dynamic quiz generation
* Animated countdown timer
* Automatic submission
* Responsive cards
* Professional error pages
* JSTL-based JSP pages
* Modular CSS architecture
* Browser compatibility improvements
* Accessibility enhancements
* Mobile-first design

---

# 📊 Current Features

| Module                | Status |
| --------------------- | ------ |
| User Registration     | ✅      |
| Login                 | ✅      |
| Logout                | ✅      |
| Session Management    | ✅      |
| Dynamic Quiz          | ✅      |
| Timer                 | ✅      |
| Auto Submit           | ✅      |
| Result Calculation    | ✅      |
| Responsive UI         | ✅      |
| 404 Page              | ✅      |
| 500 Page              | ✅      |
| Accessibility         | ✅      |
| Browser Compatibility | ✅      |

---

# 🔒 Security

Current implementation includes:

* Session authentication
* Prepared SQL statements
* Server-side validation
* JSTL output escaping
* Error reference IDs
* Duplicate submission prevention

Planned improvements:

* BCrypt password hashing
* CSRF protection
* Role-based authorization
* HTTPS deployment

---

# 📈 Future Enhancements

* Admin Dashboard
* Question Management
* User Management
* Quiz Categories
* Difficulty Levels
* Leaderboard
* Quiz History
* Email Verification
* Password Reset
* PDF Certificates
* REST API
* Docker Support
* Cloud Deployment
* Analytics Dashboard
* Dark Mode

---

# 📚 Documentation

The repository includes:

* **README.md** — Project overview and setup
* **DESIGN.md** — Architecture and design decisions
* **REQUIREMENTS.md** — Functional and technical requirements
* **IMPLEMENTATION_PLAN.md** — Development phases
* **DEPLOYMENT_CHECKLIST.md** — Deployment guide

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository.
2. Create a new branch.
3. Commit your changes.
4. Push the branch.
5. Open a Pull Request.

---

# 📄 License

This project is licensed under the MIT License.

---

# 👩‍💻 Author

**Aastha Gupta**

B.Tech – Computer Science Engineering (AI & ML)

---

<div align="center">

⭐ If you found this project useful, consider giving it a star!

</div>
