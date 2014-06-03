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
import java.util.ArrayList;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.ui.MessagePopUp;

/*
 * Model class that is a descendant of Space and represents a tax space on the board
 * 
 * @author Allie Mazzia
 */
public class BlankSpace extends Space implements Serializable {

    private int fee_;
    private double percentageFee_;

    final static long serialVersionUID = 243;

    public BlankSpace() {
        name_ = "NONE";
        fee_ = 0;
        percentageFee_ = 0.0;
    }

    public BlankSpace(String name) {
        name_ = name;
        fee_ = 0;
        percentageFee_ = 0.0;
    }

    public BlankSpace(String name, int fee, double percentageFee) {
        name_ = name;
        fee_ = fee;
        percentageFee_ = percentageFee;
    }

    @Override
    public void landOn(Player p) {
        //System.out.println(p.getName() + " land on a empty space.");
        if (!(p instanceof AIPlayer)) {
		new MessagePopUp("You land on an empty space. No action possible.");
            }
    }

    // Getters and setters
    public void setFee(int fee) {
        this.fee_ = fee;
    }

    public int getFee() {
        return fee_;
    }

    public void setPercentageFee(double percentageFee) {
        percentageFee_ = percentageFee;
    }

    public double getPercentageFee() {
        return percentageFee_;
    }

}
