package com.nupuit.vetmed.utils;

import android.util.Log;

import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.model.UserAnsFour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by USER on 23-May-17.
 */

public class RememberSingleton {
    private static RememberSingleton ourInstance = new RememberSingleton();

    public static RememberSingleton getInstance() {

        if (ourInstance == null) {
            ourInstance = new RememberSingleton();
        }
        return ourInstance;
    }

    private static final ArrayList<MarkQuestion> correctQuestionList = new ArrayList<MarkQuestion>();
    private static final ArrayList<MarkQuestion> wrongQuestionList = new ArrayList<MarkQuestion>();
    private static final ArrayList<MarkQuestion> skippedQuestionList = new ArrayList<MarkQuestion>();
    private static final HashMap<MarkQuestion, String> remeberHash = new HashMap<MarkQuestion, String>();
    private static final HashMap<String, UserAnsFour> remeberHashFour = new HashMap<String, UserAnsFour>();

    public static void populateFourMarkFromMockupList(String qsnNumber, UserAnsFour userAnsFour) {


        remeberHashFour.put(qsnNumber, userAnsFour);
        Log.d("integerFaulet", qsnNumber + " " + qsnNumber);

    }

    public UserAnsFour getCheckedFour(String questionNumber) {

        //Log.e("4qsnNumber", questionNumber + "");

        //   UserAnsFour userAnsFour = new UserAnsFour();

        Set<String> keySet = remeberHashFour.keySet();

        UserAnsFour userAnsFour = new UserAnsFour();
        for (String markQsn : keySet) {

            if (questionNumber.equals(markQsn)) {
                userAnsFour = remeberHashFour.get(markQsn);
               // Log.e("4qsnNumber1", markQsn + " " + questionNumber + " " + userAnsFour.getAns1a());
            }


        }

        //Log.e("4qsnNumber1", " " + questionNumber + " " + userAnsFour.getAns1a());

        return userAnsFour;

    }


    public static void populateFromMockupList(MarkQuestion markQuestion, String checkedValue) {


        remeberHash.put(markQuestion, checkedValue);
        Log.d("integerFaulet", markQuestion.getQuestion() + "");

    }

    public String getCheckedValue(MarkQuestion markQuestion) {

        String value = "-5";

        Set<MarkQuestion> keySet = remeberHash.keySet();

        for (MarkQuestion markQsn : keySet) {

            String mark = markQsn.getMark();
            String qsn = markQsn.getQuestion();


            if (markQuestion.getMark().equals(mark)) {
                if (markQuestion.getQuestion().equals(qsn)) {

                    //  MarkQuestion mar = new MarkQuestion(mark, qsn);
                    value = remeberHash.get(markQsn);

                }
            }

        }

        //  String value1 = remeberHash.get((MarkQuestion) new MarkQuestion(markQuestion.getMark(), markQuestion.getQuestion()));

        //Log.d("integerFaulet", value + " ");
        return value;

    }

    public void updateCheckedValue(MarkQuestion markQuestion, String checkedValue) {

        Set<MarkQuestion> keySet = remeberHash.keySet();

        for (MarkQuestion markQsn : keySet) {

            String mark = markQsn.getMark();
            String qsn = markQsn.getQuestion();

            if (markQuestion.getMark().equals(mark)) {
                if (markQuestion.getQuestion().equals(qsn)) {

                    //  MarkQuestion mar = new MarkQuestion(mark, qsn);
                    remeberHash.put(markQsn, checkedValue);

                }
            }

        }


    }


    public void updateCheckedValueFour(String qsnNumber, UserAnsFour userAnsFour) {
        Set<String> keySet = remeberHashFour.keySet();

        for (String markQsn : keySet) {


            if (qsnNumber.equals(markQsn)) {
                remeberHashFour.put(markQsn, userAnsFour);
         //       Log.d("4qsnNumber1Update", " " + qsnNumber + " " + markQsn);
            }

          //  Log.d("4qsnNumber1UpdateBaire", " " + qsnNumber + " " + markQsn);

        }
    }


    public void setListNull() {

        if (remeberHash.size() > 0) {
            remeberHash.clear();
        }

    }

    public void setListNullFour() {

        if (remeberHashFour.size() > 0) {
            remeberHashFour.clear();
        }

    }

    private RememberSingleton() {
    }

    public String getResultFour() {

        int skipped = 0;
        int wrong = 0;
        int correct = 0;
        int mark = 0;



        Set<String> keySet = remeberHashFour.keySet();

        for (String markQsn : keySet) {

            UserAnsFour userAnsFour = remeberHashFour.get(markQsn);
            float singelmark = userAnsFour.getObtainedMark();

            if (userAnsFour.getObtA() == 5){
                mark = mark+2;
            }
            if (userAnsFour.getObtB() == 5){
                mark = mark+2;
            }
            if (userAnsFour.getObtC() == 5){
                mark = mark+2;
            }
            if (userAnsFour.getObtD() == 5){
                mark = mark+2;
            }
            if (userAnsFour.getObtE() == 5){
                mark = mark+2;
            }


        }


        Log.d("dekhitoki", mark+":"+keySet.size());


        return mark+":"+keySet.size();
    }



    public MarkQuestion getResult(int checkedValue) {

        MarkQuestion markQuestion = new MarkQuestion();
        int mark1 = 0;
        int qsn1 = 0;

        Set<MarkQuestion> keySet = remeberHash.keySet();

        for (MarkQuestion markQsn : keySet) {

            int mark = Integer.parseInt(markQsn.getMark());
            int value = Integer.parseInt(remeberHash.get(markQsn));

            if (value == checkedValue) {
                mark1 = mark1 + 2;
                qsn1++;
            }

        }

        markQuestion.setMark(String.valueOf(mark1));
        markQuestion.setQuestion(String.valueOf(qsn1));

        return markQuestion;
    }
}
