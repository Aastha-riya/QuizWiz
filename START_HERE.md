# 🚀 QuizWiz - Start Here

Welcome to **QuizWiz**, a modern online assessment platform built using **Java Servlets, JSP, JSTL, JDBC, MySQL, HTML5, CSS3, and JavaScript**.

If this is your first time using the project, follow the steps below.

---

# 📌 What is QuizWiz?

QuizWiz is a Java web application that allows users to:

* Register and log in
* Attempt timed quizzes
* View instant results
* Experience a responsive and modern user interface
* Automatically submit quizzes when the timer expires

The project follows the **MVC (Model-View-Controller)** architecture for better maintainability and scalability.

---

# 🛠 Prerequisites

Before running the project, make sure you have:

* Java JDK 17 or later
* Apache Tomcat 10.1 or later
* MySQL 8.0 or later
* IntelliJ IDEA or Eclipse
* Git (optional)

---

# ⚡ Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/QuizWiz.git
```

---

### 2. Create the Database

```sql
CREATE DATABASE quizwiz;
```

---

### 3. Import the Database

```bash
mysql -u root -p quizwiz < database/quizwiz.sql
```

---

### 4. Configure Database Credentials

Update your database configuration with:

* Database URL
* Username
* Password

---

### 5. Build the Project

**Windows**

```bat
build.bat
```

**Linux / macOS**

```bash
chmod +x build.sh
./build.sh
```

---

### 6. Deploy to Apache Tomcat

Copy the application (or generated WAR file) into the Tomcat `webapps` directory and start the server.

---

### 7. Open the Application

```text
http://localhost:8080/QuizWiz/
```

---

# 📖 Documentation

The repository contains detailed documentation.

| File                      | Description                                   |
| ------------------------- | --------------------------------------------- |
| `README.md`               | Project overview and features                 |
| `DESIGN.md`               | System architecture and design decisions      |
| `REQUIREMENTS.md`         | Software and hardware requirements            |
| `IMPLEMENTATION_PLAN.md`  | Development phases and implementation details |
| `DEPLOYMENT_CHECKLIST.md` | Deployment checklist                          |
| `RUN_APP.md`              | Step-by-step guide to run the application     |

---

# ✨ Features

* User Registration
* User Login
* Session Management
* Dynamic Quiz Generation
* Countdown Timer
* Auto Submission
* Progress Bar
* Responsive Design
* Modern UI
* Result Analysis
* Custom 404 Error Page
* Custom 500 Error Page
* Browser Compatibility
* Accessibility Improvements

---

# 🏗 Technology Stack

### Backend

* Java
* Jakarta Servlets
* JSP
* JSTL
* JDBC

### Frontend

* HTML5
* CSS3
* JavaScript

### Database

* MySQL

### Server

* Apache Tomcat

---

# 📂 Project Structure

```text
QuizWiz/
├── src/
├── webapp/
├── database/
├── README.md
├── START_HERE.md
├── DESIGN.md
├── REQUIREMENTS.md
├── IMPLEMENTATION_PLAN.md
├── DEPLOYMENT_CHECKLIST.md
├── RUN_APP.md
├── build.bat
├── build.sh
└── .gitignore
```

---

# 🧪 Verify Everything Works

After launching the application, verify that you can:

* Register a new account
* Log in successfully
* Start a quiz
* Answer questions
* Observe the countdown timer
* Submit the quiz
* View the result page
* Access custom 404 and 500 error pages

---

# 📌 Need Help?

If you encounter any issues:

1. Check **RUN_APP.md** for setup instructions.
2. Review **DEPLOYMENT_CHECKLIST.md** to verify your configuration.
3. Ensure Java, MySQL, and Tomcat are installed and configured correctly.

---

## 🎉 You're Ready!

Once the application opens at:

```text
http://localhost:8080/QuizWiz/
```

you're ready to explore QuizWiz and its features. Happy coding!
