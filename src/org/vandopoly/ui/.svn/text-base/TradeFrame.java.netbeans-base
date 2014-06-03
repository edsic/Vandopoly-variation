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
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;

import org.vandopoly.messaging.NetworkedMessageFilter;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.UtilitySpace;

/*
 * Trade Frame allows players to trade with one another.  Currently, it only supports 2 player property
 * trading, but can be extended to include 3-way trading and also allow for cash.
 * 
 * @author James Kasten
 */
public class TradeFrame implements ListSelectionListener {
	
	JLabel intro;
	JComboBox comboBox;
	JScrollPane scroll[][];
	DefaultListModel model[][];
	JList list[][];
	ListSelectionModel listSelection[][];
	Player2SelectionListener listListener2;
	
	JButton trade, add1_, remove1_, add2_, remove2_,accept_, cancel_;
	
	boolean isNetworked_ = false;
	NetworkedMessageFilter filter_ = null;
	
	// List of all players in the game
	ArrayList<Player> players_;
	
	// Players that actually trade
	ArrayList<Player> tradePlayer;
	
	// Lists % 2 == 0 are what the player keeps
	// Odd list numbers are properties the player is trading
	LinkedList<LinkedList<PropertySpace>> property;
	// Cash Array index % 2 is money the player keeps
	// Odd indices are cash involved in the trade
	int cash_[];
	
	int CASH_MOVEMENT = 50;
	
	JFrame frame;
	
	int panelWidth = 350;
	int panelHeight = 300;
	int buttonHeight = 50;
	
	// These variables are used after the player selects who he would like to trade with
	int newWidth = 900;
	int newHeight = 400;
	int pSpace = 25;
	int scrollWidth = (newWidth / 4) - pSpace;
	int acceptBuffer = 140;
	int buttonWidth = 150;
	
	Font nameFont = new Font("broadway", Font.PLAIN, 18);
	
