# Requirements Document

## Introduction

QuizWiz is an Online Assessment Platform built as a Dynamic Web Project using Core Java, Servlets, JSP, and JDBC without any frameworks. The system allows users to take timed quizzes with multiple-choice questions and receive immediate scoring feedback.

## Glossary

- **System**: The QuizWiz Online Assessment Platform
- **Quiz_Engine**: The backend component that manages quiz logic and scoring
- **Database_Layer**: The JDBC-based data access component
- **Timer_Component**: The JavaScript-based countdown timer in the UI
- **Question**: A multiple-choice question with four options and one correct answer
- **User**: A person taking the quiz through the web interface

## Requirements

### Requirement 1: Database Schema and Sample Data

**User Story:** As a system administrator, I want a properly structured database with sample questions, so that the quiz system has data to work with immediately.

#### Acceptance Criteria

1. THE System SHALL create a database named `quizwiz_db`
2. THE System SHALL create a `questions` table with columns: `id` (INT, PRIMARY KEY, AUTO_INCREMENT), `question_text` (VARCHAR(500)), `option_a` (VARCHAR(200)), `option_b` (VARCHAR(200)), `option_c` (VARCHAR(200)), `option_d` (VARCHAR(200)), `correct_answer` (VARCHAR(1))
3. THE System SHALL insert at least 5 sample technical questions covering Java and Data Structures topics
4. WHEN the database is queried, THE System SHALL return all questions with their options and correct answers

### Requirement 2: Database Connection Management

**User Story:** As a developer, I want a reusable database connection utility, so that all servlets can reliably connect to MySQL.

#### Acceptance Criteria

1. THE Database_Layer SHALL provide a utility class that establishes JDBC connections to MySQL
2. THE Database_Layer SHALL use PreparedStatements for all database queries
3. IF a database connection fails, THEN THE Database_Layer SHALL throw a descriptive exception
4. THE Database_Layer SHALL properly close database resources (connections, statements, result sets)

### Requirement 3: Quiz Display and Question Retrieval

**User Story:** As a user, I want to see all quiz questions on a single page, so that I can answer them in sequence.

#### Acceptance Criteria

1. WHEN a user navigates to the quiz page, THE Quiz_Engine SHALL fetch all questions from the database
2. THE Quiz_Engine SHALL store questions as a request attribute
3. THE Quiz_Engine SHALL forward the request to the quiz JSP page
4. THE System SHALL display each question with its four options as radio buttons within a form
5. THE System SHALL display questions in a clean, card-based layout

### Requirement 4: Countdown Timer

**User Story:** As a quiz administrator, I want a 10-minute countdown timer, so that quizzes have a time limit.

#### Acceptance Criteria

1. WHEN the quiz page loads, THE Timer_Component SHALL start a countdown from 10 minutes (600 seconds)
2. THE Timer_Component SHALL display the remaining time in MM:SS format at the top of the page
3. WHEN the timer reaches 00:00, THE Timer_Component SHALL automatically submit the quiz form
4. THE Timer_Component SHALL update the display every second

### Requirement 5: Answer Submission and Scoring

**User Story:** As a user, I want to submit my answers and receive a score, so that I know how well I performed.

#### Acceptance Criteria

1. WHEN a user submits the quiz form, THE Quiz_Engine SHALL receive all selected answers
2. THE Quiz_Engine SHALL retrieve correct answers from the database
3. THE Quiz_Engine SHALL compare each user answer with the correct answer
4. THE Quiz_Engine SHALL calculate the total score as correct answers divided by total questions
5. THE Quiz_Engine SHALL forward the score to the result page

### Requirement 6: Result Display

**User Story:** As a user, I want to see my final score after submission, so that I understand my performance.

#### Acceptance Criteria

1. WHEN the quiz is submitted, THE System SHALL display the final score in the format "You scored X/Y"
2. THE System SHALL provide a "Try Again" button that redirects to the quiz page
3. THE System SHALL display the result in a visually clear format

### Requirement 7: Error Handling

**User Story:** As a developer, I want proper exception handling, so that the system fails gracefully.

#### Acceptance Criteria

1. WHEN a database error occurs, THE System SHALL catch SQLException and provide meaningful error messages
2. WHEN a servlet encounters an error, THE System SHALL log the exception details
3. IF a required resource is unavailable, THEN THE System SHALL display an appropriate error page

### Requirement 8: Project Structure

**User Story:** As a developer, I want a standard Dynamic Web Project structure, so that the project follows Java EE conventions.

#### Acceptance Criteria

1. THE System SHALL organize Java source files in the `src` directory
2. THE System SHALL place web resources (JSP, HTML, CSS, JS) in the `webapp` directory
3. THE System SHALL configure servlets in `WEB-INF/web.xml`
4. THE System SHALL place compiled classes in `WEB-INF/classes`
5. THE System SHALL store library dependencies in `WEB-INF/lib`

### Requirement 9: User Interface Styling

**User Story:** As a user, I want a modern and clean interface, so that the quiz is pleasant to use.

#### Acceptance Criteria

1. THE System SHALL use a card layout for displaying questions
2. THE System SHALL highlight the Submit button with a distinct color
3. THE System SHALL use responsive CSS that works on different screen sizes
4. THE System SHALL provide visual feedback for interactive elements (hover states)
5. THE System SHALL maintain consistent spacing and typography throughout
