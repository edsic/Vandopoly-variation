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
//import org.vandopoly.messaging.Notification;
import org.vandopoly.ui.MessagePopUp;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * PlayerFree class implements the behavior associated with the player 
 * being free, or not in jail.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PlayerFree extends PlayerState implements Serializable {

    private static PlayerFree INSTANCE = null;
    final static long serialVersionUID = 210;

    protected PlayerFree() {
        // Exists to disable instantiation
    }

    public static PlayerState Instance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerFree();
        }

        return INSTANCE;
    }

    @Override
    public void movePiece(Player player, int spaces) {
        player.updatePosition(spaces);
    }

    public void movePiece(Player player, Dice dice) {
        player.updatePosition(dice.getTotalRoll());
    }

    @Override
    public void collectRent(Player payee, int amount, Player payer) {
        // Call to payee.updateCash(), payer.updateCash() 
        // Temporary - for checking to make sure states are working properly
        if (payer.getIndex() != payee.getIndex()) {
            payee.updateCash(amount);
            payer.updateCash(-amount);
            //NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE,payer.getName() + " paid $" + amount + " in rent to " + payee.getName());
            if (!(payer instanceof AIPlayer)) {
                new MessagePopUp("You pay $" + amount + " rent.");
            } else if (payer instanceof AIPlayer) {
                new MessagePopUp("You receive $" + amount + " rent.");
            }
        }
        NotificationManager.getInstance().notifyObservers(Notification.PROPERTY_RENT, true);
    }

    @Override
    public void goToJail(Player player) {
        player.changeState(PlayerInJail.Instance());
    }

    @Override
    public void getOutOfJail(Player player) {
        // Empty - player is not in jail
    }

    public String toString() {
        return "PlayerFree";
    }
}
