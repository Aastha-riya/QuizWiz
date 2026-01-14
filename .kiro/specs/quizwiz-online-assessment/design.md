# Design Document: QuizWiz Online Assessment Platform

## Overview

QuizWiz is a servlet-based web application that provides an online quiz platform with timed assessments. The system follows a traditional Java EE architecture using Servlets for request handling, JSP for view rendering, and JDBC for database access. The application runs on Apache Tomcat and uses MySQL for data persistence.

## Architecture

The application follows a Model-View-Controller (MVC) pattern:

- **Model**: Question data objects and database access layer
- **View**: JSP pages for quiz display and results
- **Controller**: Servlets that handle HTTP requests and coordinate between model and view

### Component Diagram

```
┌─────────────┐
│   Browser   │
└──────┬──────┘
       │ HTTP Request
       ▼
┌─────────────────────────────────┐
│      Apache Tomcat Server       │
│  ┌───────────────────────────┐  │
│  │   QuizServlet (GET)       │  │
│  │   - Fetch questions       │  │
│  │   - Forward to quiz.jsp   │  │
│  └───────────┬───────────────┘  │
│              │                   │
│  ┌───────────▼───────────────┐  │
│  │   SubmitServlet (POST)    │  │
│  │   - Process answers       │  │
│  │   - Calculate score       │  │
│  │   - Forward to result.jsp │  │
│  └───────────┬───────────────┘  │
│              │                   │
│  ┌───────────▼───────────────┐  │
│  │   DBConnection Utility    │  │
│  │   - JDBC connection pool  │  │
│  │   - PreparedStatements    │  │
│  └───────────┬───────────────┘  │
└──────────────┼───────────────────┘
               │ JDBC
               ▼
        ┌─────────────┐
        │    MySQL    │
        │ quizwiz_db  │
        └─────────────┘
```

## Components and Interfaces

### 1. Database Layer

**DBConnection.java**
- **Purpose**: Provides JDBC connection management
- **Methods**:
  - `getConnection()`: Returns a Connection object to MySQL database
- **Configuration**:
  - URL: `jdbc:mysql://localhost:3306/quizwiz_db`
  - Driver: `com.mysql.cj.jdbc.Driver`
  - Credentials: Configurable username/password

### 2. Controller Layer

**QuizServlet.java**
- **URL Pattern**: `/quiz`
- **HTTP Method**: GET
- **Responsibilities**:
  - Fetch all questions from database
  - Create Question objects
  - Set questions as request attribute
  - Forward to quiz.jsp
- **Key Methods**:
  - `doGet(HttpServletRequest, HttpServletResponse)`: Main handler

**SubmitServlet.java**
- **URL Pattern**: `/submit`
- **HTTP Method**: POST
- **Responsibilities**:
  - Extract user answers from request parameters
  - Fetch correct answers from database
  - Compare answers and calculate score
  - Set score as request attribute
  - Forward to result.jsp
- **Key Methods**:
  - `doPost(HttpServletRequest, HttpServletResponse)`: Main handler

### 3. View Layer

**quiz.jsp**
- **Purpose**: Display quiz questions with timer
- **Features**:
  - Form with radio buttons for each question
  - JavaScript countdown timer (10 minutes)
  - Auto-submit on timer expiration
  - Styled with CSS for card layout

**result.jsp**
- **Purpose**: Display quiz results
- **Features**:
  - Show score (e.g., "You scored 4/5")
  - "Try Again" button linking back to quiz

**style.css**
- **Purpose**: Provide modern, clean styling
- **Features**:
  - Card-based question layout
  - Responsive design
  - Highlighted submit button
  - Timer display styling

## Data Models

### Question Model

```java
class Question {
    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer; // 'A', 'B', 'C', or 'D'
    
    // Getters and setters
}
```

### Database Schema

**Table: questions**

| Column          | Type         | Constraints                    |
|-----------------|--------------|--------------------------------|
| id              | INT          | PRIMARY KEY, AUTO_INCREMENT    |
| question_text   | VARCHAR(500) | NOT NULL                       |
| option_a        | VARCHAR(200) | NOT NULL                       |
| option_b        | VARCHAR(200) | NOT NULL                       |
| option_c        | VARCHAR(200) | NOT NULL                       |
| option_d        | VARCHAR(200) | NOT NULL                       |
| correct_answer  | VARCHAR(1)   | NOT NULL, CHECK IN ('A','B','C','D') |

### Request/Response Flow

**Quiz Display Flow:**
1. User requests `/quiz`
2. QuizServlet.doGet() executes
3. Query: `SELECT * FROM questions`
4. Results mapped to List<Question>
5. Request attribute: `questions`
6. Forward to quiz.jsp
7. JSP renders form with questions

**Quiz Submission Flow:**
1. User submits form to `/submit`
2. SubmitServlet.doPost() executes
3. Extract parameters: `answer_1`, `answer_2`, etc.
4. Query: `SELECT id, correct_answer FROM questions`
5. Compare user answers with correct answers
6. Calculate: `score = correctCount / totalQuestions`
7. Request attributes: `score`, `total`
8. Forward to result.jsp
9. JSP displays results

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system—essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

