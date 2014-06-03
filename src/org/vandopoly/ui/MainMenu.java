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
// Import the swing and AWT classes needed
package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.vandopoly.controller.GameController;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/**
 * Main Menu for Vandopoly
 */
public class MainMenu extends JPanel {

    private JButton newGame_, rules_, quitGame_, networkGame_;
    Display display_;
    private GameOptions options_;
    private NetworkedGame networkedGame_;

    private String nameTwo_;
    private String namesAndIcons_[];

    private JTextField nameOne_;
    private JLabel playerNames_, selectPieces_, player1Piece_;
    private JLabel longNameError_, noNameError_, gameChosenError_;
    private JList gameList_;
    private DefaultListModel listModel;

    private ImageIcon redIcon_, greenIcon_, blueIcon_,
            yellowIcon_;

    static final long serialVersionUID = 2;
    boolean[] availableGame;

    public MainMenu(Display d) {
        display_ = d;

        int frameWidth = 730, frameHeight = 480;
        int buttonWidth = 350, buttonHeight = 75;

        // Space between buttons and the Y component of first button
        int betweenSpace = 20, buttonStart = 180;

        // Set size of window
        this.setSize(frameWidth, frameHeight);

        // Allows for automatic positioning
        this.setLayout(null);

        // Center the frame on the user's screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        Point location = new Point((int) (screen.getWidth() - frameWidth) / 2,
                ((int) (screen.getHeight() - frameHeight) / 2));

        // Set up the title bar along with positioning and size
        JLabel titleBar = new JLabel();
        ImageIcon title = new ImageIcon("images/monopoly-logo.gif");
        titleBar.setIcon(title);
        titleBar.setBounds(51, 10, frameWidth, 159);

        // Setup and create all 3 buttons
        Font buttonFont = new Font("arial", Font.BOLD, 24);
        Font headerFont = new Font("arial", Font.PLAIN, 20);
        Font radioButtonFont = new Font("arial", Font.PLAIN, 16);
        Font errorFont = new Font("arial", Font.BOLD, 20);

        playerNames_ = new JLabel("Player's Name:");
        playerNames_.setFont(headerFont);
        playerNames_.setBounds(500, 160, 300, 100);

        nameOne_ = new JTextField();
        nameOne_.setFont(radioButtonFont);
        nameOne_.setBounds(500, 235, 130, 25);

        //define AI player's name
        nameTwo_ = "0556";

        String red = "Red_circle";
        String green = "Green_circle";
        String yellow = "Yellow_circle";
        String blue = "Blue_circle";

        // Now set up all of the piece icons
        redIcon_ = new ImageIcon("images/Piece/Red_circle.png");
        greenIcon_ = new ImageIcon("images/Piece/Green_circle.png");
        yellowIcon_ = new ImageIcon("images/Piece/Yellow_circle.png");
        blueIcon_ = new ImageIcon("images/Piece/Blue_circle.png");

        selectPieces_ = new JLabel("Select your game to join: ");
        selectPieces_.setFont(headerFont);
        selectPieces_.setBounds(75, 160, 300, 100);

        // Create the labels that will be placed underneath the player choices
        player1Piece_ = new JLabel();
        player1Piece_.setBounds(425, 160, 100, 100);
        this.add(player1Piece_);
        player1Piece_.setIcon(redIcon_);

        listModel = new DefaultListModel();
        Random r1 = new Random();
        Random r2 = new Random();
        boolean[] availableGame = new boolean[5];
        int totalGameNo = r2.nextInt(5);

        for (int i = 1; i <= totalGameNo; i++) {
            int existNoPlayer = r1.nextInt(2) * 2;
            listModel.addElement("Game Board No." + i + ". Players---(" + existNoPlayer + "/2)");
            if (existNoPlayer == 0) {
                availableGame[i - 1] = true;
            } else {
                availableGame[i - 1] = false;
            }
        }
        listModel.addElement("Game Board No." + (totalGameNo + 1) + ". Players---(0/2)");
        availableGame[totalGameNo] = true;

        final boolean[] availableGameTemp = availableGame;

        gameList_ = new JList(listModel);
        gameList_.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameList_.setSelectedIndex(0);
        gameList_.setVisibleRowCount(5);
        gameList_.setBounds(75, 235, 300, 175);
        gameList_.setBorder(BorderFactory.createLineBorder(Color.black));

