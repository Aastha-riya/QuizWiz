package com.quizwiz.service;

import com.quizwiz.dao.QuestionDAO;
import com.quizwiz.dao.QuizAttemptDAO;
import com.quizwiz.model.Question;
import com.quizwiz.model.QuizResult;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service layer for quiz submission logic.
 * Handles score calculation, percentage, performance label,
 * category analytics, and delegates persistence to DAOs.
 * No SQL. No HTTP.
 */
public class SubmitService {

    private static final Logger LOGGER = Logger.getLogger(SubmitService.class.getName());

    private final QuestionDAO    questionDAO    = new QuestionDAO();
    private final QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();

    /**
     * Processes a quiz submission end-to-end:
     * scores it, persists it, and returns a rich result object.
     *
     * @param userAnswers      map of questionId -> selected option (from HTTP params)
     * @param userId           logged-in user ID, or null for guest
     * @param startedAt        timestamp when the quiz was started
     * @param completedAt      timestamp when the quiz was submitted
     * @return QuizResult containing all data needed by result.jsp
     * @throws SQLException if a database error occurs
     */
    public QuizResult processSubmission(Map<Integer, String> userAnswers,
                                         Integer userId,
                                         Timestamp startedAt,
                                         Timestamp completedAt) throws SQLException {

        long timeTakenSeconds = (completedAt.getTime() - startedAt.getTime()) / 1000;

        // ── Fetch questions for scoring ───────────────────────────────────────
        List<Question> questions = questionDAO.getAllForScoring();

        // ── Build lookup maps ─────────────────────────────────────────────────
        Map<Integer, String>  correctAnswers   = new HashMap<>();
        Map<Integer, Integer> questionMarks    = new HashMap<>();
        Map<Integer, String>  questionCategory = new HashMap<>();
        int totalMarks = 0;

        for (Question q : questions) {
            correctAnswers.put(q.getId(),   String.valueOf(q.getCorrectAnswer()));
            questionMarks.put(q.getId(),    q.getMarks());
            questionCategory.put(q.getId(), q.getCategoryName() != null ? q.getCategoryName() : "Uncategorized");
            totalMarks += q.getMarks();
        }

        // ── Calculate score ───────────────────────────────────────────────────
        int obtainedMarks = 0;
        int correctCount  = 0;
        int wrongCount    = 0;
        int unanswered    = 0;

        for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
            int    qId        = entry.getKey();
            String correct    = entry.getValue();
            String userAnswer = userAnswers.get(qId);

            if (userAnswer == null || userAnswer.trim().isEmpty()) {
                unanswered++;
            } else if (userAnswer.trim().equalsIgnoreCase(correct)) {
                correctCount++;
                obtainedMarks += questionMarks.getOrDefault(qId, 1);
            } else {
                wrongCount++;
            }
        }

        // ── Calculate percentage ──────────────────────────────────────────────
        double percentage = totalMarks > 0 ? (obtainedMarks * 100.0 / totalMarks) : 0;

        // ── Persist attempt + answers ─────────────────────────────────────────
        quizAttemptDAO.saveAttemptWithAnswers(userId, obtainedMarks, questions.size(),
                percentage, timeTakenSeconds, startedAt, completedAt,
                userAnswers, correctAnswers, questionMarks);

        // ── Performance label ─────────────────────────────────────────────────
        String performance;
        if      (percentage >= 90) performance = "Excellent";
        else if (percentage >= 75) performance = "Good";
        else if (percentage >= 50) performance = "Average";
        else                       performance = "Needs Improvement";

        // ── Category-wise analytics ───────────────────────────────────────────
        Map<String, int[]> categoryStats = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
            int    qId        = entry.getKey();
            String correct    = entry.getValue();
            String category   = questionCategory.get(qId);
            String userAnswer = userAnswers.get(qId);

            categoryStats.putIfAbsent(category, new int[]{0, 0});
            int[] stats = categoryStats.get(category);
            stats[1] += questionMarks.getOrDefault(qId, 1);
            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correct)) {
                stats[0] += questionMarks.getOrDefault(qId, 1);
            }
        }

        // ── Build and return result ───────────────────────────────────────────
        return new QuizResult(
                obtainedMarks, totalMarks, percentage,
                correctCount, wrongCount, unanswered,
                questions.size(), performance,
                timeTakenSeconds, categoryStats
        );
    }
}
