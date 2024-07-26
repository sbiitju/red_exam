package com.nupuit.vetmed.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by USER on 17-May-17.
 */

public class MockupHistory extends RealmObject {

    public MockupHistory(){}

    String mockupName;
    String id;
    String result;
    String correctQsn;
    String totalQsnSkipped;
    String totalQsnWrong;
    String totalQsn;
    String percentage;
    String time;
    String date;

    RealmList<RealmString> correctQsnList;
    RealmList<RealmString> skippedQsnList;
    RealmList<RealmString> wrongQsnList;

    public MockupHistory(String mockupName, String id, String result, String correctQsn, String totalQsn, String percentage, String time, String date) {
        this.mockupName = mockupName;
        this.id = id;
        this.result = result;
        this.correctQsn = correctQsn;
        this.totalQsn = totalQsn;
        this.percentage = percentage;
        this.time = time;
        this.date = date;
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

    public RealmList<RealmString> getCorrectQsnList() {

        return correctQsnList;
    }

    public void setCorrectQsnList(RealmList<RealmString> correctQsnList) {
        this.correctQsnList = correctQsnList;
    }

    public RealmList<RealmString> getSkippedQsnList() {

        return skippedQsnList;
    }

    public void setSkippedQsnList(RealmList<RealmString> skippedQsnList) {
        this.skippedQsnList = skippedQsnList;
    }

    public RealmList<RealmString> getWrongQsnList() {

        return wrongQsnList;
    }

    public void setWrongQsnList(RealmList<RealmString> wrongQsnList) {
        this.wrongQsnList = wrongQsnList;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getMockupName() {

        return mockupName;
    }

    public void setMockupName(String mockupName) {

        this.mockupName = mockupName;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {

        this.result = result;
    }

    public String getCorrectQsn() {

        return correctQsn;
    }

    public void setCorrectQsn(String correctQsn) {

        this.correctQsn = correctQsn;
    }

    public String getTotalQsn()
    {
        return totalQsn;
    }

    public void setTotalQsn(String totalQsn) {

        this.totalQsn = totalQsn;
    }

    public String getPercentage() {

        return percentage;
    }

    public void setPercentage(String percentage) {

        this.percentage = percentage;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }
}
