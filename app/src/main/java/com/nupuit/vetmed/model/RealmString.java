package com.nupuit.vetmed.model;

import io.realm.RealmObject;

/**
 * Created by USER on 20-May-17.
 */

public class RealmString extends RealmObject {

    public RealmString(){}

    String string;

    public RealmString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
