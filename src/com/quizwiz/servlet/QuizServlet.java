package com.quizwiz.servlet;

import com.quizwiz.model.Question;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet that handles quiz display by fetching questions from database
 * and forwarding to quiz.jsp for rendering.
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Handles GET requests to display the quiz
     * Fetches all questions from database and forwards to quiz.jsp
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Question> questions = new ArrayList<>();
        
        try {
            // Establish database connection
            connection = DBConnection.getConnection();
            
            // Prepare SQL query to fetch all questions
            String sql = "SELECT * FROM questions ORDER BY id";
            preparedStatement = connection.prepareStatement(sql);
            
            // Execute query
            resultSet = preparedStatement.executeQuery();
            
            // Map ResultSet to Question objects
            while (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getInt("id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setOptionA(resultSet.getString("option_a"));
                question.setOptionB(resultSet.getString("option_b"));
                question.setOptionC(resultSet.getString("option_c"));
                question.setOptionD(resultSet.getString("option_d"));
                question.setCorrectAnswer(resultSet.getString("correct_answer"));
                
                questions.add(question);
            }
            
            // Set questions as request attribute
            request.setAttribute("questions", questions);
            
            // Forward to quiz.jsp
            request.getRequestDispatcher("/quiz.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.err.println("Database error while fetching questions: " + e.getMessage());
            e.printStackTrace();
            
            // Set error message and forward to error page or display message
            request.setAttribute("errorMessage", "Unable to load quiz questions. Please try again later.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "Database error occurred");
            
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
