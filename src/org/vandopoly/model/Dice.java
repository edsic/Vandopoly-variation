/**
 * ***************************************************************************
 * Copyright 2010 Vandopoly Team * Licensed under the Apache License, Version
 * 2.0 (the "License"); * you may not use this file except in compliance with
 * the License. * You may obtain a copy of the License at * *
 * http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 * law or agreed to in writing, software * distributed under the License is
 * distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.* See the License for the specific language
 * governing permissions and * limitations under the License. *
 * **************************************************************************
 */
package org.vandopoly.model;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Dice class is a model class simulating the roll of a dice
 * 
 * @author James Kasten
 */
public class Dice implements Serializable {

    private static final long serialVersionUID = 107;
    private int die1_, die2_;
    private int numInRowDoubles_;
    private Random generator_ = new Random();
    private ArrayList<Turn> diceSeq;
    private Turn currentTurn_;
    private boolean gameContinue_;

    public Dice() {
        diceSeq = new ArrayList<Turn>();

         //Large Inequality
        /*
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn1
         diceSeq.add(new Turn(6, 4, "purchase", 22, "NA", -1, true));//opponent's turn2
         diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, false));//participant's turn1
         diceSeq.add(new Turn(2, 3, "purchase", 27, "NA", -1, true));//opponent's turn3
         diceSeq.add(new Turn(1, 6, "NA", -1, "NA", -1, false));//participant's turn2
         diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, true));//opponent's turn4
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, false));//participant's turn3
         diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, false));//participant's turn4
         diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn5
         diceSeq.add(new Turn(2, 5, "NA", -1, "NA", -1, true));//opponent's turn6
         diceSeq.add(new Turn(4, 3, "NA", -1, "NA", -1, false));//participant's turn5
         diceSeq.add(new Turn(4, 5, "purchase", 16, "NA", -1, true));//opponent's turn7
         diceSeq.add(new Turn(3, 1, "NA", -1, "NA", -1, false));//participant's turn6
         diceSeq.add(new Turn(2, 3, "purchase", 21, "NA", -1, true));//opponent's turn8
         diceSeq.add(new Turn(1, 1, "NA", -1, "NA", -1, false));//participant's turn7
         diceSeq.add(new Turn(2, 3, "NA", -1, "NA", -1, false));//participant's turn8
         diceSeq.add(new Turn(4, 5, "NA", -1, "NA", -1, true));//opponent's turn9
         diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, false));//participant's turn9
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn10
         diceSeq.add(new Turn(2, 3, "NA", -1, "NA", -1, true));//opponent's turn11
         diceSeq.add(new Turn(4, 1, "NA", -1, "NA", -1, false));//participant's turn10
         diceSeq.add(new Turn(2, 6, "NA", -1, "NA", -1, true));//opponent's turn12
         diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, false));//participant's turn11
         diceSeq.add(new Turn(3, 6, "purchase", 24, "NA", -1, true));//opponent's turn13
         diceSeq.add(new Turn(5, 1, "NA", -1, "NA", -1, false));//participant's turn12
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn14
         diceSeq.add(new Turn(2, 5, "NA", -1, "renovate", 24, true));//opponent's turn15
         diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, false));//participant's turn13
         diceSeq.add(new Turn(2, 5, "NA", -1, "renovate", 22, true));//opponent's turn16
         diceSeq.add(new Turn(2, 4, "NA", -1, "NA", -1, false));//participant's turn14
         diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn17
         diceSeq.add(new Turn(3, 3, "NA", -1, "NA", -1, true));//opponent's turn18
         diceSeq.add(new Turn(6, 5, "NA", -1, "NA", -1, true));//opponent's turn19
         diceSeq.add(new Turn(3, 1, "NA", -1, "NA", -1, false));//participant's turn15
         diceSeq.add(new Turn(5, 3, "NA", -1, "renovate", 21, true));//opponent's turn20
         diceSeq.add(new Turn(3, 1, "NA", -1, "NA", -1, false));//participant's turn16
         diceSeq.add(new Turn(4, 6, "NA", -1, "NA", -1, true));//opponent's turn21
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, false));//participant's turn17
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, false));//participant's turn18
         diceSeq.add(new Turn(4, 1, "NA", -1, "NA", -1, false));//participant's turn19
         diceSeq.add(new Turn(3, 3, "NA", -1, "NA", -1, true));//opponent's turn22
         diceSeq.add(new Turn(3, 5, "purchase", 29, "NA", -1, true));//opponent's turn23
         diceSeq.add(new Turn(2, 3, "NA", -1, "NA", -1, false));//participant's turn20
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn24
         diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn25
         diceSeq.add(new Turn(1, 5, "purchase", 17, "NA", -1, true));//opponent's turn26
         diceSeq.add(new Turn(5, 3, "NA", -1, "NA", -1, false));//participant's turn21
         diceSeq.add(new Turn(4, 5, "NA", -1, "NA", -1, true));//opponent's turn27
         diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn22
         diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn23
         diceSeq.add(new Turn(2, 4, "NA", -1, "NA", -1, false));//participant's turn24
         diceSeq.add(new Turn(4, 1, "NA", -1, "NA", -1, true));//opponent's turn28
         diceSeq.add(new Turn(1, 1, "NA", -1, "NA", -1, false));//participant's turn25
         diceSeq.add(new Turn(1, 4, "NA", -1, "NA", -1, false));//participant's turn26
         diceSeq.add(new Turn(4, 5, "NA", -1, "renovate", 29, true));//opponent's turn29
         diceSeq.add(new Turn(4, 1, "NA", -1, "NA", -1, false));//participant's turn27
         diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, true));//opponent's turn30
         diceSeq.add(new Turn(6, 5, "purchase", 19, "NA", -1, true));//opponent's turn31
         diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn28
         diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, false));//participant's turn29
         diceSeq.add(new Turn(3, 1, "NA", -1, "NA", -1, false));//participant's turn30
         diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, true));//opponent's turn32
         diceSeq.add(new Turn(2, 3, "NA", -1, "NA", -1, false));//participant's turn31
         diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, true));//opponent's turn33
         diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, false));//participant's turn32
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn34
         diceSeq.add(new Turn(5, 2, "NA", -1, "NA", -1, true));//opponent's turn35
         diceSeq.add(new Turn(4, 1, "NA", -1, "NA", -1, false));//participant's turn33
         diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn36
         diceSeq.add(new Turn(4, 6, "NA", -1, "NA", -1, true));//opponent's turn37
         diceSeq.add(new Turn(2, 4, "NA", -1, "NA", -1, false));//participant's turn34
         diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, true));//opponent's turn38
         //ONE MORE STEP TO END THE GAME
         diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, false));//participant's turn35
        */ 
        //Small Inequality
        
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn1
        diceSeq.add(new Turn(6, 4, "purchase", 22, "NA", -1, true));//opponent's turn2
        diceSeq.add(new Turn(6, 2, "NA", -1, "NA", -1, false));//participant's turn1
        diceSeq.add(new Turn(2, 3, "purchase", 27, "NA", -1, true));//opponent's turn3
        diceSeq.add(new Turn(2, 4, "NA", -1, "NA", -1, false));//participant's turn2
        diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, true));//opponent's turn4
        diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn3
        diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, false));//participant's turn4
        diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn5
        diceSeq.add(new Turn(2, 5, "NA", -1, "NA", -1, true));//opponent's turn6
        diceSeq.add(new Turn(4, 2, "NA", -1, "NA", -1, false));//participant's turn5
        diceSeq.add(new Turn(4, 5, "purchase", 16, "NA", -1, true));//opponent's turn7
        diceSeq.add(new Turn(3, 6, "NA", -1, "NA", -1, false));//participant's turn6
        diceSeq.add(new Turn(2, 3, "purchase", 21, "NA", -1, true));//opponent's turn8
        diceSeq.add(new Turn(3, 3, "NA", -1, "NA", -1, false));//participant's turn7
        diceSeq.add(new Turn(6, 3, "NA", -1, "NA", -1, false));//participant's turn8
        diceSeq.add(new Turn(4, 5, "NA", -1, "NA", -1, true));//opponent's turn9
        diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, false));//participant's turn9
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn10
        diceSeq.add(new Turn(2, 3, "NA", -1, "NA", -1, true));//opponent's turn11
        diceSeq.add(new Turn(3, 5, "NA", -1, "NA", -1, false));//participant's turn10
        diceSeq.add(new Turn(2, 6, "NA", -1, "NA", -1, true));//opponent's turn12
        diceSeq.add(new Turn(5, 2, "NA", -1, "NA", -1, false));//participant's turn11
        diceSeq.add(new Turn(3, 6, "purchase", 24, "NA", -1, true));//opponent's turn13
        diceSeq.add(new Turn(5, 4, "NA", -1, "NA", -1, false));//participant's turn12
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn14
        diceSeq.add(new Turn(2, 5, "NA", -1, "renovate", 24, true));//opponent's turn15
        diceSeq.add(new Turn(3, 5, "NA", -1, "NA", -1, false));//participant's turn13
        diceSeq.add(new Turn(2, 5, "NA", -1, "renovate", 22, true));//opponent's turn16
        diceSeq.add(new Turn(1, 4, "NA", -1, "NA", -1, false));//participant's turn14
        diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn17
        diceSeq.add(new Turn(3, 3, "NA", -1, "NA", -1, true));//opponent's turn18
        diceSeq.add(new Turn(6, 5, "NA", -1, "NA", -1, true));//opponent's turn19
        diceSeq.add(new Turn(5, 1, "NA", -1, "NA", -1, false));//participant's turn15
        diceSeq.add(new Turn(5, 3, "NA", -1, "renovate", 21, true));//opponent's turn20
        diceSeq.add(new Turn(1, 1, "NA", -1, "NA", -1, false));//participant's turn16
        diceSeq.add(new Turn(2, 2, "NA", -1, "NA", -1, false));//participant's turn17
        diceSeq.add(new Turn(1, 5, "NA", -1, "NA", -1, false));//participant's turn18
        diceSeq.add(new Turn(4, 6, "NA", -1, "NA", -1, true));//opponent's turn21
        diceSeq.add(new Turn(5, 3, "NA", -1, "NA", -1, false));//participant's turn19
        diceSeq.add(new Turn(3, 3, "NA", -1, "NA", -1, true));//opponent's turn22
        diceSeq.add(new Turn(3, 5, "purchase", 29, "NA", -1, true));//opponent's turn23
        diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, false));//participant's turn20
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn24
        diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, true));//opponent's turn25
        diceSeq.add(new Turn(1, 5, "NA", -1, "NA", -1, true));//opponent's turn26
        diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn21
        diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn22
        diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, false));//participant's turn23
        diceSeq.add(new Turn(4, 5, "NA", -1, "NA", -1, true));//opponent's turn27
        diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, false));//participant's turn24
        diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, false));//participant's turn25
        
        diceSeq.add(new Turn(4, 1, "purchase", 31, "NA", -1, true));//opponent's turn28
        diceSeq.add(new Turn(2, 6, "NA", -1, "NA", -1, false));//participant's turn26
        diceSeq.add(new Turn(4, 5, "NA", -1, "renovate", 29, true));//opponent's turn29
        diceSeq.add(new Turn(5, 5, "NA", -1, "NA", -1, false));//participant's turn27
        diceSeq.add(new Turn(2, 2, "NA", -1, "NA", -1, false));//participant's turn28
        diceSeq.add(new Turn(5, 3, "NA", -1, "NA", -1, false));//participant's turn29
        diceSeq.add(new Turn(4, 4, "NA", -1, "NA", -1, true));//opponent's turn30
        diceSeq.add(new Turn(6, 5, "NA", -1, "NA", -1, true));//opponent's turn31
        diceSeq.add(new Turn(2, 1, "NA", -1, "NA", -1, false));//participant's turn30
        diceSeq.add(new Turn(1, 3, "NA", -1, "NA", -1, true));//opponent's turn32
        diceSeq.add(new Turn(2, 4, "NA", -1, "NA", -1, false));//participant's turn31
        diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, true));//opponent's turn33
        diceSeq.add(new Turn(2, 5, "NA", -1, "NA", -1, false));//participant's turn32
        diceSeq.add(new Turn(6, 6, "purchase", 38, "NA", -1, true));//opponent's turn34
        diceSeq.add(new Turn(5, 2, "NA", -1, "renovate", 27, true));//opponent's turn35
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, false));//participant's turn33
        diceSeq.add(new Turn(3, 6, "NA", -1, "NA", -1, false));//participant's turn34
        diceSeq.add(new Turn(6, 6, "NA", -1, "NA", -1, true));//opponent's turn36
        diceSeq.add(new Turn(4, 6, "NA", -1, "NA", -1, true));//opponent's turn37
        diceSeq.add(new Turn(5, 6, "NA", -1, "NA", -1, false));//participant's turn35
        
        diceSeq.add(new Turn(1, 2, "NA", -1, "NA", -1, true));//opponent's turn38
        //ONE MORE STEP TO END THE GAME
        diceSeq.add(new Turn(3, 1, "NA", -1, "NA", -1, false));//participant's turn36
        
        gameContinue_ = true;

    }

    // Can be used to roll both dice at once and return the total
    public int roll() {
        die1_ = generator_.nextInt(6) + 1;
        die2_ = generator_.nextInt(6) + 1;

        numInRowDoubles_ = 0;

        /*
         if (die1_ != die2_) {
         numInRowDoubles_ = 0;
         } else if (numInRowDoubles_ < 4) {
         numInRowDoubles_++;
         } // Represents case where previous player went to Jail, counter must be reset
         else {
         numInRowDoubles_ = 1;
         }
         */
        // Notify interested parties about the change of Die state
        if (diceSeq.size() > 1) {
            if (diceSeq.get(0).isIsAI()) {
                NotificationManager.getInstance().notifyObservers(Notification.AI_ROLL_DICE, this);
            } else {
                NotificationManager.getInstance().notifyObservers(Notification.ROLL_DICE, this);
            }
            die1_ = diceSeq.get(0).getDie1();
            die2_ = diceSeq.get(0).getDie2();
            currentTurn_ = diceSeq.get(0);
            diceSeq.remove(0);
            numInRowDoubles_ = (die1_ != die2_) ? 0 : numInRowDoubles_ + 1;
            //System.out.println(numInRowDoubles_);
            return die1_ + die2_;
        } else if (diceSeq.size() == 1) {
            if (diceSeq.get(0).isIsAI()) {
                NotificationManager.getInstance().notifyObservers(Notification.AI_ROLL_DICE, this);
            } else {
                NotificationManager.getInstance().notifyObservers(Notification.ROLL_DICE, this);
            }
            die1_ = diceSeq.get(0).getDie1();
            die2_ = diceSeq.get(0).getDie2();
            currentTurn_ = diceSeq.get(0);
            diceSeq.remove(0);
            numInRowDoubles_ = (die1_ != die2_) ? 0 : numInRowDoubles_ + 1;
            //System.out.println(numInRowDoubles_);
            gameContinue_ = false;
            return die1_ + die2_;

        } else {
            NotificationManager.getInstance().notifyObservers(Notification.ROLL_DICE, this);
            gameContinue_ = false;
            return die1_ + die2_;
        }
    }

    // Roll the dies individually and return their value
    public int rollDie1() {
        // The generator gets an integer from 0 up to but not including 6
        // therefore we must add one to it.
        die1_ = generator_.nextInt(6) + 1;
        return die1_;
    }

    public int rollDie2() {
        die2_ = generator_.nextInt(6) + 1;
        return die2_;
    }

    // Get the value of each die
    public int getDie1() {
        return die1_;
    }

    public int getDie2() {
        return die2_;
    }

    public int getTotalRoll() {
        return die1_ + die2_;
    }

    public int getNumInRowDoubles() {
        return numInRowDoubles_;
    }

    public Turn getDefinedTurn() {
        return currentTurn_;
    }

    public boolean isGameContinue_() {
        return gameContinue_;
    }

    public void setGameContinue_(boolean gameContinue) {
        this.gameContinue_ = gameContinue;
    }

    // toString method
    public String toString() {
        return "(" + this.getDie1() + ", " + this.getDie2() + ")";
    }

}
