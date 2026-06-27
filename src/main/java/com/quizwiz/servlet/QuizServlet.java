package com.quizwiz.servlet;

import com.quizwiz.model.Question;
import com.quizwiz.service.QuizService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles GET /quiz — fetches questions and forwards to quiz.jsp.
 * All business logic and SQL delegated to QuizService and QuestionDAO.
 *
 * Optional URL parameters:
 *   ?category=Java
 *   ?difficulty=Hard
 */
@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(QuizServlet.class.getName());

    private final QuizService quizService = new QuizService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category   = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");

        try {
            List<Question> questions = quizService.getRandomQuestions(category, difficulty);

            // Record quiz start time in session for timer and attempt persistence
            request.getSession().setAttribute("quizStartTime", new Timestamp(System.currentTimeMillis()));

            request.setAttribute("questions",          questions);
            request.setAttribute("selectedCategory",   category);
            request.setAttribute("selectedDifficulty", difficulty);

            request.getRequestDispatcher("/quiz.jsp").forward(request, response);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load quiz questions", e);
            request.setAttribute("errorMessage", "Unable to load quiz questions. Please try again later.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }
}
