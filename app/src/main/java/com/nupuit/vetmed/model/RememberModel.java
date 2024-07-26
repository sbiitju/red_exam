package com.nupuit.vetmed.model;

/**
 * Created by USER on 23-May-17.
 */

public class RememberModel {

    String qsnNumber;
    int checkedValue;

    public RememberModel(String qsnNumber, int checkedValue) {
        this.qsnNumber = qsnNumber;
        this.checkedValue = checkedValue;
    }

    public String getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(String qsnNumber) {
        this.qsnNumber = qsnNumber;
    }

    public int getCheckedValue() {
        return checkedValue;
    }

    public void setCheckedValue(int checkedValue) {
        this.checkedValue = checkedValue;
    }
}
