package com.nupuit.vetmed.model;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by USER on 18-May-17.
 */

public class MockupList extends RealmObject {

    public MockupList(){}

 //   RealmList<OneMarkQuestion> questions1;
    RealmList<TwoMarkQuestions> questions2;
  //  RealmList<TenMarkQuestions> questions10;
    String id;

    public MockupList(RealmList<TwoMarkQuestions> questions2, String id) {
        this.questions2 = questions2;
      //  this.questions10 = questions10;
        this.id = id;
    }

    public RealmList<TwoMarkQuestions> getQuestions2() {

        return questions2;
    }

    public void setQuestions2(RealmList<TwoMarkQuestions> questions2) {
        this.questions2 = questions2;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
