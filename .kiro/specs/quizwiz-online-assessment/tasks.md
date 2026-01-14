# Implementation Plan: QuizWiz Online Assessment Platform

## Overview

This implementation plan breaks down the QuizWiz project into discrete coding tasks. Each task builds incrementally on previous work, starting with database setup, then backend components, and finally frontend integration. The project uses Core Java, Servlets, JSP, JDBC, and MySQL without any frameworks.

## Tasks

- [x] 1. Set up project structure and database
  - Create standard Dynamic Web Project directory structure (src, webapp, WEB-INF)
  - Create package structure: com.quizwiz.util, com.quizwiz.servlet, com.quizwiz.model
  - Create SQL schema file with database and table definitions
  - Insert 5 sample technical questions (Java/Data Structures topics)
  - _Requirements: 1.1, 1.2, 1.3, 8.1, 8.2, 8.3_

- [ ] 2. Implement database connection utility
  - [x] 2.1 Create DBConnection.java utility class
    - Implement getConnection() method with JDBC connection to MySQL
    - Use try-catch blocks for SQLException handling
    - Configure connection URL, driver, username, password
    - _Requirements: 2.1, 2.2, 2.3, 7.1_

  - [ ] 2.2 Write unit tests for DBConnection
    - Test successful connection with valid credentials
    - Test connection failure with invalid credentials
    - Test proper exception handling
    - _Requirements: 2.3, 7.1_

- [x] 3. Create Question model class
  - Create Question.java with fields: id, questionText, optionA, optionB, optionC, optionD, correctAnswer
  - Implement constructor, getters, and setters
  - Add toString() method for debugging
  - _Requirements: 3.1, 3.2_

- [ ] 4. Implement QuizServlet for question retrieval
  - [x] 4.1 Create QuizServlet.java
    - Implement doGet() method to handle GET requests
    - Use DBConnection to get database connection
    - Execute PreparedStatement: "SELECT * FROM questions"
    - Map ResultSet to List<Question> objects
    - Set questions as request attribute
    - Forward request to quiz.jsp
    - Implement proper resource cleanup in finally block
    - _Requirements: 3.1, 3.2, 3.3, 2.2, 2.4_

  - [ ] 4.2 Write unit tests for QuizServlet
    - Test question retrieval with mock database
    - Test empty result set handling
    - Test exception handling
    - _Requirements: 3.1, 7.2_

  - [ ] 4.3 Write property test for question retrieval completeness
    - **Property 2: Question Retrieval Completeness**
    - **Validates: Requirements 3.1**
    - Generate random number of questions in test database
    - Verify retrieved count matches database count
    - _Requirements: 3.1_

- [ ] 5. Implement SubmitServlet for answer processing
  - [x] 5.1 Create SubmitServlet.java
    - Implement doPost() method to handle POST requests
    - Extract answer parameters from request (answer_1, answer_2, etc.)
    - Use DBConnection to get database connection
    - Execute PreparedStatement: "SELECT id, correct_answer FROM questions ORDER BY id"
    - Compare user answers with correct answers
    - Calculate score (correctCount / totalQuestions)
    - Set score and total as request attributes
    - Forward request to result.jsp
    - Implement proper resource cleanup in finally block
    - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5, 2.2, 2.4_

  - [ ] 5.2 Write unit tests for SubmitServlet
    - Test score calculation with all correct answers
    - Test score calculation with all wrong answers
    - Test score calculation with partial correct answers
    - Test handling of missing answer parameters
    - _Requirements: 5.3, 5.4, 7.2_

  - [ ] 5.3 Write property test for score calculation accuracy
    - **Property 3: Score Calculation Accuracy**
    - **Validates: Requirements 5.3, 5.4**
    - Generate random combinations of user answers and correct answers
    - Verify calculated score equals expected score
    - _Requirements: 5.3, 5.4_

  - [ ] 5.4 Write property test for answer comparison correctness
    - **Property 5: Answer Comparison Correctness**
    - **Validates: Requirements 5.3**
    - Generate random question IDs and answers
    - Verify answer marked correct only when it matches database value
    - _Requirements: 5.3_

