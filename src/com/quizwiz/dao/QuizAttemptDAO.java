package com.quizwiz.dao;

import com.quizwiz.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for quiz_attempts and quiz_answers tables.
 * Handles all persistence of quiz results in a single transaction.
 */
public class QuizAttemptDAO {

    private static final Logger LOGGER = Logger.getLogger(QuizAttemptDAO.class.getName());

    /**
     * Persists a quiz attempt and all individual answers atomically.
     * Rolls back both inserts if either fails.
     *
     * @param userId           logged-in user ID, or null for guest
     * @param score            marks obtained
     * @param totalQuestions   number of questions in the quiz
     * @param percentage       score as percentage of total marks
     * @param timeTakenSeconds time from quiz start to submission
     * @param startedAt        timestamp when quiz started
     * @param completedAt      timestamp when quiz was submitted
     * @param userAnswers      map of questionId -> user's selected option string
     * @param correctAnswers   map of questionId -> correct answer string
     * @param questionMarks    map of questionId -> marks value
     * @return generated attempt ID, or -1 if the transaction failed
     */
    public int saveAttemptWithAnswers(Integer userId, int score, int totalQuestions,
                                       double percentage, long timeTakenSeconds,
                                       Timestamp startedAt, Timestamp completedAt,
                                       Map<Integer, String> userAnswers,
                                       Map<Integer, String> correctAnswers,
                                       Map<Integer, Integer> questionMarks) {
        String attemptSql = "INSERT INTO quiz_attempts " +
                            "(user_id, score, total_questions, percentage, time_taken_seconds, started_at, completed_at) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String answerSql  = "INSERT INTO quiz_answers " +
                            "(attempt_id, question_id, selected_option, is_correct) " +
                            "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement attemptPs = connection.prepareStatement(
                    attemptSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                if (userId != null) attemptPs.setInt(1, userId);
                else                attemptPs.setNull(1, java.sql.Types.INTEGER);
                attemptPs.setInt(2, score);
                attemptPs.setInt(3, totalQuestions);
                attemptPs.setDouble(4, percentage);
                attemptPs.setLong(5, timeTakenSeconds);
                attemptPs.setTimestamp(6, startedAt);
                attemptPs.setTimestamp(7, completedAt);
                attemptPs.executeUpdate();

                int attemptId = -1;
                try (ResultSet keys = attemptPs.getGeneratedKeys()) {
                    if (keys.next()) attemptId = keys.getInt(1);
                }
                if (attemptId == -1) throw new SQLException("Failed to retrieve generated attempt ID");

                try (PreparedStatement answerPs = connection.prepareStatement(answerSql)) {
                    for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
                        int     qId        = entry.getKey();
                        String  correct    = entry.getValue();
                        String  userAnswer = userAnswers.get(qId);
                        boolean isCorrect  = userAnswer != null
                                && userAnswer.trim().equalsIgnoreCase(correct);

                        answerPs.setInt(1, attemptId);
                        answerPs.setInt(2, qId);
                        if (userAnswer != null) answerPs.setString(3, userAnswer.trim().toUpperCase());
                        else                    answerPs.setNull(3, java.sql.Types.CHAR);
                        answerPs.setBoolean(4, isCorrect);
                        answerPs.addBatch();
                    }
                    answerPs.executeBatch();
                }

                connection.commit();
                LOGGER.info("Transaction committed: attemptId=" + attemptId + ", userId=" + userId + ", score=" + score);
                return attemptId;

            } catch (SQLException e) {
                connection.rollback();
                LOGGER.log(Level.WARNING, "Transaction rolled back", e);
                return -1;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Failed to obtain DB connection for persistence", e);
            return -1;
        }
    }
}
