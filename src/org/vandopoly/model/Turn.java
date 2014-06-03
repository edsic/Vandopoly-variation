/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vandopoly.model;

/**
 *
 * @author tao
 */
public class Turn {

    public int die1, die2;
    public int purchasePropertyID, renovationPropertyID;
    public String purchaseAction, renovationAction;
    public boolean isAI;

    public Turn() {
        die1 = -1;
        die2 = -1;
        purchasePropertyID = -1;
        renovationPropertyID = -1;
        purchaseAction = "NA";
        renovationAction = "NA";
        isAI = false;
    }

    public Turn(int die1_, int die2_, String purchaseaction_, int purchasePropertyID_,
            String renovationaction_, int renovationPropertyID_, boolean isAI_) {
        this.die1 = die1_;
        this.die2 = die2_;
        this.purchasePropertyID = purchasePropertyID_;
        this.renovationPropertyID = renovationPropertyID_;
        this.purchaseAction = purchaseaction_;
        this.renovationAction = renovationaction_;
        this.isAI = isAI_;
    }

    public int getDie1() {
        return die1;
    }

    public void setDie1(int die1) {
        this.die1 = die1;
    }

    public int getDie2() {
        return die2;
    }

    public void setDie2(int die2) {
        this.die2 = die2;
    }

    public String getPurchaseAction() {
        return purchaseAction;
    }

    public void setPurchaseAction(String purchaseAction) {
        this.purchaseAction = purchaseAction;
    }

    public String getRenovationAction() {
        return renovationAction;
    }

    public void setRenovationAction(String renovationAction) {
        this.renovationAction = renovationAction;
    }

    public int getPurchasePropertyID() {
        return purchasePropertyID;
    }

    public void setPurchasePropertyID(int purchasePropertyID) {
        this.purchasePropertyID = purchasePropertyID;
    }

    public int getRenovationPropertyID() {
        return renovationPropertyID;
    }

    public void setRenovationPropertyID(int renovationPropertyID) {
        this.renovationPropertyID = renovationPropertyID;
    }

    public boolean isIsAI() {
        return isAI;
    }

    public void setIsAI(boolean isAI) {
        this.isAI = isAI;
    }

}
