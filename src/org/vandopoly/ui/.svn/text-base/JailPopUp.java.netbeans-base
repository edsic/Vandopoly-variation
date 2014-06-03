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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;

/*
 * JailPopUp is used to create the window that appears when a player
 * who is in jail begins their turn - allows them to choose between
 * paying $50, rolling or (if they have one) using a get out of jail free card
 * 
 * @author Allie Mazzia
 */
public class JailPopUp {
	static final long serialVersionUID = 102;
	Player player_;
	
	public JailPopUp(Player p) {  	
		player_ = p;
		JPanel basePanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		//Create layouts and buttons
		int hGap = 10, vGap = 10;
		GridLayout baseGridLayout = new GridLayout(2, 1, hGap, vGap);
		GridLayout gridLayout1 = new GridLayout(2, 1, 0, 0);
		GridLayout gridLayout2 = new GridLayout(1, 3, 0, 0);
		
		basePanel.setLayout(null);
		panel1.setLayout(null);
		panel2.setLayout(null);
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		JLabel label = new JLabel(player_.getName() + ", you're on Academic Probation.");
		JLabel label2 = new JLabel("What would you like to do?");
		label.setFont(labelFont);
		label2.setFont(labelFont);
		panel1.setLayout(gridLayout1);
		panel1.add(label);
		panel1.add(label2);
		panel1.setBackground(new Color(236, 234, 166));
		
		JButton card = new JButton("Use Card");
		card.setFont(buttonFont);
		card.setVisible(true);
		card.setEnabled(p.hasGetOutOfJail());
		
		JButton pay = new JButton("Pay $50");
		pay.setFont(buttonFont);
		pay.setVisible(true);
		
		JButton roll = new JButton("Roll");
		roll.setFont(buttonFont);
		roll.setVisible(true);
		
		panel2.add(card);
		panel2.add(pay);
		panel2.add(roll);
		panel2.setLayout(gridLayout2);
		
		basePanel.setLayout(baseGridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, "Academic Probation");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(panel1);
		basePanel.add(panel2);
		basePanel.setVisible(true);
	
		int xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				basePanel.getPreferredSize().getHeight()) / 2;
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);
		
		card.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        	player_.getOutOfJail();
	        	player_.usedGetOutOfJail();
	        }
		});
		pay.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        	player_.getOutOfJail();
	        	player_.updateCash(-50);
	        }
	    });
		
		roll.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        }
	    });
	
		popup.show();	
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
    }
}
