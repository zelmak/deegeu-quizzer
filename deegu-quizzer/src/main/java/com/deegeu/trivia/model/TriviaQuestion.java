package com.deegeu.trivia.model;

import java.util.Date;

public class TriviaQuestion {

    private final Long id;
    private final String question;
    private final String answerA;
    private final String answerB;
    private final String answerC;
    private final String answerD;
    private final String correctAnswer;
    private final String hint;
    private final Date lastUpdated;

    public TriviaQuestion(Long id, String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String hint, Date lastUpdated) {
        this.id = id;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.hint = hint;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getHint() {
        return hint;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
    
    
}
