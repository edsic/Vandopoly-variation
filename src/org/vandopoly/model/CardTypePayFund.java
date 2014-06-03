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
 ***************************************************************************
 */
package org.vandopoly.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * The CardTypePayFund class represents a "Chance" or "Community Chest" card
 * that requires the player who drew the card to pay the Scholarship Fund
 *
 * @author Allie Mazzia
 */
public class CardTypePayFund extends Card implements Serializable {

    private int amount_;
    final static long serialVersionUID = 203;

    public CardTypePayFund() {
        message_ = "NONE";
        amount_ = 0;
    }

    public CardTypePayFund(String message, int amount) {
        message_ = message;
        amount_ = amount;
    }

    public int getAmount() {
        return amount_;
    }

    public void setAmount(int amount) {
        amount_ = amount;
    }

    public void landOn(Player p, ArrayList<Player> players) {
        //System.out.println("Pay Fund");
        p.updateCash(-getAmount());
        NotificationManager.getInstance().notifyObservers(Notification.UPDATE_SCHOLARSHIP_FUND,
                new Integer(getAmount()));
    }

    public boolean isWithQuestion() {
        return false;
    }

}
