<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.quizwiz.model.Question" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizWiz - Online Assessment</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <!-- Timer Display -->
        <div class="timer-container">
            <h2>Time Remaining: <span id="timer">10:00</span></h2>
        </div>
        
        <!-- Quiz Header -->
        <div class="quiz-header">
            <h1>QuizWiz Online Assessment</h1>
            <p>Answer all questions and click Submit. The quiz will auto-submit after 10 minutes.</p>
        </div>
        
        <!-- Quiz Form -->
        <form id="quizForm" action="submit" method="POST">
            <%
                List<Question> questions = (List<Question>) request.getAttribute("questions");
                
                if (questions != null && !questions.isEmpty()) {
                    int questionNumber = 1;
                    for (Question question : questions) {
            %>
            
            <!-- Question Card -->
            <div class="question-card">
                <h3>Question <%= questionNumber %>:</h3>
                <p class="question-text"><%= question.getQuestionText() %></p>
                
                <div class="options">
                    <label class="option">
                        <input type="radio" name="answer_<%= question.getId() %>" value="A" required>
                        <span>A. <%= question.getOptionA() %></span>
                    </label>
                    
                    <label class="option">
                        <input type="radio" name="answer_<%= question.getId() %>" value="B">
                        <span>B. <%= question.getOptionB() %></span>
                    </label>
                    
                    <label class="option">
                        <input type="radio" name="answer_<%= question.getId() %>" value="C">
                        <span>C. <%= question.getOptionC() %></span>
                    </label>
                    
                    <label class="option">
                        <input type="radio" name="answer_<%= question.getId() %>" value="D">
                        <span>D. <%= question.getOptionD() %></span>
                    </label>
                </div>
            </div>
            
            <%
                        questionNumber++;
                    }
                } else {
            %>
            
            <div class="error-message">
                <p>No questions available. Please contact the administrator.</p>
            </div>
            
            <%
                }
            %>
            
            <!-- Submit Button -->
            <div class="submit-container">
                <button type="submit" class="submit-btn">Submit Quiz</button>
            </div>
        </form>
    </div>
    
    <!-- JavaScript for Timer -->
    <script src="js/timer.js"></script>
</body>
</html>
