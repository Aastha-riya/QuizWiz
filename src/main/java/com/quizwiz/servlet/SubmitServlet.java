package com.quizwiz.servlet;

import com.quizwiz.model.QuizResult;
import com.quizwiz.service.SubmitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles POST /submit — processes quiz submission and redirects to GET /result.
 * Uses the Post/Redirect/Get (PRG) pattern to prevent form resubmission on refresh.
 * All scoring, persistence, and analytics delegated to SubmitService.
 */
@WebServlet({"/submit", "/result"})
public class SubmitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SubmitServlet.class.getName());

    private final SubmitService submitService = new SubmitService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read session data
        HttpSession session = request.getSession(false);
        Timestamp startedAt = (session != null && session.getAttribute("quizStartTime") != null)
                ? (Timestamp) session.getAttribute("quizStartTime")
                : new Timestamp(System.currentTimeMillis());
        Timestamp completedAt = new Timestamp(System.currentTimeMillis());
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        // Collect user answers from form: answer_1=A, answer_2=C, etc.
        Map<Integer, String> userAnswers = new HashMap<>();
        for (String param : request.getParameterMap().keySet()) {
            if (param.startsWith("answer_")) {
                try {
                    int qId = Integer.parseInt(param.substring("answer_".length()));
                    userAnswers.put(qId, request.getParameter(param));
                } catch (NumberFormatException ignored) {
                    // skip malformed param names
                }
            }
        }

        try {
            QuizResult result = submitService.processSubmission(userAnswers, userId, startedAt, completedAt);

            // PRG pattern: store result in session, then redirect to GET /result
            // This prevents form resubmission on browser refresh (F5).
            HttpSession resultSession = request.getSession(true);
            resultSession.setAttribute("quizResult", result);

            response.sendRedirect(request.getContextPath() + "/result");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to process quiz submission", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing quiz submission");
        }
    }

    /**
     * Handles GET /result — reads QuizResult from session and forwards to result.jsp.
     * This is the "Get" half of the Post/Redirect/Get pattern.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        QuizResult result = (session != null) ? (QuizResult) session.getAttribute("quizResult") : null;

        if (result == null) {
            // No result in session — redirect back to quiz
            response.sendRedirect(request.getContextPath() + "/quiz");
            return;
        }

        // Expose result fields as request attributes for result.jsp
        request.setAttribute("score",           result.getObtainedMarks());
        request.setAttribute("total",           result.getTotalQuestions());
        request.setAttribute("obtainedMarks",   result.getObtainedMarks());
        request.setAttribute("totalMarks",      result.getTotalMarks());
        request.setAttribute("percentage",      result.getFormattedPercentage());
        request.setAttribute("correctAnswers",  result.getCorrectAnswers());
        request.setAttribute("wrongAnswers",    result.getWrongAnswers());
        request.setAttribute("unanswered",      result.getUnanswered());
        request.setAttribute("performance",     result.getPerformance());
        request.setAttribute("timeTakenSeconds",result.getTimeTakenSeconds());
        request.setAttribute("categoryStats",   result.getCategoryStats());

        // Remove from session so a manual refresh shows an empty state rather than stale data
        session.removeAttribute("quizResult");

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
