package com.nupuit.vetmed.model;

/**
 * Created by USER on 05-Jun-17.
 */

public class RememberTen {

    String qsnNumber;
    UserAnsFour userAnsFour;

    public RememberTen(String qsnNumber, UserAnsFour userAnsFour) {
        this.qsnNumber = qsnNumber;
        this.userAnsFour = userAnsFour;
    }

    public String getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(String qsnNumber) {
        this.qsnNumber = qsnNumber;
    }

    public UserAnsFour getUserAnsFour() {
        return userAnsFour;
    }

    public void setUserAnsFour(UserAnsFour userAnsFour) {
        this.userAnsFour = userAnsFour;
    }
}
