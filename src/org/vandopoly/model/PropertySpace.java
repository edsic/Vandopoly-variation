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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Model class that is a descendant of Space and represents a property space on the board
 * 
 * @author Allie Mazzia
 */
public class PropertySpace extends Space implements Serializable {

    final static long serialVersionUID = 217;

    protected SpaceState state_;
    protected int rentValues_[];

    protected int purchasePrice_, mortgageValue_;
    protected Player owner_;

    protected int type_, spaceNumber_;

    protected static final String propertyType_[] = {"Purple", "Light Blue", "Brown", "Orange",
        "Red", "Yellow", "Green", "Dark Blue", "Transportation", "Utility"};
    protected static final int propertiesForMonopoly_[] = {3, 3, 3, 3, 3, 2, 2, 3, 4, 2};

    public PropertySpace() {
        name_ = "NONE";
        purchasePrice_ = 0;
        mortgageValue_ = 0;
        state_ = SpaceUnowned.Instance();
        rentValues_ = new int[4];
        owner_ = null;
    }

    public PropertySpace(String name, int type, int spaceNumber, int purchasePrice, int mortgageValue,
            int rent0, int rent1, int rent2, int rent3) {
        name_ = name;
        purchasePrice_ = purchasePrice;
        mortgageValue_ = mortgageValue;
        state_ = SpaceUnowned.Instance();
        rentValues_ = new int[4];
        rentValues_[0] = rent0;
        rentValues_[1] = rent1;
        rentValues_[2] = rent2;
        rentValues_[3] = rent3;
        owner_ = null;
        type_ = type;
        spaceNumber_ = spaceNumber;
    }

    public PropertySpace(PropertySpace space) {
        name_ = space.getName();
        purchasePrice_ = space.getPurchasePrice();
        mortgageValue_ = space.getMortgageValue();
        state_ = space.getState();
        rentValues_ = new int[4];
        rentValues_[0] = space.getRentValues()[0];
        rentValues_[1] = space.getRentValues()[1];
        rentValues_[2] = space.getRentValues()[2];
        rentValues_[3] = space.getRentValues()[3];

        owner_ = space.getOwner();
        type_ = space.getTypeInt();
        spaceNumber_ = space.getSpaceNumber();
    }

    public String toString() {
        String string = "Property Name: " + name_;
        if (owner_ == null) {
            string += ", Owned by: Nobody";
        } else {
            string += ", Owned by: " + owner_.getName();
        }

        return string;
    }

    public void changeState(SpaceState newState) {
        state_ = newState;
    }

    public void landOn(Player p) {
        state_.landOn(p, this);
    }

    public void ownershipIncrease() {
        state_.ownershipIncrease(this);
    }

    public void ownershipDecrease() {
        state_.ownershipDecrease(this);
    }

    // Getters and setters
    public String getType() {
        return propertyType_[type_];
    }

    /**
     * Get level of Property type, so Purple = 0, Light Blue = 1 ect.
     *
     * @return int value from 0 - 9
     */
    public int getTypeInt() {
        return type_;
    }

    public int getPropertiesForMonopoly(int type) {
        return propertiesForMonopoly_[type];
    }

    public int getSpaceNumber() {
        return spaceNumber_;
    }

    public void setOwner(Player p) {
        owner_ = p;
        NotificationManager.getInstance().notifyObservers(Notification.CHANGED_OWNER, this);
    }

    public Player getOwner() {
        return owner_;
    }

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

    public boolean isUpgradeable() {
        return false;
    }

    public boolean isDowngradeable() {
        return false;
    }

    public boolean isRenovated() {
        return false;
    }

    public void bePurchased(Player owner) {
        setOwner(owner);

        // Find the current state of properties of this type, increase them, then update this.state_
        SpaceState newState = owner.updateTypeIncrease(type_);
        if (newState != null) {
            state_ = newState;
        } else {
            state_ = PropertyOwned.Instance();
        }
    }

    public void downgrade() {
    }

    public void beMortgaged() {
        state_.changeState(this, SpaceMortgaged.Instance());
        owner_.updateTypeDecrease(type_);
    }

    public void unmortgage() {
        // Find the current state of properties of this type, increase them, then update this.state_
        SpaceState newState = owner_.updateTypeIncrease(type_);
        if (newState != null) {
            state_ = newState;
        } else {
            state_ = PropertyOwned.Instance();
        }

    }

    public int[] getRentValues() {
        return rentValues_;
    }

    public String getNameAndStatus() {
        return propertyType_[type_] + ": " + name_ + state_.getNameAndStatus();
    }

    public String getNameAndStatusWithType() {
        return getTypeInt() + name_ + state_.getNameAndStatus();
    }

    public SpaceState getState() {
        return state_;
    }
}
