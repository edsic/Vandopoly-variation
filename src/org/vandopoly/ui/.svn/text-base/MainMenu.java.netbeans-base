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

// Import the swing and AWT classes needed
package org.vandopoly.ui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
	
	static final long serialVersionUID = 2;
	
	public MainMenu(Display d) {
		display_ = d;
		int frameWidth = 529, frameHeight = 480;
		int buttonWidth = 350, buttonHeight = 75;
		
		// Space between buttons and the Y component of first button
		int betweenSpace = 20, buttonStart = 180;

		// Set size of window
		this.setSize(frameWidth, frameHeight);
		
		// Allows for automatic positioning
		this.setLayout(null);
		
		// Center the frame on the user's screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int)(screen.getWidth() - frameWidth) / 2,
				((int)(screen.getHeight() - frameHeight) / 2));
		
		// Set up the title bar along with positioning and size
		JLabel titleBar = new JLabel();
		ImageIcon title = new ImageIcon("images/vandopoly-logo.gif");
		titleBar.setIcon(title);
		titleBar.setBounds(0, 0, frameWidth, 159);

		
		// Setup and create all 3 buttons
		Font buttonFont = new Font("broadway", Font.BOLD, 32);

		newGame_ = new JButton("New Game");
		newGame_.setBounds(((frameWidth - buttonWidth) / 2), buttonStart, buttonWidth, buttonHeight);
		newGame_.setFont(buttonFont);
		newGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (options_ == null)
                	options_ = new GameOptions(MainMenu.this);
                else {
                	options_.showFirstPagePanels();
                	options_.setVisible(true);
                }
                hideMenu();
            }

        });

		networkGame_ = new JButton("Network Game");
		networkGame_.setBounds(((frameWidth - buttonWidth) / 2), buttonStart + 
				(betweenSpace + buttonHeight), buttonWidth, buttonHeight);
		networkGame_.setFont(buttonFont);
		networkGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (networkedGame_ == null)
                	networkedGame_ = new NetworkedGame(MainMenu.this);
                else {
                	networkedGame_.showSecondPagePanels();
                	networkedGame_.setVisible(true);
                }
                hideMenu();
            }

        });
		
		quitGame_ = new JButton("Quit Game");
		quitGame_.setBounds(((frameWidth - buttonWidth) / 2), buttonStart + 
				(betweenSpace + buttonHeight) * 2, buttonWidth, buttonHeight);
		quitGame_.setFont(buttonFont);
		
		quitGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

		// Add some Components to the panel
		add(titleBar);
		add(newGame_);
		add(networkGame_);
		add(quitGame_);
		
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