- [x] 6. Configure web.xml deployment descriptor
  - Define servlet mappings for QuizServlet (/quiz) and SubmitServlet (/submit)
  - Set welcome-file to quiz.jsp
  - Configure error pages for 404 and 500 errors
  - _Requirements: 8.3_

- [ ] 7. Create quiz.jsp for question display
  - [x] 7.1 Implement quiz.jsp structure
    - Create HTML form with action="/submit" and method="POST"
    - Use JSTL or scriptlet to iterate through questions attribute
    - For each question, create a card div with question text
    - Create radio button group for each question (name="answer_${questionId}")
    - Add Submit button at the end of form
    - Include link to style.css
    - Include script tag for timer.js
    - Add div for timer display at top of page
    - _Requirements: 3.4, 3.5, 4.1_

  - [ ] 7.2 Write integration test for quiz.jsp rendering
    - Test that all questions are displayed
    - Test that radio buttons are properly named
    - Test that form action is correct
    - _Requirements: 3.4, 3.5_

- [ ] 8. Implement JavaScript countdown timer
  - [x] 8.1 Create timer.js with countdown functionality
    - Initialize timer to 600 seconds (10 minutes)
    - Implement countdown function that decrements every second
    - Format time as MM:SS and update display element
    - When timer reaches 00:00, call document.getElementById("quizForm").submit()
    - Start timer on window.onload
    - _Requirements: 4.1, 4.2, 4.3, 4.4_

  - [ ] 8.2 Write unit tests for timer logic
    - Test time formatting (e.g., 599 seconds → "09:59")
    - Test countdown decrement
    - Test auto-submit trigger at 00:00
    - _Requirements: 4.2, 4.3, 4.4_

- [x] 9. Create result.jsp for score display
  - Create HTML structure to display score from request attributes
  - Show message: "You scored ${score}/${total}"
  - Add "Try Again" button that links to /quiz
  - Include link to style.css
  - _Requirements: 6.1, 6.2, 6.3_

- [ ] 10. Implement CSS styling
  - [x] 10.1 Create style.css with modern design
    - Define card layout for questions (border, padding, margin, box-shadow)
    - Style radio buttons and labels for better UX
    - Style Submit button with distinct background color and hover effect
    - Style timer display (large font, prominent position)
    - Add responsive design rules for mobile devices
    - Style result page with centered content
    - Ensure consistent typography and spacing
    - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_

  - [ ] 10.2 Manual testing of UI styling
    - Verify card layout displays correctly
    - Verify Submit button is highlighted
    - Verify responsive design on different screen sizes
    - Verify timer display is prominent
    - _Requirements: 9.1, 9.2, 9.3, 9.4_

- [x] 11. Add MySQL connector dependency
  - Download mysql-connector-java-8.0.x.jar
  - Place JAR file in webapp/WEB-INF/lib directory
  - _Requirements: 2.1_

- [ ] 12. Checkpoint - Integration testing
  - Deploy project to Tomcat server
  - Create quizwiz_db database and run schema.sql
  - Test complete flow: Navigate to /quiz → Answer questions → Submit → View results
  - Verify timer countdown and auto-submit functionality
  - Verify score calculation is accurate
  - Test error handling by stopping MySQL server
  - Ensure all tests pass, ask the user if questions arise
  - _Requirements: All_

- [ ] 13. Write property test for resource cleanup
  - **Property 6: Resource Cleanup**
  - **Validates: Requirements 2.4, 7.1**
  - Simulate various database operations (success and failure scenarios)
  - Verify all JDBC resources are properly closed
  - _Requirements: 2.4, 7.1_

- [x] 14. Final review and documentation
  - Review all code for proper exception handling
  - Verify all PreparedStatements are used (no string concatenation)
  - Add code comments for complex logic
  - Create README with setup instructions
  - Document database configuration steps
  - _Requirements: 2.2, 7.1, 7.2_

## Notes

- All tasks are required for comprehensive implementation
- Each task references specific requirements for traceability
- Checkpoints ensure incremental validation
- Property tests validate universal correctness properties
- Unit tests validate specific examples and edge cases
- The project requires Apache Tomcat and MySQL to be installed and running
- MySQL connector JAR must be compatible with the installed MySQL version
