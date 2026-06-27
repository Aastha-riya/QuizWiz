package com.quizwiz.dao;

import com.quizwiz.model.Question;
import com.quizwiz.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for the questions table.
 * All SQL related to questions lives here — no SQL in servlets or services.
 */
public class QuestionDAO {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    /**
     * Fetches up to 10 random questions, optionally filtered by category and/or difficulty.
     *
     * @param category   category name filter, or null for all categories
     * @param difficulty difficulty filter (Easy/Medium/Hard), or null for all
     * @return list of matching Question objects
     * @throws SQLException if a database error occurs
     */
    public List<Question> getRandomQuestions(String category, String difficulty) throws SQLException {
        List<Question> questions = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT q.*, c.category_name " +
            "FROM questions q " +
            "LEFT JOIN categories c ON q.category_id = c.id " +
            "WHERE 1=1 "
        );

        if (isSet(category))   sql.append("AND c.category_name = ? ");
        if (isSet(difficulty)) sql.append("AND q.difficulty = ? ");
        sql.append("ORDER BY RAND() LIMIT 10");

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString())
        ) {
            int idx = 1;
            if (isSet(category))   ps.setString(idx++, category.trim());
            if (isSet(difficulty)) ps.setString(idx++, difficulty.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapRow(rs));
                }
            }
        }

        LOGGER.fine("Fetched " + questions.size() + " questions (category=" + category + ", difficulty=" + difficulty + ")");
        return questions;
    }

    /**
     * Fetches all questions with their correct answers and marks.
     * Used by SubmitService for scoring.
     *
     * @return list of all Question objects (id, correct_answer, marks, category)
     * @throws SQLException if a database error occurs
     */
    public List<Question> getAllForScoring() throws SQLException {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT q.id, q.correct_answer, q.marks, q.category_id, c.category_name " +
                     "FROM questions q " +
                     "LEFT JOIN categories c ON q.category_id = c.id " +
                     "ORDER BY q.id";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setCorrectAnswer(rs.getString("correct_answer").charAt(0));
                q.setMarks(rs.getInt("marks"));
                q.setCategoryId(rs.getInt("category_id"));
                q.setCategoryName(rs.getString("category_name"));
                questions.add(q);
            }
        }

        return questions;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Question mapRow(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getInt("id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectAnswer(rs.getString("correct_answer").charAt(0));
        q.setCategoryId(rs.getInt("category_id"));
        q.setCategoryName(rs.getString("category_name"));
        q.setDifficulty(rs.getString("difficulty"));
        q.setMarks(rs.getInt("marks"));
        q.setExplanation(rs.getString("explanation"));
        q.setCreatedAt(rs.getTimestamp("created_at"));
        return q;
    }

    private boolean isSet(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
