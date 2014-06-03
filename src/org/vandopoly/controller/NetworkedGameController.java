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

package org.vandopoly.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.messaging.NetworkedMessage;
import org.vandopoly.messaging.NetworkedMessageFilter;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.ChanceCardSpace;
import org.vandopoly.model.CommCardSpace;
import org.vandopoly.model.CornerSpace;
import org.vandopoly.model.Dice;
import org.vandopoly.model.Player;
import org.vandopoly.model.PlayerInJail;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.Space;
import org.vandopoly.model.TaxSpace;
import org.vandopoly.model.UpgradeablePropertySpace;
import org.vandopoly.model.UtilitySpace;
import org.vandopoly.ui.ActionMessage;
import org.vandopoly.ui.DicePanel;
import org.vandopoly.ui.Display;
import org.vandopoly.ui.DisplayAssembler;
import org.vandopoly.ui.GameButtonPanel;
import org.vandopoly.ui.JailPopUp;
import org.vandopoly.ui.MessagePopUp;
import org.vandopoly.ui.Piece;
import org.vandopoly.ui.PlayerPanel;
import org.vandopoly.ui.PropertySelectionPanel;
import org.vandopoly.ui.TradeFrame;
import org.vandopoly.ui.TradeProposalPopUp;
import org.vandopoly.ui.WaitingMessage;

/*
 * NetworkedGameController is meant to handle all complex button/model 
 * interactions in a networked game. 
 * NetworkedGameController should have access to all real models and control 
 * game state.
 * 
 *  @author Allie Mazzia
 */
public class NetworkedGameController implements ActionListener {

	Dice dice_;
	ArrayList<Player> players_;
	ArrayList<Piece> pieces_;
	Space board_[];
	int scholarshipFund_;

	Display display_;
	DicePanel dicePanel_;
	GameButtonPanel buttonPanel_;
	PlayerPanel playerPanel_;
	PropertySelectionPanel propertySelectionPanel_;
	TradeFrame tradeFrame_;
	WaitingMessage tradeWait;

	String[] namesAndIcons_;
	int numOfPlayers_ = 2;

	ChanceCardSpace chance_;
	CommCardSpace commChest_;

	final int NUM_OF_SPACES = 40;

	// Suggested integer for keeping track of the state of game
	int currentPlayerNum_;

	private ServerSocket serverSocket_ = null;
	private Socket clientSocket_ = null;
	private PrintWriter printOut_ = null;
	private BufferedReader readIn_ = null;
	private ObjectInputStream objectInput_ = null;
	private ObjectOutputStream objectOutput_ = null;
	private NetworkedMessageFilter filter_ = null;

	private boolean localControl_ = true;
	public boolean isServer_;
	Player localPlayer_;
	
