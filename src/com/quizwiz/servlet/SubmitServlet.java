package com.quizwiz.servlet;

import com.quizwiz.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet that handles quiz submission, calculates score by comparing
 * user answers with correct answers, and forwards to result.jsp
 */
@WebServlet("/submit")
public class SubmitServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Handles POST requests for quiz submission
     * Compares user answers with correct answers and calculates score
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            // Establish database connection
            connection = DBConnection.getConnection();
            
            // Fetch correct answers from database
            String sql = "SELECT id, correct_answer FROM questions ORDER BY id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            // Store correct answers in a map (questionId -> correctAnswer)
            Map<Integer, String> correctAnswers = new HashMap<>();
            int totalQuestions = 0;
            
            while (resultSet.next()) {
                int questionId = resultSet.getInt("id");
                String correctAnswer = resultSet.getString("correct_answer");
                correctAnswers.put(questionId, correctAnswer);
                totalQuestions++;
            }
            
            // Calculate score by comparing user answers with correct answers
            int correctCount = 0;
            
            for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
                int questionId = entry.getKey();
                String correctAnswer = entry.getValue();
                
                // Get user's answer from request parameter
                String userAnswer = request.getParameter("answer_" + questionId);
                
                // Compare answers (case-insensitive)
                if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAnswer)) {
                    correctCount++;
                }
            }
            
            // Set score and total as request attributes
            request.setAttribute("score", correctCount);
            request.setAttribute("total", totalQuestions);
            
            // Forward to result.jsp
            request.getRequestDispatcher("/result.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.err.println("Database error while processing quiz submission: " + e.getMessage());
            e.printStackTrace();
            
            // Handle error gracefully
            request.setAttribute("errorMessage", "Unable to process your submission. Please try again.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Error processing quiz submission");
            
        } finally {
            // Clean up resources in reverse order of creation
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }
    }
}
