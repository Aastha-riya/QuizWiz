package com.quizwiz.model;

import java.util.Map;

/**
 * Immutable value object carrying all quiz result data.
 * Passed from SubmitService to SubmitServlet, then forwarded to result.jsp.
 */
public class QuizResult {

    private final int    obtainedMarks;
    private final int    totalMarks;
    private final double percentage;
    private final int    correctAnswers;
    private final int    wrongAnswers;
    private final int    unanswered;
    private final int    totalQuestions;
    private final String performance;
    private final long   timeTakenSeconds;

    /** Map of categoryName -> [obtainedMarks, totalMarks] */
    private final Map<String, int[]> categoryStats;

    public QuizResult(int obtainedMarks, int totalMarks, double percentage,
                      int correctAnswers, int wrongAnswers, int unanswered,
                      int totalQuestions, String performance,
                      long timeTakenSeconds, Map<String, int[]> categoryStats) {
        this.obtainedMarks    = obtainedMarks;
        this.totalMarks       = totalMarks;
        this.percentage       = percentage;
        this.correctAnswers   = correctAnswers;
        this.wrongAnswers     = wrongAnswers;
        this.unanswered       = unanswered;
        this.totalQuestions   = totalQuestions;
        this.performance      = performance;
        this.timeTakenSeconds = timeTakenSeconds;
        this.categoryStats    = categoryStats;
    }

    public int    getObtainedMarks()    { return obtainedMarks; }
    public int    getTotalMarks()       { return totalMarks; }
    public double getPercentage()       { return percentage; }
    public String getFormattedPercentage() { return String.format("%.2f", percentage); }
    public int    getCorrectAnswers()   { return correctAnswers; }
    public int    getWrongAnswers()     { return wrongAnswers; }
    public int    getUnanswered()       { return unanswered; }
    public int    getTotalQuestions()   { return totalQuestions; }
    public String getPerformance()      { return performance; }
    public long   getTimeTakenSeconds() { return timeTakenSeconds; }
    public Map<String, int[]> getCategoryStats() { return categoryStats; }
}