	public NetworkedGameController(Display display, String[] namesAndIcons, boolean isServer) {
			
		isServer_ = isServer;
		namesAndIcons_ = namesAndIcons;
		if (isServer) {
			createServer();
			localControl_ = true;
		} else	
			localControl_ = false;

		dice_ = new Dice();
		board_ = new Space[NUM_OF_SPACES];

		display_ = display;
		
		tradeWait = new WaitingMessage("Waiting for opponent to make a decision regarding the trade");

		NotificationManager.getInstance().addObserver(Notification.START_GAME, this, "startGame");
		NotificationManager.getInstance().addObserver(Notification.UPDATE_SCHOLARSHIP_FUND, this, "updateFund");
		NotificationManager.getInstance().addObserver(Notification.AWARD_SCHOLARSHIP_FUND, this, "awardFund");
		NotificationManager.getInstance().addObserver(Notification.DICE_ANIMATION_DONE, this, "moveCurrentPlayer");
		NotificationManager.getInstance().addObserver(Notification.GO_TO_JAIL, this, "sendPlayerToJail");
		NotificationManager.getInstance().addObserver(Notification.CARD_MOVE, this, "cardMoveTo");
		NotificationManager.getInstance().addObserver(Notification.UNOWNED_PROPERTY, this, "unownedProperty");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_SPACES, this, "pieceMoveSpaces");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_TO, this, "pieceMoveTo");
		NotificationManager.getInstance().addObserver(Notification.ACTION_MESSAGE, this, "displayActionMessage");
		NotificationManager.getInstance().addObserver(Notification.UTILITY_RENT, this, "chargeUtilityRent");
		NotificationManager.getInstance().addObserver(Notification.END_TURN, this, "endTurn");
		NotificationManager.getInstance().addObserver(Notification.END_TURN_UPDATE, this, "overwrite");
		NotificationManager.getInstance().addObserver(Notification.TRADE_PROPOSED, this, "tradeProposal");
		NotificationManager.getInstance().addObserver(Notification.TRADE_ACCEPTED, this, "tradeAccepted");
		NotificationManager.getInstance().addObserver(Notification.MESSAGE_POPUP, this, "messagePopUp");
		NotificationManager.getInstance().addObserver(Notification.REMOVE_PLAYER, this, "removePlayer");
		NotificationManager.getInstance().addObserver(Notification.TRADE_FINALIZED, this, "tradeOverwrite");
		NotificationManager.getInstance().addObserver(Notification.TRADE_REJECTED, this, "tradeRejected");
		NotificationManager.getInstance().addObserver(Notification.TRADE_WAIT_MESSAGE, this, "showTradeWaitMessage");
	}

	public void clientListen(BufferedReader reader, PrintWriter writer, ObjectInputStream input, ObjectOutputStream output) {

		NetworkedMessage message = null;
		readIn_ = reader;
		printOut_ = writer;
		objectInput_ = input;
		objectOutput_ = output;
		
		filter_ = new NetworkedMessageFilter();
		
		// Create a new thread to send objects as necessary
		new Thread("sendToServer") {
			public void run() {
				while (true) {	
					try {
						NetworkedMessage tempMessage = filter_.queueRemove();
						if (tempMessage != null) {
							objectOutput_.writeObject(tempMessage);
							objectOutput_.reset();
							System.out.println("Client: Sending object: " + tempMessage.getString());
						}
					} catch (SocketException e) {
						System.out.println("Send to server: Server socket closed");
						System.out.println("listen to server: Server Socket closed.");
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			}
		}.start();

		while (true) {
			// Create the object streams to pass messages along
			try {
				// Listen for objects (messages)
				message = (NetworkedMessage) objectInput_.readObject();

				System.out.println("Client: Notifying of:" + message.getString());
				NotificationManager.getInstance().notifyObservers(message.getString(), message.getObject(), true);

			} catch (SocketException e) {
				System.out.println("listen to server: Server Socket closed.");
				new MessagePopUp("Your opponent has left the game!");
				buttonPanel_.setAllDisabled();
				dicePanel_.setDisabled();
				break;
			}catch (IOException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException in clientListen()");
			}
		}

	}

	// Called by REMOVE_PLAYER to remove the current player
	public void removePlayer() {
		try {
			pieces_.get(currentPlayerNum_).removePiece();
			pieces_.remove(currentPlayerNum_);
			players_.remove(currentPlayerNum_);
			playerPanel_.removePanel(currentPlayerNum_);
			
			if(players_.size() == 1) {
				ActionMessage.getInstance().newMessage(players_.get(0).getName() + " Won!");
				buttonPanel_.setAllDisabled();
				dicePanel_.setDisabled();
			} else {
				NotificationManager.getInstance().notifyObservers(Notification.REMOVED_PLAYER, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Called by REMOVED_PLAYER notification to update the player's turn
	public void removedPlayer() {
		numOfPlayers_--;
		
		currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
		
		disposeFrames();
		
		NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));
	}

	// Called by the START_GAME notification
	public void startGame(Object obj) {
		System.out.println("StartGame called");
		numOfPlayers_ = Integer.parseInt(namesAndIcons_[0]);
		System.out.println("Called create Players");
		createPlayers();
		System.out.println("Returned from create Players");
		if (isServer_)
			localPlayer_ = players_.get(0);
		else
			localPlayer_ = players_.get(1);
		
		createSpaces();

		shuffleCards();

		playerPanel_ = new PlayerPanel(players_);
		dicePanel_ = new DicePanel(dice_);
		buttonPanel_ = new GameButtonPanel(this);

		scholarshipFund_ = 500;

		if (localControl_ == false) {
			dicePanel_.setDisabled();
			buttonPanel_.setAllDisabled();
		}
	}

	public void createServer() {
		new Thread("startGame") {
			public void run() {
				String temp = null;

				try {

					// Initialize the ServerSocket, which listens to the socket
					serverSocket_ = new ServerSocket(3913);
					System.out.println("Created serversocket");

					// Blocks until a connection is made
					clientSocket_ = serverSocket_.accept();
					System.out.println("Recieved connection attempt");

					// Create the object streams to pass messages along\
					objectOutput_ = new ObjectOutputStream(clientSocket_.getOutputStream());
					objectOutput_.flush();
					objectInput_ = new ObjectInputStream(clientSocket_.getInputStream());
					printOut_ = new PrintWriter(clientSocket_.getOutputStream(), true);
					readIn_ = new BufferedReader(new InputStreamReader(clientSocket_.getInputStream()));
					System.out.println("Created streams");

					// Read from the socket to accept a join game request
					temp = readIn_.readLine();

					if (temp.equals("JOIN")) {
						System.out.println("Server read join game");
						printOut_.println("ACCEPTED");

						// Send player 1's name and piece info
						printOut_.println(namesAndIcons_[1]);
						printOut_.println(namesAndIcons_[3]);

						// Receive player 2's name and piece info
						namesAndIcons_[2] = readIn_.readLine();
						namesAndIcons_[4] = readIn_.readLine();

						System.out.println("Name: " + namesAndIcons_[2]);
						System.out.println("Icon: " + namesAndIcons_[4]);

						temp = readIn_.readLine();
						if (temp.equals("START")) {
							// START GAME
							System.out.println("STARTING GAME");
							NotificationManager.getInstance().notifyObservers(Notification.START_GAME, namesAndIcons_);

							objectOutput_.writeObject(new NetworkedMessage(Notification.START_GAME, null));

							// Continue sending notifications across to the
							// client
							filter_ = new NetworkedMessageFilter();
							new Thread("sendToClient") {
								public void run() {
									while (true) {
										try {
											NetworkedMessage tempMessage = filter_.queueRemove();
											if (tempMessage != null) {
												System.err.println("Sending object:" + tempMessage.getString());
												
												objectOutput_.writeObject(tempMessage);
												objectOutput_.reset();
												System.out.println("Server: Sending object: " + tempMessage.getString());
											}											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
							}.start();

							// Read in from the client
							while (true) {
								NetworkedMessage tempMessage = null;
								tempMessage = (NetworkedMessage) objectInput_.readObject();
								System.out.println("Server: Notifying of: " + tempMessage.getString());
								NotificationManager.getInstance().notifyObservers(tempMessage.getString(), tempMessage.getObject(), true);
							}
						}
					}
					closeSockets();
				} catch (SocketException e) { 
					System.out.println("ServerSocket has closed");
					new MessagePopUp("Your opponent has left the game!");
					buttonPanel_.setAllDisabled();
					dicePanel_.setDisabled();
				} catch (UnknownHostException e) {
					System.out.println("Cannot find host");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void moveCurrentPlayer(Object obj) {

		try {
			Dice dice = (Dice) obj;
			Player currentPlayer = players_.get(currentPlayerNum_);

			// Update current position of player model
			currentPlayer.movePiece(dice);
			// Kept for testing purposes
			// currentPlayer.movePiece(2);

			/*
			 * //Print out some statements that help testing
			 * System.out.println("Current Player: "+currentPlayer.getName());
			 * System.out.println("Dice Roll: "+dice.getTotalRoll());
			 * System.out.
			 * println("Giving a position of: "+currentPlayer.getPosition()+
			 * " which is "+board_[currentPlayer.getPosition()].getName());
			 */

			// Have currentPlayer landOn the appropriate board position
			board_[currentPlayer.getPosition()].landOn(currentPlayer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Same as moveCurrentPlayer except that it expects an Integer to be passed
	// to it
	public void moveCurrentPlayerInteger(Object obj) {

		try {
			Integer numSpaces = (Integer) obj;
			Player currentPlayer = players_.get(currentPlayerNum_);

			// Update current position of player model
			currentPlayer.movePiece(numSpaces);

			// Have currentPlayer landOn the appropriate board position
			board_[currentPlayer.getPosition()].landOn(currentPlayer);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Called by PIECE_MOVE_TO notification
	// Avoids player needing access to piece to allow for unit testing
	public void pieceMoveTo(Object obj) {
		Integer spaceNum = (Integer) obj;
		pieces_.get(currentPlayerNum_).moveToSpace(spaceNum);
	}

	// Called by PIECE_MOVE_SPACES notification
	// Avoids player needing access to piece to allow for unit testing
	public void pieceMoveSpaces(Object obj) {
		Integer spaceNum = (Integer) obj;
		pieces_.get(currentPlayerNum_).move(spaceNum);
	}

	public void sendPlayerToJail() {
		players_.get(currentPlayerNum_).goToJail();
		NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
				(players_.get(currentPlayerNum_).getName() + " has been sent to Academic Probation!"));
	}

	// Called by the UpdateScholarship notification
	public void updateFund(Object obj) {
		Integer value = (Integer) obj;

		if ((scholarshipFund_ + value.intValue()) > 0)
			scholarshipFund_ += value.intValue();
		else
			System.err.println("Attempted to remove more money from scholarship than there currently is");
	}

	// Called by the Award Scholarship Fund notification
	public void awardFund(Object obj) {
		Player player = (Player) obj;

		new MessagePopUp(player.getName() + " has been awarded $" + 
				scholarshipFund_ + " from the Scholarship Fund");
		filter_.addToQueue(player.getName() + " has been awarded $" + 
				scholarshipFund_ + " from the Scholarship Fund", Notification.ACTION_MESSAGE, false);
		player.updateCash(scholarshipFund_);
		scholarshipFund_ = 500;
	}

	private void createPlayers() {
		players_ = new ArrayList<Player>();
		pieces_ = new ArrayList<Piece>();

		for (int i = 0; i < namesAndIcons_.length; i++)
			System.out.println(namesAndIcons_[i]);
		
		for (int i = 0; i < numOfPlayers_; i++) {
			
			players_.add(new Player(i, namesAndIcons_[i + 1]));
			pieces_.add(new Piece(namesAndIcons_[numOfPlayers_ + i + 1], i + 1));
		}

		// Enables easy testing
		if (namesAndIcons_[1].equals("test"))
			cheatMode();
	}

	// Return the int of who's turn it is
	public int getTurn() {
		return currentPlayerNum_;
	}
	
	// Called by Notification UTILITY_RENT
	public void chargeUtilityRent(Object obj) {
		UtilitySpace property = (UtilitySpace) obj;
		int fee = property.getMultiplier() * dice_.getTotalRoll();

		property.getOwner().collectRent(fee, players_.get(currentPlayerNum_));
	}

	private void createSpaces() {
		chance_ = ChanceCardSpace.Instance(players_);
		commChest_ = CommCardSpace.Instance(players_);

		// set 1
		board_[0] = new CornerSpace("GO");
		board_[1] = new UpgradeablePropertySpace("Dyer Hall", 0, 1, 60, 30, 2, 10, 30, 90, 160, 250, 50);
		board_[2] = commChest_;
		board_[3] = new UpgradeablePropertySpace("Mims Hall", 0, 3, 60, 30, 4, 20, 60, 180, 320, 450, 50);
		board_[4] = new TaxSpace("Pay Tuition");
		board_[5] = new PropertySpace("Vandy Van Reverse Route", 8, 5, 200, 100, 25, 50, 100, 200);
		board_[6] = new UpgradeablePropertySpace("Tolman Hall", 1, 6, 100, 30, 6, 30, 90, 270, 400, 550, 50);
		board_[7] = chance_;
		board_[8] = new UpgradeablePropertySpace("Cole Hall", 1, 8, 100, 30, 6, 30, 90, 270, 400, 550, 50);
		board_[9] = new UpgradeablePropertySpace("McGill Hall", 1, 9, 120, 30, 8, 40, 100, 300, 450, 600, 50);
		board_[10] = new CornerSpace("Academic Probation");
		board_[11] = new UpgradeablePropertySpace("Furman Hall", 2, 11, 140, 70, 10, 50, 150, 450, 625, 750, 100);
		board_[12] = new UtilitySpace("CoGeneration Plant", 9, 12, 150, 75);
		board_[13] = new UpgradeablePropertySpace("Wilson Hall", 2, 13, 140, 70, 10, 50, 150, 450, 625, 750, 100);
		board_[14] = new UpgradeablePropertySpace("Buttrick Hall", 2, 14, 160, 80, 12, 60, 180, 500, 700, 900, 100);
		board_[15] = new PropertySpace("Vandy Van Long Route", 8, 15, 200, 100, 25, 50, 100, 200);
		board_[16] = new UpgradeablePropertySpace("Lewis House", 3, 16, 180, 90, 14, 70, 200, 550, 750, 950, 100);
		board_[17] = commChest_;
		board_[18] = new UpgradeablePropertySpace("Morgan House", 3, 18, 180, 90, 14, 70, 200, 550, 750, 950, 100);
		board_[19] = new UpgradeablePropertySpace("Chaffin Place", 3, 19, 200, 100, 16, 80, 220, 600, 800, 1000, 100);
		board_[20] = new CornerSpace("Scholarship Fund");
		board_[21] = new UpgradeablePropertySpace("Kirkland Hall", 4, 21, 220, 110, 18, 90, 250, 700, 875, 1050, 150);
		board_[22] = chance_;
		board_[23] = new UpgradeablePropertySpace("Wyatt Center", 4, 23, 220, 110, 18, 90, 250, 700, 875, 1050, 150);
		board_[24] = new UpgradeablePropertySpace("Featheringill Hall", 4, 24, 240, 120, 20, 100, 300, 750, 925, 1100, 150);
		board_[25] = new PropertySpace("Vandy Van Normal Route", 8, 25, 200, 100, 25, 50, 100, 200);
		board_[26] = new UpgradeablePropertySpace("Sarratt Student Center", 5, 26, 260, 130, 22, 110, 330, 800, 975, 1150, 150);
		board_[27] = new UpgradeablePropertySpace("Student Life Center", 5, 27, 260, 130, 22, 110, 330, 800, 975, 1150, 150);
		board_[28] = new UtilitySpace("BioDiesel Initiative", 9, 28, 150, 75);
		board_[29] = new UpgradeablePropertySpace("Ingram Center", 5, 29, 280, 140, 24, 120, 360, 850, 1025, 1200, 150);
		board_[30] = new CornerSpace("Go On Academic Probation");
		board_[31] = new UpgradeablePropertySpace("Murray House", 6, 31, 300, 150, 26, 130, 390, 900, 1100, 1275, 200);
		board_[32] = new UpgradeablePropertySpace("Stambaugh House", 6, 32, 300, 150, 26, 130, 390, 900, 1100, 1275, 200);
		board_[33] = commChest_;
		board_[34] = new UpgradeablePropertySpace("Hank Ingram House", 6, 34, 320, 160, 28, 150, 450, 1000, 1200, 1400, 200);
		board_[35] = new PropertySpace("Vandy Van Express Route", 8, 35, 200, 100, 25, 50, 100, 200);
		board_[36] = chance_;
		board_[37] = new UpgradeablePropertySpace("McGugin Center", 7, 37, 350, 175, 35, 175, 500, 1100, 1300, 1500, 200);
		board_[38] = new TaxSpace("Parking Ticket");
		board_[39] = new UpgradeablePropertySpace("Commons Center", 7, 39, 400, 200, 50, 200, 600, 1400, 1700, 2000, 200);

		display_.showBoard(board_);

	}

	public void shuffleCards() {
		chance_.shuffleCards();
		commChest_.shuffleCards();
	}

	// Represents the logic for the GameButtonPanel class
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Purchase")) {
			// This stops players from opening a propertySelectionPanel, then buying a property
			// and adjusting the levels inappropriately
			disposeFrames();

			int position = players_.get(currentPlayerNum_).getPosition();
			players_.get(currentPlayerNum_).purchase((PropertySpace) board_[position]);
		} else if (action.getActionCommand().equals("Manage Properties")) {
			// If the propertySelectionPanel has already been created, dispose and get a new one
			// This makes sure the panel is fully updated, and allows only a single propertySelection
			// panel at a time
			disposeFrames();

			propertySelectionPanel_ = new PropertySelectionPanel(players_.get(currentPlayerNum_));
		} else if (action.getActionCommand().equals("Trade")) {
			disposeFrames();
			// All should be updated correctly before trading, so send  
			// an overwrite to the other player
			filter_.addToQueue(players_, Notification.END_TURN_UPDATE, false);
			tradeFrame_ = new TradeFrame(new ArrayList<Player>(players_), currentPlayerNum_, true, filter_);
			
		} else if (action.getActionCommand().equals("End Turn")) {
			// Change the current player
			currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
			
			disposeFrames();
			
			NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));
			
		} else if (action.getActionCommand().equals("Quit Game")) {
			confirmationPopUp();
		}
	}

	// Kill off all floating frames
	public void disposeFrames() {
		if (propertySelectionPanel_ != null) {
			propertySelectionPanel_.dispose();
			propertySelectionPanel_ = null;
		}
		if (tradeFrame_ != null) {
			tradeFrame_.dispose();
			tradeFrame_ = null;
		}
	}
	
	// Called by Notification TRADE_WAIT_MESSAGE
	public void showTradeWaitMessage() {
		tradeWait.showWaitingMessage();
	}
	
	// Used to display a message containing a trade from the other player. 
	// Called by Notification TRADE_PROPOSED
	public void tradeProposal(Object obj) {
		
		ArrayList<String[]> trades = (ArrayList<String[]>) obj;
		
		Player proposingPlayer = null;
		if (isServer_)
			proposingPlayer = players_.get(1);
		else
			proposingPlayer = players_.get(0);
		
		new TradeProposalPopUp(trades, proposingPlayer);
		
	}
	
	// Used to update players after a trade. Called by Notification TRADE_ACCEPTED
	public void tradeAccepted(Object obj) {
		System.out.println("Called acceptTrade");
		System.out.println("Player0 = " + players_.get(0).getName());
		System.out.println("Player1 = " + players_.get(1).getName());
		ArrayList<String[]> trades = (ArrayList<String[]>) obj;
		
		int proposingPlayerIndex = 0;
		int receivingPlayerIndex = 0;
		if (players_.get(0).getName() == trades.get(4)[0]) {
			System.out.println("Proposing Player = " + players_.get(0).getName());
			proposingPlayerIndex = 0;
			receivingPlayerIndex = 1;
		}
		else {
			System.out.println("Proposing Player = " + players_.get(1).getName());
			proposingPlayerIndex = 1;
			receivingPlayerIndex = 0;
		}
		int cash0 = Integer.parseInt(trades.get(0)[0]);
		String[] properties0 = trades.get(1);
		int cash1 = Integer.parseInt(trades.get(2)[0]);
		String[] properties1 = trades.get(3);
		
		System.out.println("Properties0:");
		for (int i = 0; i < properties0.length; ++i) {
			System.out.println(properties0[i]);
		}
		System.out.println("Properties1:");
		for (int i = 0; i < properties1.length; ++i) {
			System.out.println(properties1[i]);
		}
		
		// Add the trade offer to player 2's properties and remove them from Player 1's
		for (int i = 0; i < properties0.length; ++i) {
			for (int k = 0; k < board_.length; ++k) {
				if (properties0[i].equalsIgnoreCase(board_[k].getName())) {
					
					PropertySpace p = (PropertySpace)board_[k];
					
					if (p.getClass().equals(PropertySpace.class) || p.getClass().equals(UtilitySpace.class)) {
						
						p.getOwner().updateTypeDecrease(p.getTypeInt());
						
						// This changes all relevant states owned by the player
						p.bePurchased(players_.get(receivingPlayerIndex));
					}
					
					players_.get(proposingPlayerIndex).getProperties().remove(board_[k]);
					players_.get(receivingPlayerIndex).updateProperties_withoutNotification((PropertySpace)board_[k]);
					((PropertySpace)board_[k]).setOwner(players_.get(receivingPlayerIndex));
				}
			}
		}
		
		// Sends update notification because it isn't guaranteed tradePlayer0 
		// will gain any properties
		NotificationManager.getInstance().notifyObservers(
				Notification.UPDATE_PROPERTIES, new Player(players_.get(proposingPlayerIndex)));
		
		// Add the trade offer to player 1's properties and remove them from Player 2's
		for (int i = 0; i < properties1.length; ++i) {
			for (int k = 0; k < board_.length; ++k) {
				if (properties1[i].equalsIgnoreCase(board_[k].getName())) {
					
					PropertySpace p = (PropertySpace)board_[k];
					
					if (p.getClass().equals(PropertySpace.class) || p.getClass().equals(UtilitySpace.class)) {
						
						p.getOwner().updateTypeDecrease(p.getTypeInt());
						
						// This changes all relevant states owned by the player
						p.bePurchased(players_.get(proposingPlayerIndex));
					}
					
					players_.get(receivingPlayerIndex).getProperties().remove(board_[k]);
					players_.get(proposingPlayerIndex).updateProperties_withoutNotification((PropertySpace)board_[k]);
					((PropertySpace)board_[k]).setOwner(players_.get(proposingPlayerIndex));
				}
			}
		}
		
		// Transfer cash
		System.out.println(players_.get(proposingPlayerIndex).getName() + " is updated to be: " + 
				(cash1-cash0)+ " from current:" + players_.get(proposingPlayerIndex).getCash());
		System.out.println(players_.get(receivingPlayerIndex).getName() + " is updated to be: " + 
				(cash0-cash1)+ " from current:" + players_.get(receivingPlayerIndex).getCash());
		players_.get(proposingPlayerIndex).updateCash(cash1 - cash0);
		players_.get(receivingPlayerIndex).updateCash(cash0 - cash1);
		
		// Sends update notification because it isn't guaranteed tradePlayer1 
		// will gain any properties
		NotificationManager.getInstance().notifyObservers(
				Notification.UPDATE_PROPERTIES, new Player(players_.get(receivingPlayerIndex)));
		
		playerPanel_.updateCash(players_.get(proposingPlayerIndex));
		playerPanel_.updateCash(players_.get(receivingPlayerIndex));
		playerPanel_.updateProperties(players_.get(proposingPlayerIndex));
		playerPanel_.updateProperties(players_.get(receivingPlayerIndex));
		
		System.out.println(players_.get(0).getName() + "'s Properties After Trade:");
		for(int i = 0; i < players_.get(0).getProperties().size(); i++)
			System.out.println(players_.get(0).getProperties().get(i).getNameAndStatus());
		
		System.out.println(players_.get(1).getName() + "'s Properties After Trade:");
		for(int i = 0; i < players_.get(1).getProperties().size(); i++)
			System.out.println(players_.get(1).getProperties().get(i).getNameAndStatus());
		
		// All should be updated correctly, so send an overwrite to the other player
		filter_.addToQueue(players_, Notification.TRADE_FINALIZED, false);
	}
	
	// Called by notification Trade Rejected
	// Used to remove opponents waiting screen
	public void tradeRejected() {
		// Sends no new players which indicates trade was denied.
		filter_.addToQueue(null, Notification.TRADE_FINALIZED, false);
	}
	
	
	// Change the player's turn. Called by Notification END_TURN
	public void endTurn(Object obj, String string, boolean isTerminal) {
		int newPlayer = (Integer)obj;
		currentPlayerNum_ = newPlayer;
		
		localControl_ = !localControl_;
		if (localControl_ == false) {
			dicePanel_.setDisabled();
			buttonPanel_.setAllDisabled();
		}

		// Get rid of current propertySelectionPanel
		if (propertySelectionPanel_ != null) {
			propertySelectionPanel_.dispose();
			propertySelectionPanel_ = null;
		}

		// Show the jail pop-up only if the player is in jail, they are
		// playing on the local machine and they have rolled less than 2 times
		if (((players_.get(currentPlayerNum_).getState().toString()
				.equals(PlayerInJail.Instance().toString())) && localControl_
				&& (players_.get(currentPlayerNum_).getNumOfRolls() < 2)))
			new JailPopUp(players_.get(currentPlayerNum_));
		
		if (!isTerminal) {
			System.out.println("Sending player info:");
			for (int i = 0; i < players_.size(); ++i) {
				System.out.println("Player: " + players_.get(i).getName());
				System.out.println("Cash: " + players_.get(i).getCash());
				System.out.println("Properties: ");
				for (int j = 0; j < players_.get(i).getProperties().size(); ++j) {
					System.out.println(players_.get(i).getProperties().get(j).getName());
				}
			}
			filter_.addToQueue(players_, Notification.END_TURN_UPDATE, false);
		}
	}
	
	// Called by Notification TRADE_FINALIZED
	public void tradeOverwrite(Object obj) {
		if(obj != null)
			overwrite(obj);
		
		tradeWait.hideWaitingMessage();
	}
	
	// Called by Notification END_TURN_UPDATE
	public void overwrite(Object obj) {
		ArrayList<Player> players = (ArrayList<Player>) obj;
		
		System.out.println("*******Current****************");
		for (int i = 0; i < players_.size(); ++i) {
			System.out.println("Player: " + players_.get(i).getName());
			System.out.println("Cash: " + players_.get(i).getCash());
			System.out.println("Properties: ");
			for (int j = 0; j < players_.get(i).getProperties().size(); ++j) {
				System.out.println(players_.get(i).getProperties().get(j).getName());
			}
			System.out.println("*******");
		}
		System.out.println("*********Received**************");
		for (int i = 0; i < players.size(); ++i) {
			System.out.println("Player: " + players.get(i).getName());
			System.out.println("Cash: " + players.get(i).getCash());
			System.out.println("Properties: ");
			if (players.get(i).getProperties().isEmpty())
				System.out.println("No properties owned");
			for (int j = 0; j < players.get(i).getProperties().size(); ++j) {
				System.out.println(players.get(i).getProperties().get(j).getName());
			}
			System.out.println("*******");
		}
		System.out.println("*****************************"); 
		
		for (int i = 0; i < players.size(); ++i) {
			//Update the player fields
			players_.get(i).setCash(players.get(i).getCash());
			players_.get(i).setGetOutOfJail(players.get(i).hasGetOutOfJail());
			players_.get(i).setNumOfRolls(players.get(i).getNumOfRolls());
			players_.get(i).setPosition_NoNotify(players.get(i).getPosition());
			players_.get(i).changeState(players.get(i).getState());

			ArrayList<PropertySpace> currentProperties = new ArrayList<PropertySpace>(players.get(i).getProperties().size());
			
			// Make sure all properties are updated
			for (int j = 0; j < players.get(i).getProperties().size(); ++j) {
				for (int k = 0; k < board_.length; ++k) {

					if (players.get(i).getProperties().get(j).getName().equals(board_[k].getName()))
						currentProperties.add((PropertySpace) board_[k]);
				}
			}

			for (int j = 0; j < players.get(i).getProperties().size(); ++j) {
				currentProperties.get(j).setOwner(players_.get(i));
				currentProperties.get(j).changeState(players.get(i).getProperties().get(j).getState());
			}
			
			players_.get(i).setProperties(currentProperties);
			
			playerPanel_.updateCash(players_.get(i));
			playerPanel_.updateProperties(players_.get(i));
		}
		System.out.println("*******Finished****************");
		for (int i = 0; i < players_.size(); ++i) {
			System.out.println("Player: " + players_.get(i).getName());
			System.out.println("Cash: " + players_.get(i).getCash());
			System.out.println("Properties: ");
			for (int j = 0; j < players_.get(i).getProperties().size(); ++j) {
				System.out.println(players_.get(i).getProperties().get(j).getName());
			}
			System.out.println("*****************************");
		}
	}
	
	// Called by Notification CARD_MOVE_TO
	public void cardMoveTo(Object obj) {
		Integer num = (Integer) obj;
		board_[(int) num].landOn(players_.get(currentPlayerNum_));
	}

	// Used to create the pop-up window to confirm quitting the game
	public void confirmationPopUp() {
		JPanel basePanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		// Create layouts and buttons
		int hGap = 10, vGap = 10;
		GridLayout baseGridLayout = new GridLayout(2, 1, hGap, vGap);
		GridLayout gridLayout1 = new GridLayout(1, 1, 0, 0);
		GridLayout gridLayout2 = new GridLayout(1, 2, 0, 0);

		basePanel.setLayout(null);
		panel1.setLayout(null);
		panel2.setLayout(null);
		Font labelFont = new Font("arial", Font.PLAIN, 20);
		Font buttonFont = new Font("arial", Font.PLAIN, 18);
		Font titleFont = new Font("arial", Font.PLAIN, 14);

		JLabel label = new JLabel("Are you sure you want to quit?");
		label.setFont(labelFont);
		panel1.setLayout(gridLayout1);
		panel1.add(label);
		panel1.setBackground(new Color(236, 234, 166));

		JButton quit = new JButton("Quit");
		quit.setFont(buttonFont);
		quit.setVisible(true);

		JButton cancel = new JButton("Cancel");
		cancel.setFont(buttonFont);
		cancel.setVisible(true);

		panel2.add(quit);
		panel2.add(cancel);
		panel2.setLayout(gridLayout2);

		basePanel.setLayout(baseGridLayout);
		basePanel.setBackground(new Color(236, 234, 166));

		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(blackline, "Quit Game");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(panel1);
		basePanel.add(panel2);
		basePanel.setVisible(true);

		int xCoord = (int) (DisplayAssembler.getScreenWidth() - basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int) (DisplayAssembler.getScreenHeight() - basePanel.getPreferredSize().getHeight()) / 2;

		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				popup.hide();
				NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
				System.exit(0);
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				popup.hide();
				NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
			}
		});

		popup.show();
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
	}

	public void displayActionMessage(Object obj) {
		try {
			String message = (String) obj;
			ActionMessage.getInstance().newMessage(message);

		} catch (ClassCastException e) {
			System.err.println("Unknown object passed to method displayActionMessage");
		}
	}

	// Called by the UNOWNED_PROPERTY notification to display a pop-up window
	public void unownedProperty(Object obj) {
		try {
			PropertySpace property = (PropertySpace) obj;
			new MessagePopUp(property.getName() + " is unowned. To purchase it for $" + property.getPurchasePrice() + ", use the purchase button.");
		} catch (ClassCastException e) {
			System.err.println("Unknown object passed to method unownedProperty");
		}
	}

	// Called if Player 1's name is equal to "test"
	private void cheatMode() {
		int totalX = 150;
		int buttonY = 20;

		final JTextField cheatSpaces = new JTextField();
		cheatSpaces.setBounds(0, 0, (totalX * 2 / 3), buttonY);
		cheatSpaces.setVisible(true);

		// Go button effectively rolls the dice the number given in the text
		// field
		JButton go = new JButton("Go");
		go.setBounds(0, 0, (totalX / 3), buttonY);
		go.setVisible(true);

		go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int num = Integer.parseInt(cheatSpaces.getText());
				moveCurrentPlayerInteger(num);
				//NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE,
				//		cheatSpaces.getText());
			}
		});

		// Creates an End Turn button to change players
		JButton endTurn = new JButton("End Turn");
		endTurn.setBounds(0, 0, totalX, buttonY);
		endTurn.setVisible(true);

		// Simulates End Turn button in GameButtonPanel
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
				disposeFrames();
				NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));;
			}
		});

		// Add the cheat control bar to display assembler
		DisplayAssembler.getInstance().addComponent(go, DisplayAssembler.getScreenWidth() - (totalX / 3), DisplayAssembler.getScreenHeight() - (2 * buttonY), JLayeredPane.POPUP_LAYER);
		DisplayAssembler.getInstance().addComponent(cheatSpaces, DisplayAssembler.getScreenWidth() - totalX, DisplayAssembler.getScreenHeight() - (2 * buttonY), JLayeredPane.POPUP_LAYER);
		DisplayAssembler.getInstance().addComponent(endTurn, DisplayAssembler.getScreenWidth() - totalX, DisplayAssembler.getScreenHeight() - buttonY, JLayeredPane.POPUP_LAYER);
	}
	
	public void closeSockets() {
		try {
			if (printOut_ != null)
				printOut_.close();
			if (readIn_ != null)
				readIn_.close();
			if (serverSocket_ != null)
				serverSocket_.close();
			if (clientSocket_ != null)	
				clientSocket_.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error closing sockets");
		}
	}
	
	public void messagePopUp(Object obj, String eventName, boolean isTerminal) {
		if (isTerminal) 
			new MessagePopUp((String) obj);
		else {
			filter_.addToQueue(obj, eventName, false);
		}
	}
}