	public TradeFrame(ArrayList<Player> players, final int currentPlayer, boolean isNetworked) {
		
		players_ = players;
		isNetworked_ = isNetworked;	
		
		frame = new JFrame("Trade Properties");
		frame.setSize(panelWidth, panelHeight);
		frame.setLayout(null);
		
		frame.setLocation((int)((DisplayAssembler.getScreenWidth() - panelWidth) / 2),
				(int)((DisplayAssembler.getScreenHeight() - panelHeight) / 2));
		
		intro = new JLabel("Trade with");
		intro.setFont(nameFont);
		intro.setBounds((panelWidth - buttonWidth) / 2, ((panelHeight - 30) / 2) - buttonHeight - 30, buttonWidth, 30);
		
		comboBox = new JComboBox();
		comboBox.setBounds((panelWidth - 150) / 2, ((panelHeight - 30) / 2) - buttonHeight, 150, 30);
		comboBox.setFont(nameFont);
		for (int i = 0; i < players_.size(); i++) {
			if (currentPlayer != i)
				comboBox.addItem(players_.get(i).getName());
		}
		
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		trade = new JButton("Trade");
		trade.setFont(buttonFont);
		trade.setBounds(0, panelHeight - (buttonHeight + 40), panelWidth, buttonHeight);
		trade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int i = 0;
				
				while (!players_.get(i).getName().equals(comboBox.getSelectedItem()))
					i++;
				
				setUpTradeScreen(players_.get(currentPlayer), players_.get(i));
			}
		});
		
		frame.add(intro);
		frame.add(comboBox);
		frame.add(trade);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public TradeFrame(ArrayList<Player> players, final int currentPlayer, boolean isNetworked, NetworkedMessageFilter filter) {	
		this(players, currentPlayer, isNetworked);
		
		filter_ = filter;
	}
	
	private void clearSetupScreen() {
		trade.setVisible(false);
		comboBox.setVisible(false);
		intro.setVisible(false);
	}
	
	private void setUpTradeScreen(Player player1, Player player2) {
		// Remove the initial screen
		clearSetupScreen();
		
		// Extra 10 was added as it appears the pixels are a bit off on Windows XP
		frame.setSize(newWidth + 10,newHeight);
		frame.setLocation((int)((DisplayAssembler.getScreenWidth() - newWidth) / 2),
				(int)((DisplayAssembler.getScreenHeight() - newHeight) / 2));
		
		// Represents the agents in the trade
		tradePlayer = new ArrayList<Player>();
		tradePlayer.add(player1);
		tradePlayer.add(player2);
		
		// These LinkedLists are used to represent the players properties in the scroll panes.
		property = new LinkedList<LinkedList<PropertySpace>>();
		// Represents the property holdings of player 1
		property.add(new LinkedList<PropertySpace>(tradePlayer.get(0).getProperties()));
		// Holds the trade properties of player 1
		property.add(new LinkedList<PropertySpace>());
		// Holds the properties held by player 2
		property.add(new LinkedList<PropertySpace>(tradePlayer.get(1).getProperties()));
		// Trade offer for player 2
		property.add(new LinkedList<PropertySpace>());
		
		cash_ = new int[4];
		cash_[0] = tradePlayer.get(0).getCash();
		cash_[1] = 0;
		cash_[2] = tradePlayer.get(1).getCash();
		cash_[3] = 0;
		
		// Add both players names to the top of the board
		JLabel player1Name = new JLabel(player1.getName());
		player1Name.setFont(nameFont);
		player1Name.setBounds(0, 0, 200, 30);
		player1Name.setVisible(true);
		
		// Font metrics is used to measure the length of the players name
		FontMetrics metrics = DisplayAssembler.getInstance().getFontMetrics(nameFont);
		JLabel player2Name = new JLabel(player2.getName());
		player2Name.setFont(nameFont);
		player2Name.setBounds(newWidth - metrics.stringWidth(player2.getName()), 0, 200, 30);
		player2Name.setVisible(true);
		
		setupListsandPanes();
		
		add1_ = new BasicArrowButton(SwingUtilities.EAST);
		add1_.setBounds(scrollWidth, (newHeight / 2) - pSpace, pSpace, pSpace);
		add1_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection[0][0].getAnchorSelectionIndex();
				
				// Represents case when no properties are selected
				if (index < 0) {
					return;
				}
				else if (index < property.get(0).size()){
					// Remove property from initial property list and add it to trade offer
					PropertySpace p = property.get(0).remove(index);
					addProperty(property.get(1), p);
					
					// Add a new element to the trade offer model
					model[0][1].addElement("");
					
					// Refresh the list
					for (int i = 0; i < property.get(1).size(); i++)
						model[0][1].set(i, property.get(1).get(i).getNameAndStatus());
					if (cash_[1] > 0)
						model[0][1].set( property.get(1).size(), "Cash: " + cash_[1]);
					
					model[0][0].remove(index);
					
				}
				// Item selected was cash
				else {
					int actualMovement;
					
					if (cash_[0] > CASH_MOVEMENT) {
						actualMovement = CASH_MOVEMENT;
						cash_[0] -= actualMovement;
						model[0][0].set(property.get(0).size(), "Cash: " + cash_[0]);
					}
					else {
						actualMovement = cash_[0];
						cash_[0] -= actualMovement;
						model[0][0].removeElementAt(property.get(0).size());
					}
					
					cash_[1] += actualMovement;
					
					// Signifies this is the initial movement
					if (cash_[1] == actualMovement)
						model[0][1].addElement("Cash: " + cash_[1]);
					else
						model[0][1].set(property.get(1).size(), "Cash: " + cash_[1]);
				}
				
				if (!TradeFrame.this.selectNextIndex(0, 0, index)) {
					add1_.setEnabled(false);
					remove1_.setEnabled(false);
				}
			}

		});
		
		remove1_ = new BasicArrowButton(SwingUtilities.WEST);
		remove1_.setFont(nameFont);
		remove1_.setBounds(scrollWidth, (newHeight / 2), pSpace, pSpace);
		remove1_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection[0][1].getAnchorSelectionIndex();
				
				// Represents case when no properties are selected
				if (index < 0) {
				}
				else if (index < property.get(1).size()) {
					PropertySpace p = property.get(1).remove(index);
					addProperty(property.get(0), p);
					
					model[0][0].addElement("");
					for (int i = 0; i < property.get(0).size(); i++)
						model[0][0].set(i, property.get(0).get(i).getNameAndStatus());
					if (cash_[0] > 0)
						model[0][0].set( property.get(0).size(), "Cash: " + cash_[0]);
					
					model[0][1].remove(index);
					
				}
				// Item selected was cash
				else {
					int actualMovement;
					
					if (cash_[1] > CASH_MOVEMENT) {
						actualMovement = CASH_MOVEMENT;
						cash_[1] -= actualMovement;
						model[0][1].set(property.get(1).size(), "Cash: " + cash_[1]);
					}
					else {
						actualMovement = cash_[1];
						cash_[1] -= actualMovement;
						model[0][1].removeElementAt(property.get(1).size());
					}
					
					cash_[0] += actualMovement;
					
					// Signifies this is the initial movement
					if (cash_[0] == actualMovement) {
						model[0][0].addElement("Cash: " + cash_[0]);
						ActionMessage.getInstance().newMessage("Add to end");
					}
					else
						model[0][0].set(property.get(0).size(), "Cash: " + cash_[0]);
				}
				
				if (!TradeFrame.this.selectNextIndex(0, 1, index)) {
					add1_.setEnabled(false);
					remove1_.setEnabled(false);
				}
			}

		});
		
		add2_ = new BasicArrowButton(SwingUtilities.WEST);
		add2_.setBounds(3 * (scrollWidth + pSpace), (newHeight / 2) - pSpace, pSpace, pSpace);
		add2_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection[1][0].getAnchorSelectionIndex();
				
				// Represents case when no properties are selected
				if (index < 0) {
				}
				else if (index < property.get(2).size()){
					PropertySpace p = property.get(2).remove(index);
					addProperty(property.get(3), p);
					
					model[1][1].addElement("");
					for (int i = 0; i < property.get(3).size(); i++)
						model[1][1].set(i, property.get(3).get(i).getNameAndStatus());
					if (cash_[3] > 0)
						model[1][1].set(property.get(3).size(), "Cash: " + cash_[3]);
					
					model[1][0].remove(index);
				}
				// Item selected was cash
				else {
					int actualMovement;
					
					if (cash_[2] > CASH_MOVEMENT) {
						actualMovement = CASH_MOVEMENT;
						cash_[2] -= actualMovement;
						model[1][0].set(property.get(2).size(), "Cash: " + cash_[2]);
					}
					else {
						actualMovement = cash_[2];
						cash_[2] -= actualMovement;
						model[1][0].removeElementAt(property.get(2).size());
					}
					
					cash_[3] += actualMovement;
					
					// Signifies this is the initial movement
					if (cash_[3] == actualMovement)
						model[1][1].addElement("Cash: " + cash_[3]);
					else
						model[1][1].set(property.get(3).size(), "Cash: " + cash_[3]);
				}
				
				if (!TradeFrame.this.selectNextIndex(1, 0, index)) {
					add2_.setEnabled(false);
					remove2_.setEnabled(false);
				}
			}

		});
		remove2_ = new BasicArrowButton(SwingUtilities.EAST);
		remove2_.setBounds(3 * (scrollWidth + pSpace), newHeight / 2, pSpace, pSpace);
		remove2_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection[1][1].getAnchorSelectionIndex();
				
				// Represents case when no properties are selected
				if (index < 0) {
				}
				else if (index < property.get(3).size()){
					PropertySpace p = property.get(3).remove(index);
					addProperty(property.get(2), p);
					
					model[1][0].addElement("");
					for (int i = 0; i < property.get(2).size(); i++)
						model[1][0].set(i, property.get(2).get(i).getNameAndStatus());
					if (cash_[2] > 0)
						model[1][0].set(property.get(2).size(), "Cash: " + cash_[2]);
					
					model[1][1].remove(index);
				}
				// Item selected was cash
				else {
					int actualMovement;
					
					if (cash_[3] > CASH_MOVEMENT) {
						actualMovement = CASH_MOVEMENT;
						cash_[3] -= actualMovement;
						model[1][1].set(property.get(3).size(), "Cash: " + cash_[3]);
					}
					else {
						actualMovement = cash_[3];
						cash_[3] -= actualMovement;
						model[1][1].removeElementAt(property.get(3).size());
					}
					
					cash_[2] += actualMovement;
					
					// Signifies this is the initial movement
					if (cash_[2] == actualMovement)
						model[1][0].addElement("Cash: " + cash_[2]);
					else
						model[1][0].set(property.get(2).size(), "Cash: " + cash_[2]);
				}
				
				if (!TradeFrame.this.selectNextIndex(1, 1, index)) {
					add2_.setEnabled(false);
					remove2_.setEnabled(false);
				}
			}

		});
		
		accept_ = new JButton("Accept");
		accept_.setFont(nameFont);
		accept_.setBounds(((newWidth - buttonWidth) / 2), 
				newHeight - ((acceptBuffer + buttonHeight) / 2), buttonWidth, buttonHeight);
		accept_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// Check if it is a valid trade
				if (!validTrade()) {
					NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
							"You Cannot Trade Renovated Properties", true);
					
					// Not an acceptable trade so do nothing else
					return;
				}
				
				// Gets iterator for Player 1's trade offer
				ListIterator<PropertySpace> itr = property.get(1).listIterator(property.get(1).size());
				
				if (!isNetworked_) {
										
					// Add the trade offer to player 2's properties and remove them from Player 1's
					while (itr.hasPrevious()) {
						PropertySpace p = itr.previous();
						
						if (p.getClass().equals(PropertySpace.class) || p.getClass().equals(UtilitySpace.class)) {
							p.getOwner().updateTypeDecrease(p.getTypeInt());
							
							// This changes all relevant states owned by the player
							p.bePurchased(tradePlayer.get(1));
						}
						
						tradePlayer.get(0).getProperties().remove(p);
						tradePlayer.get(1).updateProperties(p);
						p.setOwner(tradePlayer.get(1));
					}
					
					// Sends update notification because it isn't guaranteed tradePlayer0 
					// will gain any properties
					NotificationManager.getInstance().notifyObservers(
							Notification.UPDATE_PROPERTIES, tradePlayer.get(0));
					
					itr = property.get(3).listIterator(property.get(3).size());
					
					// Add the trade offer to player 1's properties and remove them from Player 2's
					while (itr.hasPrevious()) {
						PropertySpace p = itr.previous();
						
						if (p.getClass().equals(PropertySpace.class) || p.getClass().equals(UtilitySpace.class)) {
							p.getOwner().updateTypeDecrease(p.getTypeInt());
							
							// This changes all relevant states owned by the player
							p.bePurchased(tradePlayer.get(0));
						}
						
						tradePlayer.get(0).updateProperties(p);
						tradePlayer.get(1).getProperties().remove(p);
						p.setOwner(tradePlayer.get(0));
					}
					
					// Transfer cash
					tradePlayer.get(0).updateCash(cash_[3] - cash_[1]);
					tradePlayer.get(1).updateCash(cash_[1] - cash_[3]);
					
					// Sends update notification because it isn't guaranteed tradePlayer1 
					// will gain any properties
					NotificationManager.getInstance().notifyObservers(
							Notification.UPDATE_PROPERTIES, tradePlayer.get(1));
					
					frame.dispose();
				}
				else {
					// Create an ArrayList to pass over the wire
					ArrayList<String[]> tradeList = new ArrayList<String[]>(4);
					
					// Create the array of trade properties for player 2
					String player0_properties[] = new String[property.get(1).size()];
					int i = 0;
					while (itr.hasPrevious()) {
						PropertySpace p = itr.previous();
						player0_properties[i] = p.getName();
						++i;
					}
					
					itr = property.get(3).listIterator(property.get(3).size());
					
					// Create the array of trade properties for player 1
					String player1_properties[] = new String[property.get(3).size()];
					i = 0;
					while (itr.hasPrevious()) {
						PropertySpace p = itr.previous();
						player1_properties[i] = p.getName();
						++i;
					}
					
					// Create single-element arrays to hold cash
					String player0_cash[] = new String[1];
					player0_cash[0] = "" + cash_[1];
					String player1_cash[] = new String[1];
					player1_cash[0] = "" + cash_[3];
					
					// Add the arrays to the ArrayList for sending over the wire
					tradeList.add(player0_cash);
					tradeList.add(player0_properties);
					tradeList.add(player1_cash);
					tradeList.add(player1_properties);
					
					filter_.addToQueue(tradeList, Notification.TRADE_PROPOSED, false);
					
					NotificationManager.getInstance().notifyObservers(Notification.TRADE_WAIT_MESSAGE,
							null, true);
					
					frame.dispose();
					
				}
			}

			public boolean validTrade() {
				ListIterator<PropertySpace> itr = property.get(1).listIterator(property.get(1).size());
				
				// Check to make sure there are no renovated properties in trade offer
				while (itr.hasPrevious()) {
					PropertySpace p = itr.previous();
					if (p.isRenovated())
						return false;
				}
				
				itr = property.get(3).listIterator(property.get(3).size());
				
				while (itr.hasPrevious()) {
					PropertySpace p = itr.previous();
					if (p.isRenovated())
						return false;
				}
				
				return true;
			}
		});
		cancel_ = new JButton("Cancel");
		
		frame.getContentPane().add(player1Name);
		frame.getContentPane().add(player2Name);
		frame.getContentPane().add(add1_);
		frame.getContentPane().add(remove1_);
		frame.getContentPane().add(add2_);
		frame.getContentPane().add(remove2_);
		frame.getContentPane().add(accept_);
	}
	
	private void setupListsandPanes() {
		// initialize all the arrays
		scroll = new JScrollPane[2][2];
		model = new DefaultListModel[2][2];
		list = new JList[2][2];
		listSelection = new ListSelectionModel[2][2];
		
		for (int i = 0; i < 2; i++) {
			// initialize all models
			model[i][0] = new DefaultListModel();
			model[i][1] = new DefaultListModel();
			
			// Make models imitate property lists
			for (int j = 0; j < tradePlayer.get(i).getProperties().size(); j++)
				model[i][0].add(j, tradePlayer.get(i).getProperties().get(j).getNameAndStatus());
			
			// Add appropriate cash value onto last space of model
			if (cash_[i * 2] > 0)
				model[i][0].add(tradePlayer.get(i).getProperties().size(), "Cash: " + cash_[i * 2]);
			
			// initialize lists and scroll panes with the lists
			list[i][0] = new JList(model[i][0]);
			list[i][1] = new JList(model[i][1]);
			scroll[i][0] = new JScrollPane(list[i][0]);
			scroll[i][1] = new JScrollPane(list[i][1]);
			
			// Set up single selection on all lists
			listSelection[i][0] = list[i][0].getSelectionModel();
			listSelection[i][0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
			listSelection[i][1] = list[i][1].getSelectionModel();
			listSelection[i][1].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		
		// Player 2's listener
		listListener2 = new Player2SelectionListener();
		
		// Add Player 1's actionListener as this class
		listSelection[0][0].addListSelectionListener(this);	
		listSelection[0][1].addListSelectionListener(this);
		
		// Add Player 2's actionListener as a different inner class
		// Used different listeners so that finding the source / controlling the buttons
		// would be easier
		listSelection[1][0].addListSelectionListener(listListener2);
		listSelection[1][1].addListSelectionListener(listListener2);
		
		// [0][0] is the initial property list of the player who initiated the trade
		scroll[0][0].setBounds(0, 30, scrollWidth, newHeight - acceptBuffer);
		scroll[0][0].setBorder(new TitledBorder("Properties Owned"));
		
		// [0][1] is the trade offer for the player who initiated the trade
		scroll[0][1].setBounds(scrollWidth + pSpace, 30, scrollWidth, newHeight - acceptBuffer);
		scroll[0][1].setBorder(new TitledBorder("Trade Offer"));
		
		// [1][1] is the Trade offer of the player who was chosen to trade
		scroll[1][1].setBounds(2 * (scrollWidth + pSpace) + pSpace, 30, scrollWidth, newHeight - acceptBuffer);
		scroll[1][1].setBorder(new TitledBorder("Trade Offer"));
		
		// [1][0] is the initial property list of the player who was chosen to trade
		scroll[1][0].setBounds((3 * (scrollWidth + pSpace) +  pSpace), 30, scrollWidth, newHeight - acceptBuffer);
		scroll[1][0].setBorder(new TitledBorder("Properties Owned"));
		
		// Add all the players scroll panes to the frame
		for (int i = 0; i < tradePlayer.size(); i++) {
			frame.getContentPane().add(scroll[i][0]);
			frame.getContentPane().add(scroll[i][1]);
		}
	}
	
	// Listener for Player1
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		
		// 0 for initial properties 1 for trade offer
		int listType = 0;
		
		// Clear the other panel of any selection
		if (lsm.equals(listSelection[0][0])) {
			listSelection[0][1].clearSelection();
			listType = 0;
		}
		else if (lsm.equals(listSelection[0][1])) {
			listSelection[0][0].clearSelection();
			listType = 1;
		}
		else
			System.err.println("TradeFrame: Listener for Player1");
		
		toggleButtonsPlayer1(listType);
	}
	
	private void toggleButtonsPlayer1(int listType) {
		if (listType == 0) {
			add1_.setEnabled(true);
			remove1_.setEnabled(false);
		}
		else {
			add1_.setEnabled(false);
			remove1_.setEnabled(true);
		}
		
	}
	
	/*
	 * This class is used for Player2's ListSelectionListener
	 */
	class Player2SelectionListener implements ListSelectionListener {
	    public void valueChanged(ListSelectionEvent e) { 
	    	ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			
			int listType = 0;
			
			// Clear the other panel of any selection
			if (lsm.equals(listSelection[1][0])) {
				listSelection[1][1].clearSelection();
				listType = 0;
			}
			else if (lsm.equals(listSelection[1][1])) {
				listSelection[1][0].clearSelection();
				listType = 1;
			}
			else
				System.err.println("TradeFrame: Listener for Player2");
			
			toggleButtonsPlayer2(listType);

	    }
	}
	
	private void toggleButtonsPlayer2(int listType) {
		if (listType == 0) {
			add2_.setEnabled(true);
			remove2_.setEnabled(false);
		}
		else {
			add2_.setEnabled(false);
			remove2_.setEnabled(true);
		}
		
	}
	
	public boolean selectNextIndex(int player, int listType, int index) {
		// Now set up the new selected index
		if (model[player][listType].size() > index)
			list[player][listType].setSelectedIndex(index);
		// Currently selected index can be decreased
		else if (index > 0)
			list[player][listType].setSelectedIndex(index - 1);
		// list is empty...
		else {
			list[player][listType].setSelectedIndex(-1);
			return false;
		}
		return true;
	}

	// Adds elements to the property list in the correct order -
	// sorted first by type (color), and then by space number
	public void addProperty(LinkedList<PropertySpace> properties, PropertySpace p) {
		if (properties.size() == 0)
			properties.add(p);
		else {
			//Start iterator at the end of the list - for proper insertion we must traverse
			// the list from right to left
			ListIterator<PropertySpace> itr = properties.listIterator(properties.size());
			PropertySpace tempSpace = null;
			while (itr.hasPrevious()) {
				tempSpace = itr.previous(); 
				//Found properties of the same type, so insert based on space number
				if (p.getTypeInt() == tempSpace.getTypeInt()) {
					if (p.getSpaceNumber() < tempSpace.getSpaceNumber())
						properties.add(itr.nextIndex(), p);
					else
						properties.add(1 + itr.nextIndex(), p);
					
					break;
				}
				// Found property with a type less than 'property''s type - add after
				else if (p.getTypeInt() > tempSpace.getTypeInt()) {
					properties.add(1 + (itr.nextIndex()), p);
					break;
				}
			}
			// Property has the least type on the list, insert at the beginning
			if (p.getTypeInt() < tempSpace.getTypeInt())
				properties.add(itr.nextIndex(), p);
		}
	}
	
	public void dispose() {
		frame.dispose();
	}
}