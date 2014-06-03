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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.vandopoly.controller.NetworkedGameController;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * NetworkedGame JPanel that allows user to start a networked game
 * 
 * @author Matt Gioia & Allie Mazzia
 */

public class NetworkedGame extends JPanel {

	private JRadioButton one_, player_[];
	private JTextField nameOne_, gameIp_;
	private JLabel playerOne_, cannotFindHost_, selectPieces_, player1Piece_,
			unsuccessfulConnection_, waiting_, loadPanel_, choose_,
			selectGame_, longNameError_, noNameError_, pieceError_,
			repeatError_;
	private JButton continue_, back_, playGame_, createGame_, joinGame_, quit_;
	private DisplayAssembler display;
	private int optionsPageNum_ = 1, numOfPieces_ = 4;
	private String namesAndIcons_[];
	private MainMenu mainMenu_;
	private ImageIcon commodoreIcon_, squirrelIcon_, zepposIcon_,
			corneliusIcon_;
	private ButtonGroup icons_;
	
	private String connected_ = null;

	int frameWidth_ = 730, frameHeight_ = 750;

	static final long serialVersionUID = 3;

	private Socket clientSocket_;
	private PrintWriter printOut_ = null;
	private BufferedReader readIn_ = null;
	private ObjectInputStream input_ = null;
	private ObjectOutputStream output_ = null;
	
	NetworkedGameController networkedController_;

	public NetworkedGame(MainMenu mainMenu) {

		mainMenu_ = mainMenu;

		String commodore = "Commodore";
		String squirrel = "Squirrel";
		String zeppos = "Zeppos";
		String cornelius = "Cornelius";

		// Set size of window
		this.setSize(frameWidth_, frameHeight_);

		// Allows for automatic positioning
		this.setLayout(null);

		// Center the frame on the user's screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int) (screen.getWidth() - frameWidth_) / 2,
				(int) (screen.getHeight() - frameHeight_) / 2);

		// Set up the fonts used on this panel
		Font subTitleFont = new Font("arial", Font.PLAIN, 36);
		Font headerFont = new Font("arial", Font.PLAIN, 20);
		Font radioButtonFont = new Font("arial", Font.PLAIN, 16);
		Font buttonFont = new Font("arial", Font.PLAIN, 32);
		Font errorFont = new Font("arial", Font.BOLD, 20);

		// Set up the title bar along with positioning and size
		JLabel titleBar = new JLabel();
		ImageIcon title = new ImageIcon("images/monopoly-logo2.gif");
		titleBar.setIcon(title);
		titleBar.setBounds(0, 0, frameWidth_, 159);

		// Set up the Options header along with positioning and size
		JLabel subTitleBar = new JLabel("Game Options");
		subTitleBar.setFont(subTitleFont);
		subTitleBar.setBounds(240, 160, 500, 90);

		// Set up the sub-headers & labels along with positioning and size
		playerOne_ = new JLabel("Enter Your Name: ");
		playerOne_.setFont(headerFont);
		playerOne_.setBounds(480, 200, 250, 100);

		selectPieces_ = new JLabel("Select your game piece: ");
		selectPieces_.setFont(headerFont);
		selectPieces_.setBounds(50, 200, 300, 100);

		waiting_ = new JLabel("Waiting for player to connect...");
		waiting_.setFont(headerFont);
		waiting_.setBounds(50, 200, 400, 100);

		selectGame_ = new JLabel("Enter the IP of the game to join:");
		selectGame_.setFont(headerFont);
		selectGame_.setBounds(50, 200, 400, 100);

		choose_ = new JLabel("Choose an option:");
		choose_.setFont(headerFont);
		choose_.setBounds(50, 200, 300, 100);

		loadPanel_ = new JLabel();
		ImageIcon loading = new ImageIcon("images/loading.gif");
		loadPanel_.setIcon(loading);
		loadPanel_.setBounds(420, 230, 35, 35);

		ButtonGroup players_ = new ButtonGroup();
		players_.add(one_);

