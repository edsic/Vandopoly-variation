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
import java.util.ListIterator;
import java.util.Random;

import org.vandopoly.ui.MessagePopUp;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.ui.ActionMessage;
import org.vandopoly.ui.PropertySelectionSellPanel;

/*
 * Player class is a model class that represents a game player.
 * Context class for the State pattern
 * 
 * @author Allie Mazzia
 */
public class AIPlayer extends Player implements Serializable {

    final static long serialVersionUID = 234;

    private final int SPACES_ON_BOARD = 40;

    private PlayerState state_;
    private String name_;
    private int cash_ = 1500, positionOnBoard_, index_;
    private int propertiesValue_ = 0;
    private boolean getOutOfJail_, sellPanelVisible_;
    private ArrayList<PropertySpace> properties_, propertiesHidden_;
    public PropertySelectionSellPanel propertySelectionSellPanel_;
    

    //Used to move player out of jail after three rolls
    private int numOfRolls_ = 0;

    protected AIPlayer() {
        state_ = PlayerFree.Instance();
        name_ = "ANONYMOUS";
        cash_ = 1500;
        propertiesValue_ = 0;
        index_ = 0;
        positionOnBoard_ = 0;
        getOutOfJail_ = false;
        properties_ = new ArrayList<PropertySpace>();
        numOfRolls_ = 0;
        sellPanelVisible_ = false;
        
    }

    public AIPlayer(int index, String name) {
        state_ = PlayerFree.Instance();
        name_ = name;
        cash_ = 1500;
        propertiesValue_ = 0;
        index_ = index;
        positionOnBoard_ = 0;
        getOutOfJail_ = false;
        properties_ = new ArrayList<PropertySpace>();
        numOfRolls_ = 0;
        sellPanelVisible_ = false;
        
    }

    public AIPlayer(AIPlayer player) {
        state_ = player.state_;
        name_ = player.getName();
        cash_ = player.getCash();
        propertiesValue_ = player.getPropertiesValue_();
        index_ = player.getIndex();
        positionOnBoard_ = player.getPosition();
        getOutOfJail_ = player.hasGetOutOfJail();
        properties_ = new ArrayList<PropertySpace>(player.getProperties());
        numOfRolls_ = player.getNumOfRolls();
        sellPanelVisible_ = false;
        
    }
    
    public AIPlayer(Player player) {
        state_ = PlayerFree.Instance();
        name_ = player.getName();
        cash_ = player.getCash();
        propertiesValue_ = player.getPropertiesValue_();
        index_ = player.getIndex();
        positionOnBoard_ = player.getPosition();
        getOutOfJail_ = player.hasGetOutOfJail();
        properties_ = new ArrayList<PropertySpace>(player.getProperties());
        numOfRolls_ = player.getNumOfRolls();
        sellPanelVisible_ = false;
        
    }

    public void changeState(PlayerState newState) {
        state_ = newState;
    }

    public PlayerState getState() {
        return state_;
    }

    public void movePiece(int numOfSpaces) {
        state_.movePiece(this, numOfSpaces);
    }

    public void movePiece(Dice dice) {
        state_.movePiece(this, dice);
    }

    public void collectRent(int amount, AIPlayer payer) {
        state_.collectRent(this, amount, payer);
    }

    public void goToJail() {
        NotificationManager.getInstance().notifyObservers(Notification.END_TURN_EARLY, null);
        setPosition(10);
        state_.goToJail(this);
    }

    public void getOutOfJail() {
        state_.getOutOfJail(this);
    }

    public void updatePosition(int numOfSpaces) {
        NotificationManager.getInstance().notifyObservers(Notification.PIECE_MOVE_SPACES, numOfSpaces);

        // Only award if the player did not previously land on 'GO' - that is taken
        // care of in CornerSpace.java
        if (((positionOnBoard_ + numOfSpaces) != 40) && (positionOnBoard_ + numOfSpaces) >= SPACES_ON_BOARD) {
            updateCash(200);
            NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE,
                    "The other player has been awarded $200 for passing GO!");
            //new MessagePopUp(this.getName() + " has been awarded $200 for passing GO!");
        }

