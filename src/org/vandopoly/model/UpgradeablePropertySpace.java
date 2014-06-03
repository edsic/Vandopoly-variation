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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Model class that is a descendant of Space and represents an 
 * upgradeable-property on the board
 * 
 * @author Allie Mazzia
 */
public class UpgradeablePropertySpace extends PropertySpace implements Serializable {

    private int renovationCost_;
    final static long serialVersionUID = 228;

    public UpgradeablePropertySpace() {
        name_ = "NONE";
        state_ = SpaceUnowned.Instance();
        purchasePrice_ = 0;
        mortgageValue_ = 0;
        rentValues_ = new int[6];
        owner_ = null;
        renovationCost_ = 0;
    }

    public UpgradeablePropertySpace(String name, int type, int spaceNumber, int purchasePrice,
            int mortgageValue, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5, 
            int renovation) {

        name_ = name;
        state_ = SpaceUnowned.Instance();
        purchasePrice_ = purchasePrice;
        mortgageValue_ = mortgageValue;
        rentValues_ = new int[6];
        rentValues_[0] = rent0;
        rentValues_[1] = rent1;
        rentValues_[2] = rent2;
        rentValues_[3] = rent3;
        rentValues_[4] = rent4;
        rentValues_[5] = rent5;
        renovationCost_ = renovation;
        type_ = type;
        spaceNumber_ = spaceNumber;

        owner_ = null;
    }

    public UpgradeablePropertySpace(UpgradeablePropertySpace space) {
        name_ = space.getName();
        state_ = space.getState();
        purchasePrice_ = space.getPurchasePrice();
        mortgageValue_ = space.getMortgageValue();
        rentValues_ = new int[6];
        rentValues_[0] = space.getRentValues()[0];
        rentValues_[1] = space.getRentValues()[1];
        rentValues_[2] = space.getRentValues()[2];
        rentValues_[3] = space.getRentValues()[3];
        rentValues_[4] = space.getRentValues()[4];
        rentValues_[5] = space.getRentValues()[5];
        renovationCost_ = space.getRenovationCost_();
        type_ = space.getTypeInt();
        spaceNumber_ = space.getSpaceNumber();

        owner_ = space.getOwner();
    }

    public void changeState(SpaceState newState) {
        state_ = newState;
    }

    public SpaceState getState() {
        return state_;
    }

    public void landOn(Player p) {
        state_.landOn(p, this);
    }

    // Getters and setters
    public void setPurchasePrice(int purchasePrice) {
        purchasePrice_ = purchasePrice;
    }

    public int getPurchasePrice() {
        return purchasePrice_;
    }

    public void setMortgageValue(int mortgageValue) {
        mortgageValue_ = mortgageValue;
    }

    public int getMortgageValue() {
        return mortgageValue_;
    }

    public void setRentValues(int[] rentValues) {
        rentValues_ = rentValues;
    }

    public int[] getRentValues() {
        return rentValues_;
    }

    public void setOwner(Player p) {
        owner_ = p;
        NotificationManager.getInstance().notifyObservers(Notification.CHANGED_OWNER, this);
    }

    public Player getOwner() {
        return owner_;
    }

    public boolean isUpgradeable() {
        return state_.isUpgradeable(this);
    }

    public boolean isDowngradeable() {
        return state_.isDowngradeable(this);
    }

    public boolean isRenovated() {
        return state_.isRenovated(this);
    }

    public void bePurchased(Player owner) {
        setOwner(owner);
        state_.changeState(this, PropertyOwned.Instance());
    }

    public void renovate() {
        state_.renovate(this);
        NotificationManager.getInstance().notifyObservers(Notification.CHANGED_OWNER, this);
    }

    public int getRenovationCost_() {
        return renovationCost_;
    }

    public void setRenovationCost_(int renovationCost_) {
        this.renovationCost_ = renovationCost_;
    }

    public void downgrade() {
        state_.downgrade(this);
    }

    public void beMortgaged() {
        state_.changeState(this, SpaceMortgaged.Instance());
    }

    public void unmortgage() {
        state_.changeState(this, PropertyOwned.Instance());
    }

    public void ownershipIncrease() {
        // Do nothing
    }

    public void ownershipDecrease() {
        // Do nothing
    }
    
    public int getLevel() {
		return 0;
	}
}
