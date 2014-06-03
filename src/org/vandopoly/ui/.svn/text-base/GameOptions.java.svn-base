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


import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.vandopoly.controller.GameController;
import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * GameOptions JPanel that allows user to customize current game
 * 
 * @author Allie Mazzia
 */
public class GameOptions extends JPanel {

	private JRadioButton one_, two_, three_, four_;
	private JTextField nameOne_, nameTwo_, nameThree_, nameFour_;
	private JLabel playersHeader_, playerNames_, playerOne_, playerTwo_,
			playerThree_, playerFour_;
	private JButton continue_, back_, playGame_;
	private DisplayAssembler display;
	private int numberOfPlayers_ = 2, optionsPageNum_ = 1;
	private String namesAndIcons_[];

	// Private data members for page 2 of options
	// Holds the radio buttons for pieces of each player
	private JRadioButton player_[][];

	private JLabel selectPieces_, playerOne_2_, playerTwo_2_, playerThree_2_,
			playerFour_2_;

	private JLabel player1Piece_, player2Piece_, player3Piece_, player4Piece_;
	private ImageIcon commodoreIcon_, squirrelIcon_, zepposIcon_,
			corneliusIcon_;

	private JLabel repeatError_, longNameError_, noNameError_, pieceError_;

	private ButtonGroup icons_[];

	private int numOfPieces_, maxPlayers_;

	static final long serialVersionUID = 3;

	private MainMenu mainMenu_;

	public GameOptions(final MainMenu mainMenu) {

		mainMenu_ = mainMenu;

		String commodore = "Commodore";
		String squirrel = "Squirrel";
		String zeppos = "Zeppos";
		String cornelius = "Cornelius";

		int frameWidth = 730, frameHeight = 750;

		// Set size of window
		this.setSize(frameWidth, frameHeight);

		// Allows for automatic positioning
		this.setLayout(null);

		// Center the frame on the user's screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int) (screen.getWidth() - frameWidth) / 2,
				(int) (screen.getHeight() - frameHeight) / 2);

		// Set up the fonts used on this panel
		Font subTitleFont = new Font("broadway", Font.PLAIN, 36);
		Font headerFont = new Font("broadway", Font.PLAIN, 20);
		Font radioButtonFont = new Font("broadway", Font.PLAIN, 16);
		Font buttonFont = new Font("broadway", Font.PLAIN, 32);
		Font errorFont = new Font("broadway", Font.BOLD, 20);

		// Set up the title bar along with positioning and size
		JLabel titleBar = new JLabel();
		ImageIcon title = new ImageIcon("images/vandopoly-logo2.gif");
		titleBar.setIcon(title);
		titleBar.setBounds(0, 0, frameWidth, 159);

		// Set up the Options header along with positioning and size
		JLabel subTitleBar = new JLabel("Game Options");
		subTitleBar.setFont(subTitleFont);
		subTitleBar.setBounds(240, 160, 500, 90);

		// Set up the sub-headers & labels along with positioning and size
		playersHeader_ = new JLabel("Number of Players:");
		playersHeader_.setFont(headerFont);
		playersHeader_.setBounds(50, 200, 300, 100);

		playerNames_ = new JLabel("Player Names:");
		playerNames_.setFont(headerFont);
		playerNames_.setBounds(500, 200, 300, 100);

		playerOne_ = new JLabel("Player 1: ");
		playerOne_.setFont(radioButtonFont);
		playerOne_.setBounds(475, 275, 100, 25);

		playerTwo_ = new JLabel("Player 2: ");
		playerTwo_.setFont(radioButtonFont);
		playerTwo_.setBounds(475, 305, 100, 25);

		playerThree_ = new JLabel("Player 3: ");
		playerThree_.setFont(radioButtonFont);
		playerThree_.setBounds(475, 335, 100, 25);

		playerFour_ = new JLabel("Player 4: ");
		playerFour_.setFont(radioButtonFont);
		playerFour_.setBounds(475, 365, 100, 25);

