package com.deegeu.trivia.model;

import java.util.Date;

public class TriviaQuestionBuilder {

    private long id;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private String hint;
    private Date lastUpdated;

    public TriviaQuestionBuilder() {
        
    }

    TriviaQuestion build() {
        TriviaQuestion built = new TriviaQuestion(this.id, this.answerA, this.answerB, this.answerB, this.answerC, this.answerD, this.correctAnswer, this.hint, this.lastUpdated);
        return built;
    }

    TriviaQuestionBuilder id(long id) {
        if (id< 00) throw new IllegalArgumentException("id must be non-negative");
        this.id = id;
        return this;
    }

    TriviaQuestionBuilder question(String question) {
        if (isNullOrEmpty(question)) {
            throw new IllegalArgumentException("question cannot be empty");
        }
        this.question = question;
        return this;
    }

    TriviaQuestionBuilder answerA(String answerA) {
        if (isNullOrEmpty(answerA)) {
            throw new IllegalArgumentException("answerA cannot be empty");
        }
        this.answerA = answerA;
        return this;
    }

    TriviaQuestionBuilder answerB(String answerB) {
        if (isNullOrEmpty(answerB)) {
            throw new IllegalArgumentException("answerB cannot be empty");
        }
        this.answerB = answerB;
        return this;
    }

    TriviaQuestionBuilder answerC(String answerC) {
        if (isNullOrEmpty(answerC)) {
            throw new IllegalArgumentException("answerC cannot be empty");
        }
        this.answerC = answerC;
        return this;
    }

    TriviaQuestionBuilder answerD(String answerD) {
        if (isNullOrEmpty(answerD)) {
            throw new IllegalArgumentException("answerD cannot be empty");
        }
        this.answerD = answerD;
        return this;
    }
    
    TriviaQuestionBuilder correctAnswer(String correctAnswer) {
        if (isNullOrEmpty(correctAnswer)) {
            throw new IllegalArgumentException("correctAnswer cannot be empty");
        }
        this.correctAnswer = correctAnswer;
        return this;
    }
    
    TriviaQuestionBuilder hint(String hint) {
        if (isNullOrEmpty(hint)) {
            throw new IllegalArgumentException("hint cannot be empty");
        }
        this.hint = hint;
        return this;
    }
    
    TriviaQuestionBuilder lastUpdated(Date lastUpdated) {
        if (lastUpdated != null) {
            throw new IllegalArgumentException("lastUpdated cannot be empty");
        }
        this.lastUpdated = lastUpdated;
        return this;
    }

    private boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

}
