package com.nupuit.vetmed.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by USER on 25-May-17.
 */

public class FourMarkMainOptions extends RealmObject {

    public FourMarkMainOptions(){};

    String qsnNumber;
    RealmList<RealmString> realmStrings;

    public FourMarkMainOptions(String id, RealmList<RealmString> realmStrings) {
        this.qsnNumber = id;
        this.realmStrings = realmStrings;
    }


    public String getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(String qsnNumber) {
        this.qsnNumber = qsnNumber;
    }

    public RealmList<RealmString> getRealmStrings() {
        return realmStrings;
    }

    public void setRealmStrings(RealmList<RealmString> realmStrings) {
        this.realmStrings = realmStrings;
    }
}
