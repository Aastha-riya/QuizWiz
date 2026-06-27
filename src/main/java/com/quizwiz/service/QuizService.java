package com.quizwiz.service;

import com.quizwiz.dao.QuestionDAO;
import com.quizwiz.model.Question;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service layer for quiz display logic.
 * Sits between QuizServlet and QuestionDAO — no SQL here, no HTTP here.
 */
public class QuizService {

    private static final Logger LOGGER = Logger.getLogger(QuizService.class.getName());
    private final QuestionDAO questionDAO = new QuestionDAO();

    /**
     * Returns up to 10 random questions, optionally filtered by category and difficulty.
     *
     * @param category   category name, or null for all
     * @param difficulty difficulty level, or null for all
     * @return list of questions to display
     * @throws SQLException if the database query fails
     */
    public List<Question> getRandomQuestions(String category, String difficulty) throws SQLException {
        LOGGER.fine("Fetching questions for category=" + category + ", difficulty=" + difficulty);
        return questionDAO.getRandomQuestions(category, difficulty);
    }
}
