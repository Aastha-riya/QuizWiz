<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuizWiz - Results</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="result-container">
            <!-- Result Header -->
            <div class="result-header">
                <h1>Quiz Completed!</h1>
                <div class="result-icon">
                    <%
                        Integer score = (Integer) request.getAttribute("score");
                        Integer total = (Integer) request.getAttribute("total");
                        
                        if (score != null && total != null) {
                            double percentage = (score * 100.0) / total;
                            
                            if (percentage >= 80) {
                                out.print("🎉");
                            } else if (percentage >= 60) {
                                out.print("👍");
                            } else {
                                out.print("📚");
                            }
                        }
                    %>
                </div>
            </div>
            
            <!-- Score Display -->
            <div class="score-display">
                <%
                    if (score != null && total != null) {
                        double percentage = (score * 100.0) / total;
                %>
                
                <h2 class="score-text">You scored <%= score %>/<%= total %></h2>
                <p class="percentage-text"><%= String.format("%.1f", percentage) %>%</p>
                
                <!-- Performance Message -->
                <div class="performance-message">
                    <%
                        if (percentage >= 80) {
                    %>
                        <p class="excellent">Excellent work! You have a strong understanding of the concepts.</p>
                    <%
                        } else if (percentage >= 60) {
                    %>
                        <p class="good">Good job! Keep practicing to improve further.</p>
                    <%
                        } else {
                    %>
                        <p class="needs-improvement">Keep learning! Review the topics and try again.</p>
                    <%
                        }
                    %>
                </div>
                
                <%
                    } else {
                %>
                
                <div class="error-message">
                    <p>Unable to calculate score. Please try again.</p>
                </div>
                
                <%
                    }
                %>
            </div>
            
            <!-- Action Buttons -->
            <div class="action-buttons">
                <a href="quiz" class="btn btn-primary">Try Again</a>
                <a href="quiz" class="btn btn-secondary">Back to Quiz</a>
            </div>
        </div>
    </div>
</body>
</html>
