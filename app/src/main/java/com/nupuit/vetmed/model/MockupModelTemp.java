package com.nupuit.vetmed.model;

import io.realm.RealmObject;

/**
 * Created by USER on 16-May-17.
 */

public class MockupModelTemp extends RealmObject{

    public MockupModelTemp(){}

    String name;
    String time;
    String totalMark;
    String id;
    boolean lock;

    public MockupModelTemp(String name, String time, String totalMark, String id, boolean lock) {
        this.name = name;
        this.time = time;
        this.totalMark = totalMark;
        this.id = id;
        this.lock = lock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