        // Add some Components to the panel
        this.add(titleBar);
        this.add(gameList_);
        this.add(selectPieces_);
        this.add(playerNames_);
        this.add(nameOne_);

        longNameError_ = new JLabel(
                "Please limit player names to a maximum of 20 characters");
        longNameError_.setFont(errorFont);
        longNameError_.setForeground(Color.red);
        longNameError_.setBounds(75, 425, 700, 30);
        longNameError_.setVisible(false);

        noNameError_ = new JLabel("Player must have a name");
        noNameError_.setFont(errorFont);
        noNameError_.setForeground(Color.red);
        noNameError_.setBounds(75, 425, 700, 30);
        noNameError_.setVisible(false);

        gameChosenError_ = new JLabel("Players must choose a game with an empty space");
        gameChosenError_.setFont(errorFont);
        gameChosenError_.setForeground(Color.red);
        gameChosenError_.setBounds(75, 425, 700, 30);
        gameChosenError_.setVisible(false);

        this.add(longNameError_);
        this.add(noNameError_);
        this.add(gameChosenError_);

        newGame_ = new JButton("Join Game");
        newGame_.setBounds(475, 275, 200, 60);
        newGame_.setFont(buttonFont);
        newGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                noNameError_.setVisible(false);
                longNameError_.setVisible(false);
                gameChosenError_.setVisible(false);

                if (noNames() && shortNames() && gameChosed()) //pieceError_.setVisible(false);
                {
                    namesAndIcons_ = new String[5];

                    new GameController(display_);
                    namesAndIcons_[0] = "" + 2;

                    namesAndIcons_[1] = nameOne_.getText();

                    //set name for AI player
                    namesAndIcons_[2] = nameTwo_;

                    //set icon for AI player
                    namesAndIcons_[4] = "Blue_circle";

                    //set icon for participant
                    namesAndIcons_[3] = "Red_circle";
                    //TESTING
                    //System.out.println(icons_[0].getSelection().getActionCommand());

                    NotificationManager.getInstance().notifyObservers(
                            Notification.START_GAME, namesAndIcons_);
                }
                /*if (options_ == null) {
                 options_ = new GameOptions(MainMenu.this);
                 } else {
                 options_.showFirstPagePanels();
                 options_.setVisible(true);
                 }
                 hideMenu();
                 */
            }

            public boolean shortNames() {
                if (nameOne_.getText().length() > 20) {
                    longNameError_.setVisible(true);
                    return false;
                }
                return true;
            }

            public boolean noNames() {
                if (nameOne_.getText().length() == 0) {
                    noNameError_.setVisible(true);
                    return false;
                }
                return true;
            }

            public boolean gameChosed() {
                int index = gameList_.getSelectedIndex();
                if (!availableGameTemp[index]) {
                    gameChosenError_.setVisible(true);
                    return false;
                }
                return true;
            }

        });
        this.add(newGame_);

        quitGame_ = new JButton("Quit Game");
        quitGame_.setBounds(475, 350, 200, 60);
        quitGame_.setFont(buttonFont);

        quitGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        this.add(quitGame_);
        /*
         networkGame_ = new JButton("Network Game");
         networkGame_.setBounds(((frameWidth - buttonWidth) / 2), buttonStart
         + (betweenSpace + buttonHeight), buttonWidth, buttonHeight);
         networkGame_.setFont(buttonFont);
         networkGame_.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
         if (networkedGame_ == null) {
         networkedGame_ = new NetworkedGame(MainMenu.this);
         } else {
         networkedGame_.showSecondPagePanels();
         //temp not showing this button
         networkedGame_.setVisible(true);
         }
         hideMenu();
         }

         });
         */
        // Add the Panel to the display
        DisplayAssembler display = DisplayAssembler.getInstance();
        display.addComponent(this, location, JLayeredPane.PALETTE_LAYER);

        // Set the visibility as true, thereby displaying it
        setVisible(true);

        // Add "this" to necessary event observer lists
        NotificationManager.getInstance().addObserver(Notification.START_GAME,
                this, "hideMenu");
    }

    // Can be used to remove all Buttons within the MainMenu class
    public void hidePanels() {
        newGame_.setVisible(false);
        rules_.setVisible(false);
        quitGame_.setVisible(false);
    }

    public void hideMenu() {
        setVisible(false);
    }

    public void showMenu() {
        setVisible(true);
    }

    public static void main(String[] args) {
        Display display = new Display();
        new MainMenu(display);

    }
}
