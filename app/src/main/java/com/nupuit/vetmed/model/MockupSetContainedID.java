package com.nupuit.vetmed.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by USER on 20-May-17.
 */

public class MockupSetContainedID extends RealmObject{

    public MockupSetContainedID(){}


    String id;
    RealmList<RealmString> Questionid;


    public MockupSetContainedID(String id, RealmList<RealmString> questionid) {
        this.id = id;
        Questionid = questionid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<RealmString> getQuestionid() {
        return Questionid;
    }

    public void setQuestionid(RealmList<RealmString> questionid) {
        Questionid = questionid;
    }
}
