/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.ui;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.vandopoly.controller.GameController;
import org.vandopoly.controller.NetworkedGameController;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/* 
 * Holds all game control buttons located next to the board in Vandopoly
 * The logic however is implemented in the GameController class
 * 
 * @author James Kasten
 */
public class GameButtonPanel extends JPanel {
	
	JButton purchase_, mortgage_, trade_, endTurn_, quitGame_;
	
	Font buttonFont;
	
	GameController controller_;
	NetworkedGameController networkedController_;
	
	int buttonX = 221, buttonY = 50;
	int frameWidth = buttonX * 5;
	int frameHeight = buttonY;
	
	private boolean purchaseState_, endTurnState_, tradeState_, mortgageState_;
	
	static final long serialVersionUID = 11;
	
	// Will handle all controls in the actual GameController class
	// GameController will have to implement ActionListener
	public GameButtonPanel(GameController controller) {
		
		controller_ = controller;
		
		buttonFont = new Font("broadway", Font.PLAIN, 18);
		
		// Set size of window
		this.setSize(frameWidth, frameHeight);
		
		// Allows for automatic positioning
		this.setLayout(null);
		
		// Set up the purchase button
		purchase_ = buttonCreator("Purchase", 0);
		purchase_.setEnabled(false);
		
		// Set up the mortgage button
		mortgage_ = buttonCreator("Manage Properties", 1);
		mortgage_.setEnabled(true);
		
		trade_ = buttonCreator("Trade", 2);
		trade_.setEnabled(true);
		
		// Set up the end turn button
		endTurn_ = buttonCreator("End Turn", 3);
		endTurn_.setEnabled(false);
		
		// Set up the quit game button
		quitGame_ = buttonCreator("Quit Game", 4);
				
		this.add(purchase_);
		this.add(mortgage_);
		this.add(trade_);
		this.add(endTurn_);
		this.add(quitGame_);
		
		// Add the buttons to the screen
		DisplayAssembler.getInstance().addComponent(this, 
				new Point(0, ((int)DisplayAssembler.getScreenHeight()) - 50),
				JLayeredPane.PALETTE_LAYER);
		
		// Sign up for the appropriate notifications
		NotificationManager.getInstance().addObserver(Notification.DONE_ROLLING, this, "playerState");
		NotificationManager.getInstance().addObserver(Notification.UNOWNED_PROPERTY, this, "enablePurchase");
		NotificationManager.getInstance().addObserver(Notification.END_TURN, this, "rollingState");
		NotificationManager.getInstance().addObserver(Notification.DISABLE_PURCHASE, this, "disablePurchase");
		NotificationManager.getInstance().addObserver(Notification.SHOW_CARD, this, "setAllDisabled");
		NotificationManager.getInstance().addObserver(Notification.REMOVE_CARD, this, "setEnabled");
		NotificationManager.getInstance().addObserver(Notification.END_TURN_EARLY, this, "playerState");
	}

	public GameButtonPanel(NetworkedGameController networkedGameController) {
		
		networkedController_ = networkedGameController;
		
		buttonFont = new Font("broadway", Font.PLAIN, 18);
		
		// Set size of window
		this.setSize(frameWidth, frameHeight);
		
		// Allows for automatic positioning
		this.setLayout(null);
		
		// Set up the purchase button
		purchase_ = networkedButtonCreator("Purchase", 0);
		purchase_.setEnabled(false);
		
		// Set up the mortgage button
		mortgage_ = networkedButtonCreator("Manage Properties", 1);
		mortgage_.setEnabled(true);
		
		trade_ = networkedButtonCreator("Trade", 2);
		trade_.setEnabled(true);
		
		// Set up the end turn button
		endTurn_ = networkedButtonCreator("End Turn", 3);
		endTurn_.setEnabled(false);
		
		// Set up the quit game button
		quitGame_ = networkedButtonCreator("Quit Game", 4);
				
		this.add(purchase_);
		this.add(mortgage_);
		this.add(trade_);
		this.add(endTurn_);
		this.add(quitGame_);
		
		// Add the buttons to the screen
		DisplayAssembler.getInstance().addComponent(this, 
				new Point(0, ((int)DisplayAssembler.getScreenHeight()) - 50),
				JLayeredPane.PALETTE_LAYER);
		
		// Sign up for the appropriate notifications
		NotificationManager.getInstance().addObserver(Notification.DONE_ROLLING, this, "playerState");
		NotificationManager.getInstance().addObserver(Notification.UNOWNED_PROPERTY, this, "enablePurchase");
		NotificationManager.getInstance().addObserver(Notification.END_TURN, this, "rollingState");
		NotificationManager.getInstance().addObserver(Notification.DISABLE_PURCHASE, this, "disablePurchase");
		NotificationManager.getInstance().addObserver(Notification.SHOW_CARD, this, "setAllDisabled");
		NotificationManager.getInstance().addObserver(Notification.REMOVE_CARD, this, "setEnabled");
		NotificationManager.getInstance().addObserver(Notification.END_TURN_EARLY, this, "playerState");
	}

	// Responsible for creating all the buttons in GameButtonPanel
	private JButton buttonCreator(String name, int buttonNumber) {
		JButton newButton = new JButton(name);
		newButton.setBounds((buttonX * buttonNumber), 0, buttonX, buttonY);
		newButton.setFont(buttonFont);
		newButton.setActionCommand(name);
		newButton.addActionListener(controller_);
		newButton.setVisible(true);
		return newButton;
	}
	
	// Responsible for creating all the buttons in GameButtonPanel
	private JButton networkedButtonCreator(String name, int buttonNumber) {
		JButton newButton = new JButton(name);
		newButton.setBounds((buttonX * buttonNumber), 0, buttonX, buttonY);
		newButton.setFont(buttonFont);
		newButton.setActionCommand(name);
		newButton.addActionListener(networkedController_);
		newButton.setVisible(true);
		return newButton;
	}
	
	// Meant to represent the panel state when the player is done rolling and making decisions
	// States may need to be more finely tuned based on actual space landed on
	public void playerState() {
		endTurn_.setEnabled(true);
	}
	
	// Gets called when the space landed on is available
	public void enablePurchase() {
		purchase_.setEnabled(true);
	}
	
	// Gets called after successful purchase, or when there are doubles and
	// the player must roll again
	public void disablePurchase() {
		purchase_.setEnabled(false);
	}
	
	// Represents the panel state when the player is rolling
	// ie. the player should not be able to end his turn early
	public void rollingState() {
		purchase_.setEnabled(false);
		endTurn_.setEnabled(false);
		
		mortgage_.setEnabled(true);
		trade_.setEnabled(true);
	}
	
	public void setAllDisabled() {
		purchaseState_ = purchase_.isEnabled();
		purchase_.setEnabled(false);
		
		mortgageState_ = mortgage_.isEnabled();
		mortgage_.setEnabled(false);
		
		tradeState_ = trade_.isEnabled();
		trade_.setEnabled(false);
		
		endTurnState_ = endTurn_.isEnabled();
		endTurn_.setEnabled(false);
	}
	
	public void setEnabled() {
		purchase_.setEnabled(purchaseState_);
		mortgage_.setEnabled(mortgageState_);
		trade_.setEnabled(tradeState_);
		endTurn_.setEnabled(endTurnState_);
	}
	
}
