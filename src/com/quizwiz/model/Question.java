package com.quizwiz.model;

import java.sql.Timestamp;

/**
 * Model class representing a quiz question with multiple choice options.
 * Encapsulates question data retrieved from the database.
 */
public class Question {
    
    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctAnswer;
    private int categoryId;
    private String categoryName;
    private String difficulty;
    private int marks;
    private String explanation;
    private Timestamp createdAt;
    
    /**
     * Default constructor
     */
    public Question() {
    }
    
    /**
     * Parameterized constructor for creating Question objects
     * 
     * @param id Unique question identifier
     * @param questionText The question text
     * @param optionA First option
     * @param optionB Second option
     * @param optionC Third option
     * @param optionD Fourth option
     * @param correctAnswer Correct answer (A, B, C, or D)
     */
    public Question(int id, String questionText, String optionA, String optionB, 
                   String optionC, String optionD, char correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Full constructor including category, difficulty, marks, explanation and timestamp
     *
     * @param id Unique question identifier
     * @param questionText The question text
     * @param optionA First option
     * @param optionB Second option
     * @param optionC Third option
     * @param optionD Fourth option
     * @param correctAnswer Correct answer (A, B, C, or D)
     * @param categoryId Foreign key to categories table
     * @param difficulty Difficulty level (Easy, Medium, Hard)
     * @param marks Marks awarded for correct answer
     * @param explanation Explanation of the correct answer
     * @param createdAt Timestamp when question was created
     */
    public Question(int id, String questionText, String optionA, String optionB,
                   String optionC, String optionD, char correctAnswer,
                   int categoryId, String difficulty, int marks,
                   String explanation, Timestamp createdAt) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.marks = marks;
        this.explanation = explanation;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public String getOptionA() {
        return optionA;
    }
    
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }
    
    public String getOptionB() {
        return optionB;
    }
    
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
    
    public String getOptionC() {
        return optionC;
    }
    
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }
    
    public String getOptionD() {
        return optionD;
    }
    
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
    
    public char getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // -------------------------
    // Validation & Helper Methods
    // -------------------------

    /**
     * Validates that correctAnswer is one of the allowed option characters.
     */
    public boolean isCorrectOptionValid() {
        return correctAnswer == 'A'
            || correctAnswer == 'B'
            || correctAnswer == 'C'
            || correctAnswer == 'D';
    }

    /** Returns true if difficulty is Easy */
    public boolean isEasy() {
        return "Easy".equalsIgnoreCase(difficulty);
    }

    /** Returns true if difficulty is Medium */
    public boolean isMedium() {
        return "Medium".equalsIgnoreCase(difficulty);
    }

    /** Returns true if difficulty is Hard */
    public boolean isHard() {
        return "Hard".equalsIgnoreCase(difficulty);
    }

    /** Returns true if the question carries 2 or more marks */
    public boolean isHighWeightQuestion() {
        return marks >= 2;
    }

    /**
     * Returns string representation of Question object for debugging
     */
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", categoryId=" + categoryId +
                ", difficulty='" + difficulty + '\'' +
                ", marks=" + marks +
                ", explanation='" + explanation + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
