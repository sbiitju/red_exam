package com.nupuit.vetmed.model;

import io.realm.RealmObject;

/**
 * Created by USER on 16-May-17.
 */

public class MockupModelUser extends RealmObject{

    public MockupModelUser(){}

    String name;
    String timeRequired;
    String totalMarkObtained;
    String id;
    String totalQsnAnswered;
    String totalQsnSkipped;
    String totalQsnWrong;


    public MockupModelUser(String name, String timeRequired, String totalMarkObtained, String id, String totalQsnAnswered, String totalQsnSkipped, String totalQsnWrong) {
        this.name = name;
        this.timeRequired = timeRequired;
        this.totalMarkObtained = totalMarkObtained;
        this.id = id;
        this.totalQsnAnswered = totalQsnAnswered;
        this.totalQsnSkipped = totalQsnSkipped;
        this.totalQsnWrong = totalQsnWrong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(String timeRequired) {
        this.timeRequired = timeRequired;
    }

    public String getTotalMarkObtained() {
        return totalMarkObtained;
    }

    public void setTotalMarkObtained(String totalMarkObtained) {
        this.totalMarkObtained = totalMarkObtained;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalQsnAnswered() {
        return totalQsnAnswered;
    }

    public void setTotalQsnAnswered(String totalQsnAnswered) {
        this.totalQsnAnswered = totalQsnAnswered;
    }

    public String getTotalQsnSkipped() {
        return totalQsnSkipped;
    }

    public void setTotalQsnSkipped(String totalQsnSkipped) {
        this.totalQsnSkipped = totalQsnSkipped;
    }

    public String getTotalQsnWrong() {
        return totalQsnWrong;
    }

    public void setTotalQsnWrong(String totalQsnWrong) {
        this.totalQsnWrong = totalQsnWrong;
    }
}