		selectPieces_ = new JLabel("Select your game piece: ");
		selectPieces_.setFont(headerFont);
		selectPieces_.setBounds(50, 200, 300, 100);

		playerOne_2_ = new JLabel("Player 1: ");
		playerOne_2_.setFont(radioButtonFont);
		playerOne_2_.setBounds(50, 270, 100, 25);

		playerTwo_2_ = new JLabel("Player 2: ");
		playerTwo_2_.setFont(radioButtonFont);
		playerTwo_2_.setBounds(200, 270, 100, 25);

		playerThree_2_ = new JLabel("Player 3: ");
		playerThree_2_.setFont(radioButtonFont);
		playerThree_2_.setBounds(350, 270, 100, 25);

		playerFour_2_ = new JLabel("Player 4: ");
		playerFour_2_.setFont(radioButtonFont);
		playerFour_2_.setBounds(500, 270, 100, 25);

		// Set up and create radio buttons and text fields
		two_ = new JRadioButton("2");
		two_.setBounds(100, 280, 50, 25);
		two_.setFont(radioButtonFont);
		two_.setSelected(true);
		two_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GameOptions.this.showTwo();
				numberOfPlayers_ = 2;
			}
		});

		three_ = new JRadioButton("3");
		three_.setBounds(100, 310, 50, 25);
		three_.setFont(radioButtonFont);
		three_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GameOptions.this.showThree();
				numberOfPlayers_ = 3;
			}
		});

		four_ = new JRadioButton("4");
		four_.setBounds(100, 340, 50, 25);
		four_.setFont(radioButtonFont);
		four_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GameOptions.this.showFour();
				numberOfPlayers_ = 4;
			}
		});

		ButtonGroup players_ = new ButtonGroup();
		players_.add(one_);
		players_.add(two_);
		players_.add(three_);
		players_.add(four_);

		// Now set up all of the piece icons
		commodoreIcon_ = new ImageIcon("images/Piece/Commodore.png");
		squirrelIcon_ = new ImageIcon("images/Piece/Squirrel.png");
		zepposIcon_ = new ImageIcon("images/Piece/Zeppos.png");
		corneliusIcon_ = new ImageIcon("images/Piece/Cornelius.png");

		// Create the labels that will be placed underneath the player choices
		player1Piece_ = new JLabel();
		player1Piece_.setBounds(105, 410, 100, 100);
		this.add(player1Piece_);

		player2Piece_ = new JLabel();
		player2Piece_.setBounds(250, 410, 100, 100);
		this.add(player2Piece_);

		player3Piece_ = new JLabel();
		player3Piece_.setBounds(400, 410, 100, 100);
		this.add(player3Piece_);

		player4Piece_ = new JLabel();
		player4Piece_.setBounds(550, 410, 100, 100);
		this.add(player4Piece_);

		maxPlayers_ = 4;
		numOfPieces_ = 4;

		player_ = new JRadioButton[maxPlayers_][numOfPieces_];
		icons_ = new ButtonGroup[numOfPieces_];

		// Now set up the radio buttons
		player_[0][0] = new JRadioButton(commodore);
		player_[0][0].setBounds(100, 300, 150, 25);
		player_[0][0].setFont(radioButtonFont);
		player_[0][0].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(commodoreIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[0][1] = new JRadioButton(cornelius);
		player_[0][1].setBounds(100, 335, 150, 25);
		player_[0][1].setFont(radioButtonFont);
		player_[0][1].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(corneliusIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[0][2] = new JRadioButton(squirrel);
		player_[0][2].setBounds(100, 370, 150, 25);
		player_[0][2].setFont(radioButtonFont);
		player_[0][2].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(squirrelIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[0][3] = new JRadioButton(zeppos);
		player_[0][3].setBounds(100, 405, 150, 25);
		player_[0][3].setFont(radioButtonFont);
		player_[0][3].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(zepposIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		icons_[0] = new ButtonGroup();
		icons_[0].add(player_[0][0]);
		icons_[0].add(player_[0][1]);
		icons_[0].add(player_[0][2]);
		icons_[0].add(player_[0][3]);

		player_[1][0] = new JRadioButton(commodore);
		player_[1][0].setBounds(250, 300, 150, 25);
		player_[1][0].setFont(radioButtonFont);
		player_[1][0].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player2Piece_.setIcon(commodoreIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[1][1] = new JRadioButton(cornelius);
		player_[1][1].setBounds(250, 335, 150, 25);
		player_[1][1].setFont(radioButtonFont);
		player_[1][1].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player2Piece_.setIcon(corneliusIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[1][2] = new JRadioButton(squirrel);
		player_[1][2].setBounds(250, 370, 150, 25);
		player_[1][2].setFont(radioButtonFont);
		player_[1][2].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player2Piece_.setIcon(squirrelIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[1][3] = new JRadioButton(zeppos);
		player_[1][3].setBounds(250, 405, 150, 25);
		player_[1][3].setFont(radioButtonFont);
		player_[1][3].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player2Piece_.setIcon(zepposIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		icons_[1] = new ButtonGroup();
		icons_[1].add(player_[1][0]);
		icons_[1].add(player_[1][1]);
		icons_[1].add(player_[1][2]);
		icons_[1].add(player_[1][3]);

		player_[2][0] = new JRadioButton(commodore);
		player_[2][0].setBounds(400, 300, 150, 25);
		player_[2][0].setFont(radioButtonFont);
		player_[2][0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player3Piece_.setIcon(commodoreIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[2][1] = new JRadioButton(cornelius);
		player_[2][1].setBounds(400, 335, 150, 25);
		player_[2][1].setFont(radioButtonFont);
		player_[2][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player3Piece_.setIcon(corneliusIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[2][2] = new JRadioButton(squirrel);
		player_[2][2].setBounds(400, 370, 150, 25);
		player_[2][2].setFont(radioButtonFont);
		player_[2][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player3Piece_.setIcon(squirrelIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[2][3] = new JRadioButton(zeppos);
		player_[2][3].setBounds(400, 405, 150, 25);
		player_[2][3].setFont(radioButtonFont);
		player_[2][3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player3Piece_.setIcon(zepposIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		icons_[2] = new ButtonGroup();
		icons_[2].add(player_[2][0]);
		icons_[2].add(player_[2][1]);
		icons_[2].add(player_[2][2]);
		icons_[2].add(player_[2][3]);

		player_[3][0] = new JRadioButton(commodore);
		player_[3][0].setBounds(550, 300, 150, 25);
		player_[3][0].setFont(radioButtonFont);
		player_[3][0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				player4Piece_.setIcon(commodoreIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[3][1] = new JRadioButton(cornelius);
		player_[3][1].setBounds(550, 335, 150, 25);
		player_[3][1].setFont(radioButtonFont);
		player_[3][1].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player4Piece_.setIcon(corneliusIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[3][2] = new JRadioButton(squirrel);
		player_[3][2].setBounds(550, 370, 150, 25);
		player_[3][2].setFont(radioButtonFont);
		player_[3][2].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player4Piece_.setIcon(squirrelIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		player_[3][3] = new JRadioButton(zeppos);
		player_[3][3].setBounds(550, 405, 150, 25);
		player_[3][3].setFont(radioButtonFont);
		player_[3][3].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player4Piece_.setIcon(zepposIcon_);
				GameOptions.this.refreshPieceButtons();
			}
		});

		icons_[3] = new ButtonGroup();
		icons_[3].add(player_[3][0]);
		icons_[3].add(player_[3][1]);
		icons_[3].add(player_[3][2]);
		icons_[3].add(player_[3][3]);

		// Set action commands for all radio buttons
		// Helps identify proper icon when figuring out which button was pressed
		for (int i = 0; i < this.maxPlayers_; i++) {
			player_[i][0].setActionCommand(commodore);
			player_[i][1].setActionCommand(cornelius);
			player_[i][2].setActionCommand(squirrel);
			player_[i][3].setActionCommand(zeppos);
		}

		nameOne_ = new JTextField();
		nameOne_.setFont(radioButtonFont);
		nameOne_.setBounds(565, 275, 110, 25);

		nameTwo_ = new JTextField();
		nameTwo_.setFont(radioButtonFont);
		nameTwo_.setBounds(565, 305, 110, 25);

		nameThree_ = new JTextField();
		nameThree_.setFont(radioButtonFont);
		nameThree_.setBounds(565, 335, 110, 25);

		nameFour_ = new JTextField();
		nameFour_.setFont(radioButtonFont);
		nameFour_.setBounds(565, 365, 110, 25);

		continue_ = new JButton("Continue");
		continue_.setBounds(115, 500, 500, 75);
		continue_.setFont(buttonFont);
		continue_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				noNameError_.setVisible(false);
				longNameError_.setVisible(false);
				repeatError_.setVisible(false);

				namesAndIcons_ = new String[(2 * numberOfPlayers_) + 1];

				namesAndIcons_[0] = "" + numberOfPlayers_;

				namesAndIcons_[1] = nameOne_.getText();
				playerOne_2_.setText(namesAndIcons_[1] + ":");

				namesAndIcons_[2] = nameTwo_.getText();
				playerTwo_2_.setText(namesAndIcons_[2] + ":");

				if (numberOfPlayers_ > 2) {
					namesAndIcons_[3] = nameThree_.getText();
					playerThree_2_.setText(namesAndIcons_[3] + ":");

					if (numberOfPlayers_ == 4) {
						namesAndIcons_[4] = nameFour_.getText();
						playerFour_2_.setText(namesAndIcons_[4] + ":");
					}
				}

				if (noRepeatNames() && shortNames() && allHaveNames()) {
					GameOptions.this.hideFirstPagePanels();
					GameOptions.this.showSecondPagePanels();
					optionsPageNum_ = 2;
				}
			}

			// Check to see if any of the names given are duplicates
			public boolean noRepeatNames() {
				// Had to adjust for loops to compensate for blank first name
				for (int i = 1; i <= numberOfPlayers_; i++) {
					for (int j = i + 1; j <= numberOfPlayers_; j++) {
						if (namesAndIcons_[i].equals(namesAndIcons_[j])) {
							repeatError_.setVisible(true);
							return false;
						}
					}
				}
				return true;
			}

			public boolean shortNames() {
				for (int i = 1; i <= numberOfPlayers_; i++) {
					if (namesAndIcons_[i].length() > 20) {
						longNameError_.setVisible(true);
						return false;
					}
				}
				return true;
			}

			public boolean allHaveNames() {
				for (int i = 1; i <= numberOfPlayers_; i++) {
					if (namesAndIcons_[i].length() == 0) {
						noNameError_.setVisible(true);
						return false;
					}
				}
				return true;
			}
		});

		back_ = new JButton("Back");
		back_.setBounds(115, 580, 500, 75);
		back_.setFont(buttonFont);
		back_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (optionsPageNum_ == 1)
					GameOptions.this.backToMain();
				else
					GameOptions.this.backToFirstPage();

			}
		});

		playGame_ = new JButton("PLAY GAME");
		playGame_.setBounds(115, 500, 500, 75);
		playGame_.setFont(buttonFont);
		playGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if ((icons_[0].getSelection() == null || icons_[1]
						.getSelection() == null)
						|| (numberOfPlayers_ > 2 && icons_[2].getSelection() == null)
						|| (numberOfPlayers_ > 3 && icons_[3].getSelection() == null)) {
					pieceError_.setVisible(true);
				}

				else {
					new GameController(mainMenu.display_);
					pieceError_.setVisible(false);
					// Get all appropriate names for the buttons and puts them
					// in the namesAndIcons_ array
					for (int i = 0; i < numberOfPlayers_; i++) {
						namesAndIcons_[numberOfPlayers_ + i + 1] = icons_[i]
								.getSelection().getActionCommand();
					}
					NotificationManager.getInstance().notifyObservers(
							Notification.START_GAME, namesAndIcons_);
					GameOptions.this.hideSecondPagePanels();
					GameOptions.this.setVisible(false);
					
				}
			}

		});

		// Set up the duplicate name error message
		repeatError_ = new JLabel("Each Player must have a unique name");
		repeatError_.setFont(errorFont);
		repeatError_.setForeground(Color.red);
		repeatError_.setBounds(138, 675, 600, 40);
		repeatError_.setVisible(false);

		longNameError_ = new JLabel(
				"Please limit player names to a maximum of 20 characters");
		longNameError_.setFont(errorFont);
		longNameError_.setForeground(Color.red);
		longNameError_.setBounds(25, 675, 700, 40);
		longNameError_.setVisible(false);

		noNameError_ = new JLabel("All players must have a name");
		noNameError_.setFont(errorFont);
		noNameError_.setForeground(Color.red);
		noNameError_.setBounds(187, 675, 700, 40);
		noNameError_.setVisible(false);

		pieceError_ = new JLabel("All Players must choose a game piece");
		pieceError_.setFont(errorFont);
		pieceError_.setForeground(Color.red);
		pieceError_.setBounds(143, 675, 600, 40);
		pieceError_.setVisible(false);

		// Add some Components to the panel
		this.add(titleBar);
		this.add(subTitleBar);
		this.add(playersHeader_);
		this.add(playerNames_);
		this.add(playerOne_);
		this.add(playerTwo_);
		this.add(playerThree_);
		this.add(playerFour_);
		this.add(two_);
		this.add(three_);
		this.add(four_);
		this.add(nameOne_);
		this.add(nameTwo_);
		this.add(nameThree_);
		this.add(nameFour_);
		this.add(continue_);
		this.add(back_);
		this.add(playGame_);

		// Add the radio buttons
		for (int i = 0; i < maxPlayers_; i++) {
			for (int j = 0; j < numOfPieces_; j++)
				this.add(player_[i][j]);
		}

		this.add(selectPieces_);
		this.add(playerOne_2_);
		this.add(playerTwo_2_);
		this.add(playerThree_2_);
		this.add(playerFour_2_);

		this.add(repeatError_);
		this.add(longNameError_);
		this.add(noNameError_);
		this.add(pieceError_);

		// Add the Panel to the display
		display = DisplayAssembler.getInstance();
		display.addComponent(this, location, JLayeredPane.POPUP_LAYER);

		// Set the visibility as true, thereby displaying it
		this.setVisible(true);

		this.hideSecondPagePanels();

		NotificationManager.getInstance().addObserver(Notification.START_GAME,
				this, "backToMain");
	}

	// Sets all piece buttons to enabled or disabled based on current selections
	public void refreshPieceButtons() {

		// First set all radio buttons to enabled
		for (int i = 0; i < maxPlayers_; i++) {
			for (int j = 0; j < numOfPieces_; j++)
				player_[i][j].setEnabled(true);
		}

		// Run through all radio buttons and if one is found to be selected
		// deselect all corresponding buttons.
		for (int i = 0; i < maxPlayers_; i++) {
			for (int j = 0; j < numOfPieces_; j++) {
				if (player_[i][j].isSelected()) {

					for (int z = 1; z < maxPlayers_; z++) {
						player_[(i + z) % maxPlayers_][j].setEnabled(false);
					}

					break;
				}
			}
		}
	}

	public void hideFirstPagePanels() {
		playerOne_.setVisible(false);
		nameOne_.setVisible(false);
		playerTwo_.setVisible(false);
		nameTwo_.setVisible(false);
		playerThree_.setVisible(false);
		nameThree_.setVisible(false);
		playerFour_.setVisible(false);
		nameFour_.setVisible(false);

		two_.setVisible(false);
		three_.setVisible(false);
		four_.setVisible(false);

		continue_.setVisible(false);
		playerNames_.setVisible(false);
		playersHeader_.setVisible(false);
	}

	public void showFirstPagePanels() {

		playerOne_.setVisible(true);
		nameOne_.setVisible(true);
		playerTwo_.setVisible(true);
		nameTwo_.setVisible(true);

		if (numberOfPlayers_ > 2) {
			playerThree_.setVisible(true);
			nameThree_.setVisible(true);

			if (numberOfPlayers_ == 4) {
				playerFour_.setVisible(true);
				nameFour_.setVisible(true);
			}
		}

		two_.setVisible(true);
		three_.setVisible(true);
		four_.setVisible(true);

		continue_.setVisible(true);
		playerNames_.setVisible(true);
		playersHeader_.setVisible(true);
	}

	public void showSecondPagePanels() {

		selectPieces_.setVisible(true);
		playGame_.setVisible(true);

		playerOne_2_.setVisible(true);
		player_[0][0].setVisible(true);
		player_[0][1].setVisible(true);
		player_[0][2].setVisible(true);
		player_[0][3].setVisible(true);
		player1Piece_.setVisible(true);

		playerTwo_2_.setVisible(true);
		player_[1][0].setVisible(true);
		player_[1][1].setVisible(true);
		player_[1][2].setVisible(true);
		player_[1][3].setVisible(true);
		player2Piece_.setVisible(true);

		if (numberOfPlayers_ > 2) {
			playerThree_2_.setVisible(true);
			player_[2][0].setVisible(true);
			player_[2][1].setVisible(true);
			player_[2][2].setVisible(true);
			player_[2][3].setVisible(true);
			player3Piece_.setVisible(true);

			if (numberOfPlayers_ == 4) {
				playerFour_2_.setVisible(true);
				player_[3][0].setVisible(true);
				player_[3][1].setVisible(true);
				player_[3][2].setVisible(true);
				player_[3][3].setVisible(true);
				player4Piece_.setVisible(true);
			}
		}
	}

	public void hideSecondPagePanels() {
		playerThree_.setVisible(false);
		nameThree_.setVisible(false);
		playerFour_.setVisible(false);
		nameFour_.setVisible(false);

		// Set all radio buttons to false
		for (int i = 0; i < maxPlayers_; i++) {
			for (int j = 0; j < numOfPieces_; j++)
				player_[i][j].setVisible(false);
		}

		player1Piece_.setVisible(false);
		player2Piece_.setVisible(false);
		player3Piece_.setVisible(false);
		player4Piece_.setVisible(false);

		selectPieces_.setVisible(false);
		playerOne_2_.setVisible(false);
		playerTwo_2_.setVisible(false);
		playerThree_2_.setVisible(false);
		playerFour_2_.setVisible(false);

		icons_[0].clearSelection();
		icons_[1].clearSelection();
		icons_[2].clearSelection();
		icons_[3].clearSelection();
		refreshPieceButtons();

		player1Piece_.setIcon(null);
		player2Piece_.setIcon(null);
		player3Piece_.setIcon(null);
		player4Piece_.setIcon(null);

		pieceError_.setVisible(false);

		playGame_.setVisible(false);
	}

	// Methods to display the appropriate number of text boxes on the
	// first options page
	public void showTwo() {
		playerThree_.setVisible(false);
		nameThree_.setVisible(false);
		playerFour_.setVisible(false);
		nameFour_.setVisible(false);

		nameThree_.setText(null);
		nameFour_.setText(null);
	}

	public void showThree() {
		playerThree_.setVisible(true);
		nameThree_.setVisible(true);
		playerFour_.setVisible(false);
		nameFour_.setVisible(false);

		nameFour_.setText(null);
	}

	public void showFour() {
		playerThree_.setVisible(true);
		nameThree_.setVisible(true);
		playerFour_.setVisible(true);
		nameFour_.setVisible(true);
	}

	public void backToMain() {

		two_.setSelected(true);
		nameOne_.setText(null);
		nameTwo_.setText(null);
		nameThree_.setText(null);
		nameFour_.setText(null);
		numberOfPlayers_ = 2;

		longNameError_.setVisible(false);
		repeatError_.setVisible(false);
		noNameError_.setVisible(false);
		mainMenu_.showMenu();
		this.setVisible(false);
	}

	public void backToFirstPage() {
		hideSecondPagePanels();
		showFirstPagePanels();

		optionsPageNum_ = 1;
	}

}
