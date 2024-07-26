package com.nupuit.vetmed.model;

import java.util.ArrayList;

/**
 * Created by USER on 20-May-17.
 */

public class FourMarkQuestion_New_Firebase {

    public FourMarkQuestion_New_Firebase(){}

//    String qsn1, Qsn1OPa, Qsn1OPb, Qsn1OPc, Qsn1OPd, qsn1ana, Qsn1Anb, Qsn1Anc, Qsn1And, Qsn2,
//            Qsn2Ana, Qsn2Anb, Qsn2Anc, Qsn2And, Qsn2OPa, Qsn2OPb, Qsn2OPc, Qsn2OPd, mainText, mainSubText;
//
//    RealmList<RealmString> Qsn2Op;
//    RealmList<RealmString> mainOptions;
//    String qsnNumber;

    String qsn1;
    String qsn1OPa;
    String qsn1OPb;
    String qsn1OPc;
    String qsn1OPd;
    String qsn1ana;
    String qsn1Anb;
    String qsn1Anc;
    String qsn1And;
    String qsn2;
    String qsn2Ana;
    String qsn2Anb;
    String qsn2Anc;
    String qsn2And;
    String qsn2OPa;
    String qsn2OPb;
    String qsn2OPc;
    String qsn2OPd;
    String mainText;
    String mainSubText;
    ArrayList<String> qsn2Op;
    ArrayList<String> mainOptions;
    String qsnNumber;
    String mark;


    public FourMarkQuestion_New_Firebase(String qsn1, String qsn1OPa, String qsn1OPb, String qsn1OPc,
                                         String qsn1OPd, String qsn1ana, String qsn1Anb, String qsn1Anc,
                                         String qsn1And, String qsn2, String qsn2Ana, String qsn2Anb,
                                         String qsn2Anc, String qsn2And, String qsn2OPa, String qsn2OPb,
                                         String qsn2OPc, String qsn2OPd, String mainText, String mainSubText,
                                         ArrayList<String> qsn2Op,   ArrayList<String> mainOptions, String qsnNumber, String mark) {
        this.qsn1 = qsn1;
        this.qsn1OPa = qsn1OPa;
        this.qsn1OPb = qsn1OPb;
        this.qsn1OPc = qsn1OPc;
        this.qsn1OPd = qsn1OPd;
        this.qsn1ana = qsn1ana;
        this.qsn1Anb = qsn1Anb;
        this.qsn1Anc = qsn1Anc;
        this.qsn1And = qsn1And;
        this.qsn2 = qsn2;
        this.qsn2Ana = qsn2Ana;
        this.qsn2Anb = qsn2Anb;
        this.qsn2Anc = qsn2Anc;
        this.qsn2And = qsn2And;
        this.qsn2OPa = qsn2OPa;
        this.qsn2OPb = qsn2OPb;
        this.qsn2OPc = qsn2OPc;
        this.qsn2OPd = qsn2OPd;
        this.mainText = mainText;
        this.mainSubText = mainSubText;
        this.qsn2Op = qsn2Op;
        this.mainOptions = mainOptions;
        this.qsnNumber = qsnNumber;
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getQsn1() {
        return qsn1;
    }

    public void setQsn1(String qsn1) {
        this.qsn1 = qsn1;
    }

    public String getQsn1OPa() {
        return qsn1OPa;
    }

    public void setQsn1OPa(String qsn1OPa) {
        this.qsn1OPa = qsn1OPa;
    }

    public String getQsn1OPb() {
        return qsn1OPb;
    }

    public void setQsn1OPb(String qsn1OPb) {
        this.qsn1OPb = qsn1OPb;
    }

    public String getQsn1OPc() {
        return qsn1OPc;
    }

    public void setQsn1OPc(String qsn1OPc) {
        this.qsn1OPc = qsn1OPc;
    }

    public String getQsn1OPd() {
        return qsn1OPd;
    }

    public void setQsn1OPd(String qsn1OPd) {
        this.qsn1OPd = qsn1OPd;
    }

    public String getQsn1ana() {
        return qsn1ana;
    }

    public void setQsn1ana(String qsn1ana) {
        this.qsn1ana = qsn1ana;
    }

    public String getQsn1Anb() {
        return qsn1Anb;
    }

    public void setQsn1Anb(String qsn1Anb) {
        this.qsn1Anb = qsn1Anb;
    }

    public String getQsn1Anc() {
        return qsn1Anc;
    }

    public void setQsn1Anc(String qsn1Anc) {
        this.qsn1Anc = qsn1Anc;
    }

    public String getQsn1And() {
        return qsn1And;
    }

    public void setQsn1And(String qsn1And) {
        this.qsn1And = qsn1And;
    }

    public String getQsn2() {
        return qsn2;
    }

    public void setQsn2(String qsn2) {
        this.qsn2 = qsn2;
    }

    public String getQsn2Ana() {
        return qsn2Ana;
    }

    public void setQsn2Ana(String qsn2Ana) {
        this.qsn2Ana = qsn2Ana;
    }

    public String getQsn2Anb() {
        return qsn2Anb;
    }

    public void setQsn2Anb(String qsn2Anb) {
        this.qsn2Anb = qsn2Anb;
    }

    public String getQsn2Anc() {
        return qsn2Anc;
    }

    public void setQsn2Anc(String qsn2Anc) {
        this.qsn2Anc = qsn2Anc;
    }

    public String getQsn2And() {
        return qsn2And;
    }

    public void setQsn2And(String qsn2And) {
        this.qsn2And = qsn2And;
    }

    public String getQsn2OPa() {
        return qsn2OPa;
    }

    public void setQsn2OPa(String qsn2OPa) {
        this.qsn2OPa = qsn2OPa;
    }

    public String getQsn2OPb() {
        return qsn2OPb;
    }

    public void setQsn2OPb(String qsn2OPb) {
        this.qsn2OPb = qsn2OPb;
    }

    public String getQsn2OPc() {
        return qsn2OPc;
    }

    public void setQsn2OPc(String qsn2OPc) {
        this.qsn2OPc = qsn2OPc;
    }

    public String getQsn2OPd() {
        return qsn2OPd;
    }

    public void setQsn2OPd(String qsn2OPd) {
        this.qsn2OPd = qsn2OPd;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getMainSubText() {
        return mainSubText;
    }

    public void setMainSubText(String mainSubText) {
        this.mainSubText = mainSubText;
    }

    public   ArrayList<String> getQsn2Op() {
        return qsn2Op;
    }

    public void setQsn2Op(  ArrayList<String> qsn2Op) {
        this.qsn2Op = qsn2Op;
    }

    public   ArrayList<String> getMainOptions() {
        return mainOptions;
    }

    public void setMainOptions(  ArrayList<String> mainOptions) {
        this.mainOptions = mainOptions;
    }

    public String getQsnNumber() {
        return qsnNumber;
    }

    public void setQsnNumber(String qsnNumber) {
        this.qsnNumber = qsnNumber;
    }
}
