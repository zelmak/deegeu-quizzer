package com.deegeu.trivia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TriviaQuestionArrayAccess implements TriviaQuestionAccessible {

    private List<TriviaQuestion> questionList;
    private static final int MAX_NUMBER_OF_QUESTIONS_PER_PAGE = 10;

    public TriviaQuestionArrayAccess() {
        this.loadQuestionArray();
    }

    private void loadQuestionArray() {
        questionList = new ArrayList<>();
        questionList.add(new TriviaQuestionBuilder()
                .id(0)
                .question("How many feet are in a mile?")
                .answerA("5260")
                .answerB("5270")
                .answerC("5280")
                .answerD("5290")
                .correctAnswer("C")
                .hint("The altitude of Denver, Colorado")
                .lastUpdated(new Date())
                .build());
    }

    @Override
    public TriviaQuestion getQuestionByIndex(long index) {
        return ((int) index < questionList.size()) ? questionList.get((int) index) : null;
    }

    @Override
    public TriviaQuestion getQuestionById(long id) {
        return questionList.stream().filter(q -> q.getId() == id).findFirst().get();
    }

    @Override
    public TriviaQuestion getRandomQuestion() {
        int index = (new Random()).nextInt(questionList.size());
        return questionList.get(index);
    }

    @Override
    public List<TriviaQuestion> getQuestionList(long offset) {
        long start = offset;
        if (start < 0) {
            start = 0;
        }
        if (start >= questionList.size()) {
            start = questionList.size();
        }
        long end = start + MAX_NUMBER_OF_QUESTIONS_PER_PAGE;
        if (end > questionList.size()) {
            end = questionList.size();
        }

        return questionList.subList((int) start, (int) end);
    }

    @Override
    public List<TriviaQuestion> getSpecifiedQuestionList(long... ids) {
        List<TriviaQuestion> returnList = new ArrayList<>();
        for (long currentId: ids) {
            returnList.add(getQuestionById(currentId));
        }
        return returnList;
    }

    @Override
    public long getQuestionListSize() {
        return questionList.size();
    }

}
