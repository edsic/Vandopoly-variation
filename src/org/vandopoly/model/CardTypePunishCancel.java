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

/*
 * The CardTypePayPlayers class represents a "Chance" or "Community Chest" card
 * that requires the player who drew the card to pay the other player(s)
 *
 * @author Allie Mazzia
 */
public class CardTypePunishCancel extends Card implements Serializable {

    private int amount_;
    final static long serialVersionUID = 244;

    public CardTypePunishCancel() {
        message_ = "NONE";
        amount_ = 0;
    }

    public CardTypePunishCancel(String message, int amount) {
        message_ = message;
        amount_ = amount;
    }

    public int getAmount() {
        return amount_;
    }

    public void setAmount(int amount) {
        amount_ = amount;
    }

    // punish the other player(s)
    @Override
    public void landOn(Player p, ArrayList<Player> players) {
        p.updateCash(getAmount());
        if (p instanceof AIPlayer) {
            //p.setProperties(p.getPropertiesHidden_());
            for (int x = 0; x < p.getPropertiesHidden_().size(); x++) {
                p.updateCash(-1 * p.getPropertiesHidden_().get(x).getPurchasePrice());
                p.updatePropertiesValue(p.getPropertiesHidden_().get(x).getPurchasePrice());
                p.getPropertiesHidden_().get(x).bePurchased(p);
                p.updateProperties(p.getPropertiesHidden_().get(x));

                String propertyName = p.getPropertiesHidden_().get(x).getName();
                //hard coded part
                if ((propertyName == "Murray Tower")
                        || (propertyName == "Hank Tower")
                        || (propertyName == "Wellington Tower")
                        //|| (propertyName == "Eisenhower Mansion")
                        || (propertyName == "Madison Mansion")) {
                    int renoCost = ((UpgradeablePropertySpace) p.getPropertiesHidden_().get(x)).getRenovationCost_();
                    p.updateCash(-renoCost);
                    p.updatePropertiesValue(renoCost);
                    ((UpgradeablePropertySpace) p.getPropertiesHidden_().get(x)).renovate();

                    //System.out.println("renovated " + p.getPropertiesHidden_().get(x).getName());
                }
                //NotificationManager.getInstance().notifyObservers(Notification.CHANGED_OWNER, p.getProperties().get(x));
            }

            AIPlayer tempPlayer = new AIPlayer(p);
            NotificationManager.getInstance().notifyObservers(Notification.UPDATE_PROPERTIES, tempPlayer);
        }

    }

    @Override
    public boolean isWithQuestion() {
        return false;
    }

}