        positionOnBoard_ = (positionOnBoard_ + numOfSpaces) % SPACES_ON_BOARD;
    }

    public void setPosition(int space) {
        if (space >= 0 && space < SPACES_ON_BOARD) {
            positionOnBoard_ = space;
            NotificationManager.getInstance().notifyObservers(Notification.PIECE_MOVE_TO, space);
        } else {
            System.err.println("Invalid space number for setPosition: " + space);
        }
    }

    // Sets the position of the player, but does not send a notification.
    // Used for networking 
    public void setPosition_NoNotify(int space) {
        if (space >= 0 && space < SPACES_ON_BOARD) {
            positionOnBoard_ = space;
        } else {
            System.err.println("Invalid space number for setPosition: " + space);
        }
    }

    public int getPosition() {
        return positionOnBoard_;
    }

    public void updateCash(int value) {
        cash_ += value;
        int propertyValue = this.getPropertiesValue_();
        if (cash_ < 0) {
            // the following part is hardcoded!!!

            if (cash_ + propertyValue > 0) {
                this.setPropertiesValue_(0);
                cash_ += propertyValue;
                propertiesHidden_ = (ArrayList<PropertySpace>)properties_.clone();
                for (int x = 0; x < properties_.size(); x++) {
                    properties_.get(x).changeState(SpaceUnowned.Instance());
                    properties_.get(x).owner_ = null;
                    NotificationManager.getInstance().notifyObservers(Notification.REMOVE_OWNER, properties_.get(x));
                }
                properties_.clear();
                AIPlayer tempPlayer = new AIPlayer(this);
                NotificationManager.getInstance().notifyObservers(Notification.UPDATE_PROPERTIES, tempPlayer);

            } else {
                ActionMessage.getInstance().newMessage("You Lost!");
                for (int x = 0; x < properties_.size(); x++) {
                    properties_.get(x).changeState(SpaceUnowned.Instance());
                }
                NotificationManager.getInstance().notifyObservers(Notification.REMOVE_PLAYER, this);
            }
        } else {
            sellPanelVisible_ = false;
        }

        //System.out.println(name_ + " called updateCash");
        AIPlayer tempPlayer = new AIPlayer(this);
        NotificationManager.getInstance().notifyObservers(Notification.UPDATE_CASH, tempPlayer);
        NotificationManager.getInstance().notifyObservers(Notification.UPDATE_PROPERTIES, tempPlayer);
    }

    public void setCash(int value) {
        cash_ = value;
    }

    public int getCash() {
        return cash_;
    }

    public int getPropertiesValue_() {
        return propertiesValue_;
    }

    public void setPropertiesValue_(int propertiesValue_) {
        this.propertiesValue_ = propertiesValue_;
    }

    public void setGetOutOfJail(boolean hasCard) {
        getOutOfJail_ = hasCard;

    }

    public void usedGetOutOfJail() {
        getOutOfJail_ = false;
        NotificationManager.getInstance().notifyObservers(Notification.USED_JAIL_CARD, new AIPlayer(this));
    }

    public void gainedGetOutOfJail() {
        getOutOfJail_ = true;
        NotificationManager.getInstance().notifyObservers(Notification.GAINED_JAIL_CARD, new AIPlayer(this));

    }

    public boolean hasGetOutOfJail() {
        return getOutOfJail_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public ArrayList<PropertySpace> getPropertiesHidden_() {
        return propertiesHidden_;
    }

    public void setPropertiesHidden_(ArrayList<PropertySpace> propertiesHidden_) {
        this.propertiesHidden_ = propertiesHidden_;
    }

    public String getName() {
        return name_;
    }

    public void setProperties(ArrayList<PropertySpace> properties) {
        properties_ = properties;
    }

    public ArrayList<PropertySpace> getProperties() {
        return properties_;
    }

    public String[] getPropertyArray() {
        String[] array = new String[properties_.size()];

        for (int i = 0; i < properties_.size(); i++) {
            array[i] = properties_.get(i).getNameAndStatusWithType();
        }

        return array;
    }

    public int getIndex() {
        return index_;
    }

    public void setIndex(int i) {
        index_ = i;
    }

    public void setNumOfRolls(int i) {
        numOfRolls_ = i;
    }

    public int getNumOfRolls() {
        return numOfRolls_;
    }

    public void updatePropertiesValue(int value) {
        propertiesValue_ += value;

        AIPlayer tempPlayer = new AIPlayer(this);
        NotificationManager.getInstance().notifyObservers(Notification.UPDATE_CASH, tempPlayer);
        //System.out.println(name_ + " called updateCash from updatePropertiesValue");

    }

    public void purchase(PropertySpace property) {
        if (property.getPurchasePrice() < cash_) {
            updateCash(-1 * property.getPurchasePrice());
            updatePropertiesValue(property.getPurchasePrice());
            // Property must be purchased before the property list
            // is updated... The railroads rely on it.
            property.bePurchased(this);
            updateProperties(property);

            NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE,
                    "The other player purchased " + property.getName());

            NotificationManager.getInstance().notifyObservers(Notification.DISABLE_PURCHASE, null);
        }
    }

    // Adds elements to the property list in the correct order -
    // sorted first by type (color), and then by space number
    public void updateProperties(PropertySpace property) {
        if (properties_.size() == 0) {
            properties_.add(property);
        } else {
            //Start iterator at the end of the list - for proper insertion we must traverse
            // the list from right to left
            ListIterator<PropertySpace> itr = properties_.listIterator(properties_.size());
            PropertySpace tempSpace = null;
            while (itr.hasPrevious()) {
                tempSpace = itr.previous();
                //Found properties of the same type, so insert based on space number
                if (property.getTypeInt() == tempSpace.getTypeInt()) {
                    if (property.getSpaceNumber() < tempSpace.getSpaceNumber()) {
                        properties_.add(itr.nextIndex(), property);
                    } else {
                        properties_.add(1 + itr.nextIndex(), property);
                    }

                    break;
                } // Found property with a type less than 'property''s type - add after
                else if (property.getTypeInt() > tempSpace.getTypeInt()) {
                    properties_.add(1 + (itr.nextIndex()), property);
                    break;
                }
            }
            // Property has the least type on the list, insert at the beginning
            if (property.getTypeInt() < tempSpace.getTypeInt()) {
                properties_.add(itr.nextIndex(), property);
            }
        }

        AIPlayer tempPlayer = new AIPlayer(this);
        NotificationManager.getInstance().notifyObservers(Notification.UPDATE_PROPERTIES, tempPlayer);
    }

    // Copy of updateProperties, just without the notification. 
    // Used for networking
    public void updateProperties_withoutNotification(PropertySpace property) {
        if (properties_.size() == 0) {
            properties_.add(property);
        } else {
            //Start iterator at the end of the list - for proper insertion we must traverse
            // the list from right to left
            ListIterator<PropertySpace> itr = properties_.listIterator(properties_.size());
            PropertySpace tempSpace = null;
            while (itr.hasPrevious()) {
                tempSpace = itr.previous();
                //Found properties of the same type, so insert based on space number
                if (property.getTypeInt() == tempSpace.getTypeInt()) {
                    if (property.getSpaceNumber() < tempSpace.getSpaceNumber()) {
                        properties_.add(itr.nextIndex(), property);
                    } else {
                        properties_.add(1 + itr.nextIndex(), property);
                    }

                    break;
                } // Found property with a type less than 'property''s type - add after
                else if (property.getTypeInt() > tempSpace.getTypeInt()) {
                    properties_.add(1 + (itr.nextIndex()), property);
                    break;
                }
            }
            // Property has the least type on the list, insert at the beginning
            if (property.getTypeInt() < tempSpace.getTypeInt()) {
                properties_.add(itr.nextIndex(), property);
            }
        }
    }

    /**
     * Increases ownership value for all properties owned by player of given
     * type_
     *
     * @param type_
     * @return state of the properties of color type_ returns null if no other
     * properties of given type are owned
     */
    public SpaceState updateTypeIncrease(int type_) {
        // Represents the new state that the properties of this type should be in.
        SpaceState newState = null;

        for (int i = 0; i < properties_.size(); i++) {
            if (type_ == properties_.get(i).getTypeInt()
                    && !properties_.get(i).getState().toString().equals(SpaceMortgaged.Instance().toString())) {
                properties_.get(i).ownershipIncrease();
                newState = properties_.get(i).getState();
            }
        }
        return newState;
    }

    /**
     * Decreases ownership value for all properties owned by player of given
     * type_
     *
     * @param type_
     * @return state of the properties of color type_ returns null if no other
     * properties of given type are owned
     */
    public SpaceState updateTypeDecrease(int type_) {
        // Represents the new state that the properties of this type should be in.
        SpaceState newState = null;

        for (int i = 0; i < properties_.size(); i++) {
            if (type_ == properties_.get(i).getTypeInt()
                    && !properties_.get(i).getState().toString().equals(SpaceMortgaged.Instance().toString())) {
                properties_.get(i).ownershipDecrease();
                newState = properties_.get(i).getState();
            }
        }
        return newState;
    }

    /**
     * Counts the number of properties owned of color type
     *
     * @param type
     * @return number of type properties owned
     */
    public int countProperties(int type) {
        int counter = 0;

        for (int i = 0; i < properties_.size(); i++) {
            if (properties_.get(i).getTypeInt() == type) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * @param type
     * @return whether a monopoly is owned of this type by player
     */
    public boolean hasMonopoly(int type) {
        return (countProperties(type) == PropertySpace.propertiesForMonopoly_[type]);
    }

    /**
     * Returns whether current property can be upgraded Checks to make sure
     * monopoly is owned and also checks to make sure that other properties in
     * monopoly are not a lower level
     *
     * @param UpgradeablePropertySpace that is being considered for upgrade
     * @return true if upgrading is valid; false otherwise
     */
    public boolean monopolyUpgradeValid(UpgradeablePropertySpace p) {
        int propertyCounter = 0;

        for (int i = 0; i < properties_.size(); i++) {
            if (properties_.get(i).getTypeInt() == p.getTypeInt()) {
                propertyCounter++;

                // Check to make sure that other properties in monopoly are not in lower state
                // Then checks to make sure that no other properties of this type are mortgaged
                if (properties_.get(i).getState().getLevel() < p.getState().getLevel()
                        || properties_.get(i).getState().toString().equals(SpaceMortgaged.Instance().toString())) {

                    System.err.println(properties_.get(i).getState().getLevel());
                    System.err.println(properties_.get(i).getNameAndStatus());
                    return false;
                }
            }
        }

        if (propertyCounter == PropertySpace.propertiesForMonopoly_[p.getTypeInt()]) {
            return true;
        }

        return false;
    }

    /**
     * Returns whether current property can be downgraded. Assumes monopoly is
     * already owned. Checks to make sure other properties in monopoly are not a
     * higher level
     *
     * @param UpgradeablePropertySpace that is being considered for upgrade
     * @return true if downgrading is valid; false otherwise
     */
    public boolean monopolyDowngradeValid(UpgradeablePropertySpace p) {

        for (int i = 0; i < properties_.size(); i++) {
            if (properties_.get(i).getTypeInt() == p.getTypeInt()) {

                // Check to make sure that other properties in monopoly are not in higher state
                if (properties_.get(i).getState().getLevel() > p.getState().getLevel()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * propertiesRenovated is used to determine whether the player owns any
     * properties of the same type that are currently renovated. The player
     * should not be able to mortgage their properties, if they own another
     * property of the same type that is renovated
     *
     * @param type integer used to associate colors of properties
     * @return true if there are other properties renovated, false otherwise
     */
    public boolean propertiesRenovated(int type) {
        for (int i = 0; i < properties_.size(); i++) {
            if (properties_.get(i).getTypeInt() == type
                    && properties_.get(i).getState().getLevel() != 0) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public boolean renovateProperty(UpgradeablePropertySpace p) {
        int renoCost = p.getRenovationCost_();
        if (cash_ >= renoCost && p.getState().getLevel() < 5) {

            updateCash(-renoCost);
            updatePropertiesValue(renoCost);
            p.renovate();
            NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE,
                    "The other player renovated " + p.getName());
            return true;
        }
        return false;
    }

    @Override
    public String AutomatedActions(Dice dice, Space[] board) {
        int position = this.getPosition();
        int toPurchasePropertyID = dice.getDefinedTurn().getPurchasePropertyID();
        int toRenovationPropertyID = dice.getDefinedTurn().getRenovationPropertyID();
        String actionResult = null;
        if (dice.getDefinedTurn().getPurchaseAction().equals("purchase")) {
            Random r1 = new Random();
            try {
                Thread.sleep(800 + r1.nextInt(12) * 150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (position == toPurchasePropertyID) {
                this.purchase((PropertySpace) board[position]);
                actionResult = dice.getDefinedTurn().getPurchaseAction() + "d " + board[position].getName();
            } else {
                //System.out.println("position != toPurchasePropertyID");
            }
        }
        if (dice.getDefinedTurn().getRenovationAction().equals("renovate")) {
            Random r1 = new Random();
            try {
                Thread.sleep(800 + r1.nextInt(12) * 150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (board[toRenovationPropertyID] instanceof UpgradeablePropertySpace) {
                this.renovateProperty((UpgradeablePropertySpace) board[toRenovationPropertyID]);
                actionResult = dice.getDefinedTurn().getRenovationAction() + "d " + board[toRenovationPropertyID].getName();
            } else {
                //System.out.println("Property cannot be upgraded");
            }

        }
        return actionResult;

    }

}
