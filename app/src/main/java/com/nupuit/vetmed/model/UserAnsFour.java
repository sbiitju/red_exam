package com.nupuit.vetmed.model;

/**
 * Created by USER on 26-May-17.
 */

public class UserAnsFour {

    int ansA, ansB, ansC, ansD, ansE;
    int obtA, obtB, obtC, obtD, obtE;
    float obtainedMark;

    public UserAnsFour(){};

    public UserAnsFour(int ansA, int ansB, int ansC, int ansD, int ansE, int obtA, int obtB, int obtC, int obtD, int obtE, float obtainedMark) {
        this.ansA = ansA;
        this.ansB = ansB;
        this.ansC = ansC;
        this.ansD = ansD;
        this.ansE = ansE;
        this.obtA = obtA;
        this.obtB = obtB;
        this.obtC = obtC;
        this.obtD = obtD;
        this.obtE = obtE;
        this.obtainedMark = obtainedMark;
    }

    public int getObtE() {
        return obtE;
    }

    public void setObtE(int obtE) {
        this.obtE = obtE;
    }

    public int getAnsA() {
        return ansA;
    }

    public void setAnsA(int ansA) {
        this.ansA = ansA;
    }

    public int getAnsB() {
        return ansB;
    }

    public void setAnsB(int ansB) {
        this.ansB = ansB;
    }

    public int getAnsC() {
        return ansC;
    }

    public void setAnsC(int ansC) {
        this.ansC = ansC;
    }

    public int getAnsD() {
        return ansD;
    }

    public void setAnsD(int ansD) {
        this.ansD = ansD;
    }

    public int getAnsE() {
        return ansE;
    }

    public void setAnsE(int ansE) {
        this.ansE = ansE;
    }

    public int getObtA() {
        return obtA;
    }

    public void setObtA(int obtA) {
        this.obtA = obtA;
    }

    public int getObtB() {
        return obtB;
    }

    public void setObtB(int obtB) {
        this.obtB = obtB;
    }

    public int getObtC() {
        return obtC;
    }

    public void setObtC(int obtC) {
        this.obtC = obtC;
    }

    public int getObtD() {
        return obtD;
    }

    public void setObtD(int obtD) {
        this.obtD = obtD;
    }

    public float getObtainedMark() {
        return obtainedMark;
    }

    public void setObtainedMark(float obtainedMark) {
        this.obtainedMark = obtainedMark;
    }
}