### Property 1: Database Connection Validity

*For any* database connection request, if the MySQL server is running and credentials are correct, then the connection should be successfully established and return a non-null Connection object.

**Validates: Requirements 2.1, 2.3**

### Property 2: Question Retrieval Completeness

*For any* state of the questions table, when QuizServlet fetches questions, the number of Question objects in the request attribute should equal the number of rows in the questions table.

**Validates: Requirements 3.1**

### Property 3: Score Calculation Accuracy

*For any* set of user answers and correct answers, the calculated score should equal the count of matching answers divided by the total number of questions.

**Validates: Requirements 5.3, 5.4**

### Property 4: Timer Auto-Submit

*For any* quiz session, when the countdown timer reaches 00:00, the quiz form should be automatically submitted without user interaction.

**Validates: Requirements 4.3**

### Property 5: Answer Comparison Correctness

*For any* question ID and user answer, the answer should be marked correct if and only if it matches the correct_answer value in the database for that question ID.

**Validates: Requirements 5.3**

### Property 6: Resource Cleanup

*For any* database operation, all JDBC resources (Connection, PreparedStatement, ResultSet) should be properly closed in a finally block or try-with-resources, regardless of whether the operation succeeds or fails.

**Validates: Requirements 2.4, 7.1**

## Error Handling

### Database Errors

- **SQLException**: Catch and log with descriptive messages
- **Connection failures**: Display user-friendly error page
- **Query failures**: Log stack trace and return empty result set with error flag

### Servlet Errors

- **Missing parameters**: Validate request parameters and provide defaults
- **Invalid data**: Validate input format before processing
- **Forward failures**: Log error and send HTTP 500 response

### Exception Handling Pattern

```java
Connection conn = null;
PreparedStatement stmt = null;
ResultSet rs = null;
try {
    conn = DBConnection.getConnection();
    stmt = conn.prepareStatement(sql);
    rs = stmt.executeQuery();
    // Process results
} catch (SQLException e) {
    e.printStackTrace();
    // Log error and handle gracefully
} finally {
    if (rs != null) try { rs.close(); } catch (SQLException e) { }
    if (stmt != null) try { stmt.close(); } catch (SQLException e) { }
    if (conn != null) try { conn.close(); } catch (SQLException e) { }
}
```

## Testing Strategy

### Unit Testing

The testing strategy will focus on specific examples and edge cases:

- **DBConnection**: Test successful connection, failed connection with wrong credentials
- **QuizServlet**: Test question retrieval with empty database, with multiple questions
- **SubmitServlet**: Test score calculation with all correct, all wrong, partial correct
- **Timer**: Test countdown display, auto-submit trigger
- **Edge Cases**: Empty question text, special characters in options, null answers

### Property-Based Testing

Property-based tests will validate universal correctness properties using a Java PBT library (e.g., jqwik or QuickTheories):

- **Minimum 100 iterations per property test**
- Each test tagged with: `Feature: quizwiz-online-assessment, Property N: [property text]`
- Properties to test:
  - Score calculation accuracy across random answer combinations
  - Question retrieval completeness with varying database states
  - Resource cleanup verification across different error scenarios

### Integration Testing

- End-to-end flow: Quiz display → Answer selection → Submission → Result display
- Database integration: Verify JDBC operations against actual MySQL instance
- Servlet integration: Test request/response handling with mock HTTP objects

### Manual Testing

- Visual verification of UI styling and responsiveness
- Timer countdown accuracy and auto-submit behavior
- Cross-browser compatibility (Chrome, Firefox, Edge)

## Implementation Notes

### Project Structure

```
QuizWiz/
├── src/
│   └── com/
│       └── quizwiz/
│           ├── util/
│           │   └── DBConnection.java
│           └── servlet/
│               ├── QuizServlet.java
│               └── SubmitServlet.java
├── webapp/
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   ├── classes/
│   │   └── lib/
│   │       └── mysql-connector-java-8.0.x.jar
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   └── timer.js
│   ├── quiz.jsp
│   └── result.jsp
└── database/
    └── schema.sql
```

### Deployment Configuration

**web.xml Configuration:**
- Servlet mappings for QuizServlet and SubmitServlet
- Welcome file: quiz.jsp
- Error page mappings for 404, 500

**Tomcat Configuration:**
- Context path: `/QuizWiz`
- Port: 8080
- MySQL connector JAR in WEB-INF/lib

### Security Considerations

- **SQL Injection Prevention**: Use PreparedStatements exclusively
- **Input Validation**: Validate all request parameters
- **Session Management**: Consider adding session tracking for future enhancements
- **Database Credentials**: Store in configuration file, not hardcoded

## Future Enhancements

- User authentication and session management
- Question categories and difficulty levels
- Randomized question order
- Detailed answer review after submission
- Admin panel for question management
- Multiple quiz support
- Score history and analytics
