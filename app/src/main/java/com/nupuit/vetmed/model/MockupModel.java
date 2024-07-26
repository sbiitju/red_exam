package com.nupuit.vetmed.model;

import io.realm.RealmObject;

/**
 * Created by USER on 16-May-17.
 */

public class MockupModel extends RealmObject{

    public MockupModel(){}

    String name;
    String time;
    String totalMark;
    String id;
    boolean lock;
    String totalQsn;

    public MockupModel(String name, String time, String totalMark, String id, boolean lock, String totalQsn) {
        this.name = name;
        this.time = time;
        this.totalMark = totalMark;
        this.id = id;
        this.lock = lock;
        this.totalQsn = totalQsn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalQsn() {
        return totalQsn;
    }

    public void setTotalQsn(String totalQsn) {
        this.totalQsn = totalQsn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
