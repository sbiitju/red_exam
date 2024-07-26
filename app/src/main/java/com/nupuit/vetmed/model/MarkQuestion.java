package com.nupuit.vetmed.model;

/**
 * Created by USER on 22-May-17.
 */

public class MarkQuestion {

    public MarkQuestion(){};

    String mark, question;

    public MarkQuestion(String mark, String question) {
        this.mark = mark;
        this.question = question;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