		// Now set up all of the piece icons
		commodoreIcon_ = new ImageIcon("images/Piece/Commodore.png");
		squirrelIcon_ = new ImageIcon("images/Piece/Squirrel.png");
		zepposIcon_ = new ImageIcon("images/Piece/Zeppos.png");
		corneliusIcon_ = new ImageIcon("images/Piece/Cornelius.png");

		// Create the labels that will be placed underneath the player choices
		player1Piece_ = new JLabel();
		player1Piece_.setBounds(105, 410, 100, 100);
		this.add(player1Piece_);

		numOfPieces_ = 4;

		player_ = new JRadioButton[numOfPieces_];

		// Now set up the radio buttons
		player_[0] = new JRadioButton(commodore);
		player_[0].setBounds(100, 300, 150, 25);
		player_[0].setFont(radioButtonFont);
		player_[0].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(commodoreIcon_);
			}
		});

		player_[1] = new JRadioButton(cornelius);
		player_[1].setBounds(100, 335, 150, 25);
		player_[1].setFont(radioButtonFont);
		player_[1].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(corneliusIcon_);
			}
		});

		player_[2] = new JRadioButton(squirrel);
		player_[2].setBounds(100, 370, 150, 25);
		player_[2].setFont(radioButtonFont);
		player_[2].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(squirrelIcon_);
			}
		});

		player_[3] = new JRadioButton(zeppos);
		player_[3].setBounds(100, 405, 150, 25);
		player_[3].setFont(radioButtonFont);
		player_[3].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(zepposIcon_);
			}
		});

		icons_ = new ButtonGroup();
		icons_.add(player_[0]);
		icons_.add(player_[1]);
		icons_.add(player_[2]);
		icons_.add(player_[3]);

		// Set action commands for all radio buttons
		// Helps identify proper icon when figuring out which button was pressed

		player_[0].setActionCommand(commodore);
		player_[1].setActionCommand(cornelius);
		player_[2].setActionCommand(squirrel);
		player_[3].setActionCommand(zeppos);

		createGame_ = new JButton("Create A Game");
		createGame_.setBounds(220, 300, 300, 60);
		createGame_.setFont(buttonFont);
		createGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				NetworkedGame.this.hideSecondPagePanels();
				NetworkedGame.this.showPlayerPagePanels();
			}
		});

		joinGame_ = new JButton("Join A Game");
		joinGame_.setBounds(220, 370, 300, 60);
		joinGame_.setFont(buttonFont);
		joinGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				NetworkedGame.this.hideSecondPagePanels();
				NetworkedGame.this.showFourthPagePanels();
			}

		});

		nameOne_ = new JTextField();
		nameOne_.setFont(radioButtonFont);
		nameOne_.setBounds(480, 275, 200, 25);

		gameIp_ = new JTextField();
		gameIp_.setFont(radioButtonFont);
		gameIp_.setBounds(480, 275, 200, 25);

		continue_ = new JButton("Continue");
		continue_.setBounds(115, 500, 500, 75);
		continue_.setFont(buttonFont);
		continue_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (optionsPageNum_ == 4) {// join game, enter ip
					// Create the names and icons array since we will be adding
					// player 1 to it when we connect
					namesAndIcons_ = new String[5];
					namesAndIcons_[0] = "" + 2;

					cannotFindHost_.setVisible(false);
					unsuccessfulConnection_.setVisible(false);
					
					connected_ = null;
					
					// Attempt to join the game
					createJoinGameSocket();
					
					while (true) {
						if (connected_ == null)
							continue;
						else if (connected_.equals("CONNECTED")) {
							// We have joined successfully, so go to the next page
							NetworkedGame.this.hideFourthPagePanels();
							NetworkedGame.this.showPlayerPagePanels();
								
							// Disable the piece that player 1 (server) has selected
							for (int i = 0; i < numOfPieces_; ++i) {
								if (player_[i].getActionCommand().equals(
										namesAndIcons_[3]))
									player_[i].setEnabled(false);
							}
							optionsPageNum_ = 6;
							break;
						}
						else 
							return;
					}
					
				} else if (optionsPageNum_ == 6) {// join game, select player

					namesAndIcons_[2] = nameOne_.getText();

					// Find the piece that player 2 (this, the client) has
					// selected
					for (int i = 0; i < numOfPieces_; ++i) {
						if (player_[i].isSelected())
							namesAndIcons_[4] = icons_.getSelection()
									.getActionCommand();
					}

					pieceError_.setVisible(false);
					repeatError_.setVisible(false);
					if ((icons_.getSelection() != null) && allHaveNames()
							&& shortNames() && noRepeatNames()) {
						printOut_.println(namesAndIcons_[2]);
						printOut_.println(namesAndIcons_[4]);

						printOut_.println("START");
						
						networkedController_ = new NetworkedGameController(mainMenu_.display_, namesAndIcons_, false);
						
						 NetworkedGame.this.hidePlayerPagePanels();
						 NetworkedGame.this.hideSecondPagePanels();
						 NetworkedGame.this.setVisible(false);
					} else if (icons_.getSelection() == null)
						pieceError_.setVisible(true);
				} else { // Create a new networked game
					System.out.println("Created names and Icons_");
					namesAndIcons_ = new String[5];
					namesAndIcons_[0] = "" + 2;
					namesAndIcons_[1] = nameOne_.getText();
					// Add icon to array
					for (int i = 0; i < numOfPieces_; i++) {
						if (player_[i].isSelected())
							namesAndIcons_[3] = icons_.getSelection()
									.getActionCommand();
					}
					System.err.println(namesAndIcons_[3]);
					
					pieceError_.setVisible(false);
					if ((icons_.getSelection() != null) && allHaveNames()
							&& shortNames()) {
						NetworkedGame.this.hidePlayerPagePanels();
						NetworkedGame.this.showThirdPagePanels();
					} else if (icons_.getSelection() == null)
						pieceError_.setVisible(true);

				}
			}
		});

		back_ = new JButton("Back");
		back_.setBounds(115, 580, 500, 75);
		back_.setFont(buttonFont);
		back_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (optionsPageNum_ == 2)
					NetworkedGame.this.backToMain();
				else if (optionsPageNum_ == 3) {
					NetworkedGame.this.backToPlayerPage();
					
				}
				else {
					cannotFindHost_.setVisible(false);
					unsuccessfulConnection_.setVisible(false);
					NetworkedGame.this.backToSecondPage();
				}
			}
		});
		
		quit_ = new JButton("Quit");
		quit_.setBounds(115, 580, 500, 75);
		quit_.setFont(buttonFont);
		quit_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (optionsPageNum_ == 3) {
					networkedController_.closeSockets();
					System.exit(0);
				}
			}
		});

		playGame_ = new JButton("PLAY GAME");
		playGame_.setBounds(115, 500, 500, 75);
		playGame_.setFont(buttonFont);
		playGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// NotificationManager.getInstance().notifyObservers(
				// Notification.START_GAME, namesAndIcons_);
				// NetworkedGame.this.hideSecondPagePanels();
				// NetworkedGame.this.setVisible(false);

			}

		});
		/*
		 * repeatError_ = new JLabel("Each Player must have a unique name");
		 * repeatError_.setFont(errorFont);
		 * repeatError_.setForeground(Color.red); repeatError_.setBounds(138,
		 * 675, 600, 40); repeatError_.setVisible(false);
		 */
		longNameError_ = new JLabel(
				"Please limit your name to a maximum of 20 characters");
		longNameError_.setFont(errorFont);
		longNameError_.setForeground(Color.red);
		longNameError_.setBounds(25, 675, 700, 40);
		longNameError_.setVisible(false);

		noNameError_ = new JLabel("Please enter a name");
		noNameError_.setFont(errorFont);
		noNameError_.setForeground(Color.red);
		noNameError_.setBounds(187, 675, 700, 40);
		noNameError_.setVisible(false);

		pieceError_ = new JLabel("Please choose a game piece");
		pieceError_.setFont(errorFont);
		pieceError_.setForeground(Color.red);
		pieceError_.setBounds(143, 675, 600, 40);
		pieceError_.setVisible(false);

		cannotFindHost_ = new JLabel(
				"Cannot find IP address given. Please try again.");
		cannotFindHost_.setFont(errorFont);
		cannotFindHost_.setForeground(Color.red);
		cannotFindHost_.setBounds(25, 675, 700, 40);
		cannotFindHost_.setVisible(false);

		unsuccessfulConnection_ = new JLabel(
				"Unsuccessful connection attempt. Please try again.");
		unsuccessfulConnection_.setFont(errorFont);
		unsuccessfulConnection_.setForeground(Color.red);
		unsuccessfulConnection_.setBounds(25, 675, 700, 40);
		unsuccessfulConnection_.setVisible(false);

		repeatError_ = new JLabel("Player 1 has already chosen that name");
		repeatError_.setFont(errorFont);
		repeatError_.setForeground(Color.red);
		repeatError_.setBounds(138, 675, 600, 40);
		repeatError_.setVisible(false);

		// Add some Components to the panel
		this.add(titleBar);
		this.add(subTitleBar);
		this.add(playerOne_);
		this.add(nameOne_);
		this.add(continue_);
		this.add(back_);
		this.add(quit_);
		this.add(playGame_);
		this.add(waiting_);
		this.add(loadPanel_);
		this.add(choose_);
		this.add(createGame_);
		this.add(joinGame_);
		this.add(selectGame_);
		this.add(gameIp_);

		for (int j = 0; j < numOfPieces_; j++)
			this.add(player_[j]);

		this.add(selectPieces_);

		this.add(cannotFindHost_);
		this.add(unsuccessfulConnection_);
		this.add(longNameError_);
		this.add(noNameError_);
		this.add(pieceError_);
		this.add(repeatError_);

		// Add the Panel to the display
		display = DisplayAssembler.getInstance();
		display.addComponent(this, location, JLayeredPane.POPUP_LAYER);

		// Set the visibility as true, thereby displaying it
		this.setVisible(true);

		this.hidePlayerPagePanels();

		NotificationManager.getInstance().addObserver(Notification.START_GAME,
				this, "backToMain");
	}

	public void createJoinGameSocket() {

		new Thread("startClient") {
			public void run() {
				try {
					String temp = null;
					// Create a new socket connection
					clientSocket_ = new Socket(InetAddress.getByName(gameIp_
							.getText()), 3913);

					// Create the data input and output streams
					printOut_ = new PrintWriter(
							clientSocket_.getOutputStream(), true);
					readIn_ = new BufferedReader(new InputStreamReader(
							clientSocket_.getInputStream()));
					
					input_ = new ObjectInputStream(clientSocket_.getInputStream());
					output_ = new ObjectOutputStream(clientSocket_.getOutputStream());
					output_.flush();
					
					// Send the request to the server
					printOut_.println("JOIN");

					// Read a line
					temp = readIn_.readLine();
					System.out.println("Client temp: " + temp);
					if (temp.equals("ACCEPTED")) {
						System.out.println("Accepted");

						namesAndIcons_[1] = readIn_.readLine();
						namesAndIcons_[3] = readIn_.readLine();
					//	System.out.println("Name: " + namesAndIcons_[1]);
					//	System.out.println("Icon: " + namesAndIcons_[3]);
						
						connected_ = "CONNECTED";
						
						while (networkedController_ == null) {
						//	System.out.println("Sleeping");
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					//	System.out.println("Calling clientListen()");
						networkedController_.clientListen(readIn_, printOut_, input_, output_);
						
					} else
						System.out.println("Connect failed");

				} catch (ConnectException e) {
					e.printStackTrace();
					try {
						clientSocket_.close();
						printOut_.close();
						readIn_.close();
						output_.close();
						input_.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				} catch (UnknownHostException e) {
					System.out.println("Cannot find host");
					cannotFindHost_.setVisible(true);
					connected_ = "NO";
					
				} catch (SocketException e) {
					// The server has quit - handle appropriately
					System.out.println("Server has quit game");
				}catch (IOException e) {
					e.printStackTrace();
					unsuccessfulConnection_.setVisible(true);
					connected_ = "NO";
					
				} 
			}
		}.start();

	}

	public boolean noRepeatNames() {
		System.out.println(namesAndIcons_[1] + " " + namesAndIcons_[2]);
		if (namesAndIcons_[1].equals(namesAndIcons_[2])) {
			repeatError_.setVisible(true);
			return false;
		}

		return true;
	}

	public boolean shortNames() {
		if (namesAndIcons_[1].length() > 20) {
			noNameError_.setVisible(false);
			repeatError_.setVisible(false);
			longNameError_.setVisible(true);
			return false;
		}

		longNameError_.setVisible(false);
		return true;
	}

	public boolean allHaveNames() {
		if (namesAndIcons_[1].length() == 0) {
			longNameError_.setVisible(false);
			repeatError_.setVisible(false);
			noNameError_.setVisible(true);
			return false;
		}

		noNameError_.setVisible(false);
		return true;
	}

	public void disableIcon(int i) {
		player_[i].setEnabled(false);
	}

	public void hidePlayerPagePanels() {
		playerOne_.setVisible(false);
		nameOne_.setVisible(false);
		selectPieces_.setVisible(false);

		player_[0].setVisible(false);
		player_[1].setVisible(false);
		player_[2].setVisible(false);
		player_[3].setVisible(false);
		player1Piece_.setVisible(false);

		continue_.setVisible(false);
		quit_.setVisible(false);
		hideThirdPagePanels();
		hideFourthPagePanels();
	}

	public void showPlayerPagePanels() {
		playerOne_.setVisible(true);
		nameOne_.setVisible(true);
		selectPieces_.setVisible(true);

		player_[0].setVisible(true);
		player_[1].setVisible(true);
		player_[2].setVisible(true);
		player_[3].setVisible(true);
		player1Piece_.setVisible(true);
		
		quit_.setVisible(false);
		continue_.setVisible(true);
		optionsPageNum_ = 5;
	}

	public void showSecondPagePanels() {
		choose_.setVisible(true);
		createGame_.setVisible(true);
		quit_.setVisible(false);
		joinGame_.setVisible(true);
		optionsPageNum_ = 2;
	}

	public void hideSecondPagePanels() {
		choose_.setVisible(false);
		createGame_.setVisible(false);
		quit_.setVisible(false);
		joinGame_.setVisible(false);
	}

	public void hideThirdPagePanels() {
		loadPanel_.setVisible(false);
		waiting_.setVisible(false);
		playGame_.setVisible(false);
		quit_.setVisible(false);
	}

	public void showThirdPagePanels() {
		// playGame_.setVisible(true);
		loadPanel_.setVisible(true);
		waiting_.setVisible(true);
		quit_.setVisible(true);
		back_.setVisible(false);
		optionsPageNum_ = 3;

		startNetworkedGame();
	}

	public void startNetworkedGame() {
		networkedController_ = new NetworkedGameController
				(mainMenu_.display_, namesAndIcons_, true);
	}

	public void hideFourthPagePanels() {
		selectGame_.setVisible(false);
		gameIp_.setVisible(false);
		continue_.setVisible(false);
		quit_.setVisible(false);
	}

	public void showFourthPagePanels() {
		selectGame_.setVisible(true);
		gameIp_.setVisible(true);
		optionsPageNum_ = 4;
		continue_.setVisible(true);
		quit_.setVisible(false);

	}

	public void backToMain() {
		nameOne_.setText(null);

		longNameError_.setVisible(false);
		noNameError_.setVisible(false);
		mainMenu_.showMenu();
		this.setVisible(false);
		quit_.setVisible(false);
	}

	public void backToPlayerPage() {
		hideThirdPagePanels();
		showPlayerPagePanels();

	}

	public void backToSecondPage() {
		hideThirdPagePanels();
		hideFourthPagePanels();
		hidePlayerPagePanels();
		showSecondPagePanels();
	}
}
