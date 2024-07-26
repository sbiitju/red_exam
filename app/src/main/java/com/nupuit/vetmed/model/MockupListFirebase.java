package com.nupuit.vetmed.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by USER on 18-May-17.
 */
@IgnoreExtraProperties
public class MockupListFirebase{

    public MockupListFirebase(){}

    RealmList<OneMarkQuestion> questions1;
    RealmList<TwoMarkQuestions> questions2;
    RealmList<FourMarkQuestion_New> questions4;
    String id;
    ArrayList<FourMarkQuestion_New_Firebase> questionsFire4;
    RealmList<RealmString> questionsNUmber1, questionsNUmber2,  questionsNUmber4;

//    public MockupListFirebase(RealmList<OneMarkQuestion> questions1, RealmList<TwoMarkQuestions> questions2, RealmList<FourMarkQuestion_New> questions4, String id) {
//        this.questions1 = questions1;
//        this.questions2 = questions2;
//        this.questions4 = questions4;
//        this.id = id;
//    }

    public MockupListFirebase(RealmList<OneMarkQuestion> questions1, RealmList<TwoMarkQuestions> questions2, ArrayList<FourMarkQuestion_New_Firebase> questionsFire4, String id) {
        this.questions1 = questions1;
        this.questions2 = questions2;
        this.questionsFire4 = questionsFire4;
        this.id = id;
    }

    public MockupListFirebase(RealmList<RealmString> questionsNUmber1, RealmList<RealmString> questionsNUmber2, RealmList<RealmString> questionsNUmber4, String id) {
        this.questionsNUmber1 = questionsNUmber1;
        this.questionsNUmber2 = questionsNUmber2;
        this.questionsNUmber4 = questionsNUmber4;
        this.id = id;
    }

    public ArrayList<FourMarkQuestion_New_Firebase> getQuestionsFire4() {

        return questionsFire4;
    }

    public void setQuestionsFire4(ArrayList<FourMarkQuestion_New_Firebase> questionsFire4) {
        this.questionsFire4 = questionsFire4;
    }

    public RealmList<OneMarkQuestion> getQuestions1() {

        return questions1;
    }

    public void setQuestions1(RealmList<OneMarkQuestion> questions1) {
        this.questions1 = questions1;
    }

    public RealmList<TwoMarkQuestions> getQuestions2() {

        return questions2;
    }

    public void setQuestions2(RealmList<TwoMarkQuestions> questions2) {
        this.questions2 = questions2;
    }

    public RealmList<FourMarkQuestion_New> getQuestions4() {

        return questions4;
    }

    public void setQuestions4(RealmList<FourMarkQuestion_New> questions4) {
        this.questions4 = questions4;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
