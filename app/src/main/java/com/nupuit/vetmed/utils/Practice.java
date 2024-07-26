package com.nupuit.vetmed.utils;

import com.nupuit.vetmed.model.MarkQuestion;

import java.util.ArrayList;

/**
 * Created by USER on 22-May-17.
 */

public class Practice {
    private static Practice ourInstance = new Practice();

    public static Practice getInstance() {
        if (ourInstance == null) {
            ourInstance = new Practice();
        }
        return ourInstance;
    }

    private static final ArrayList<MarkQuestion> markQuestionList = new ArrayList<MarkQuestion>();

    public static void setValue(MarkQuestion markQuestion) {

        markQuestionList.add(markQuestion);

    }

    public static MarkQuestion getResult() {

        String qsnNumber = String.valueOf(markQuestionList.size());

        float totalMark = 0f;

        for (int i = 0; i < markQuestionList.size(); i++) {

            if (markQuestionList.get(i).getMark().equals("1")) {
                totalMark = totalMark + 1;
            } else if (markQuestionList.get(i).getMark().equals("2")){
                totalMark = totalMark + 2;
            }if (markQuestionList.get(i).getMark().equals("4")){
                totalMark = totalMark + Float.parseFloat(markQuestionList.get(i).getQuestion());
            }

        }

        MarkQuestion markQuestion = new MarkQuestion(String.valueOf(totalMark), qsnNumber);

        return markQuestion;

    }

    public static void setListNull() {

        if (markQuestionList.size() > 0) {
            markQuestionList.clear();
        }
    }

    private Practice() {
    }
}
