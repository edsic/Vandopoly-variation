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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.SpaceMortgaged;
import org.vandopoly.model.UpgradeablePropertySpace;

/*
 * PropertySelectionPanel is allows the user to sell or downgrade his properties if he runs out of money
 * so he can join back in the game.
 * 
 * @author Matt Gioia
 */
public class PropertySelectionSellPanel implements ListSelectionListener, Serializable {

	final static long serialVersionUID = 336;
	
	private final ArrayList<PropertySpace> propertyList;
	private JButton mortgage, defeat, downgrade;
	
	JPanel panel;
	final DefaultListModel model;

	int panelWidth = 400;
	int panelHeight = 300;
	int buttonHeight = 50;
	int listHeight = 120;
	int titleHeight = 60;
	int padding = 5;
	
	public PropertySelectionSellPanel(final Player player) {

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.PINK);
		panel.setSize(panelWidth, panelHeight);
		panel.setLocation((int)((DisplayAssembler.getScreenWidth() - panelWidth) / 2),
				(int)((DisplayAssembler.getScreenHeight() - panelHeight) / 2));

		propertyList = player.getProperties();

		model = new DefaultListModel();

		for (int i = 0; i < propertyList.size(); i++)
			model.addElement(propertyList.get(i).getNameAndStatus());

		final JList list = new JList(model);
		// list.addKeyListener(keyTypedListener);
		final ListSelectionModel listSelection = list.getSelectionModel();
		listSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelection.addListSelectionListener(this);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(new TitledBorder("Select Properties"));
		scrollPane.setBackground(Color.PINK);
		scrollPane.setBounds(0, titleHeight + padding, panelWidth, listHeight);
		scrollPane.setVisible(true);

		Font buttonFont = new Font("arial", Font.PLAIN, 18);

		// Set up the defeat button
		defeat = new JButton("Accept Defeat");
		defeat.setFont(buttonFont);
		defeat.setBounds(0, listHeight + buttonHeight + titleHeight + padding, panelWidth,
				buttonHeight);
		
		defeat.setVisible(true);
		
		defeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(player.getCash() > 0) {
					panel.setVisible(false);
					NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
				}
				else {
					// remove player
					ActionMessage.getInstance().newMessage("You Lost!");
					NotificationManager.getInstance().notifyObservers(Notification.REMOVE_PLAYER, player);
					panel.setVisible(false);
				}
			}
		});
		
		// Set up the downgrade button
		downgrade = new JButton("Downgrade");
		downgrade.setFont(buttonFont);
		downgrade.setBounds((panelWidth / 2), listHeight + titleHeight + padding, (panelWidth / 2),
				buttonHeight);
		downgrade.setVisible(true);
		downgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection.getAnchorSelectionIndex();

				// Represents case when no properties are selected
				if (index < 0) {
					System.out.println(player.getName() + " tried to downgrade: "
							+ index + " which is invalid");
				}
				else {
					player.downgradeProperty((UpgradeablePropertySpace)propertyList.get(index));
					model.set(index, propertyList.get(index).getNameAndStatus());
					
					int newIndex = findNextIndexIncrease(index);
					list.setSelectedIndex(newIndex);
				}
				checkCash(player);
			}

		});
		
		// Set up the mortgage button
		mortgage = new JButton("Mortgage");
		mortgage.setFont(buttonFont);
		mortgage.setBounds(0, titleHeight + padding + listHeight, (panelWidth / 2), buttonHeight);
		mortgage.setVisible(true);

		mortgage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection.getAnchorSelectionIndex();

				// Represents case when no properties are selected
				if (index < 0) {
					System.out.println(player.getName() + " tried to mortage: "
							+ index + " which is invalid");
				}
				// If property selected is currently being mortgaged
				else if (!propertyList.get(index).getState().equals(SpaceMortgaged.Instance())) {
					player.mortgage(propertyList.get(index));
					repaint();
					
					// Toggle Buttons Appropriately
					PropertySelectionSellPanel.this.toggleButtons(propertyList.get(index));
				} 
				else if (propertyList.get(index).getState().equals(SpaceMortgaged.Instance())) {
					player.unmortgage(propertyList.get(index));
					repaint();
					
					// Toggle Buttons Appropriately
					PropertySelectionSellPanel.this.toggleButtons(propertyList.get(index));
				}
				checkCash(player);
			}

		});

		Font titleFont = new Font("arial", Font.PLAIN, 26);
		JLabel titleBar = new JLabel("You Are Out of Money!");
		titleBar.setFont(titleFont);
		titleBar.setBounds(titleHeight - padding * 2, 0, panelWidth, titleHeight);
		
		panel.add(titleBar);
		panel.add(scrollPane);
		panel.add(defeat);
		panel.add(mortgage);
		panel.add(downgrade);

		panel.setVisible(true);
		
		DisplayAssembler.getInstance().addComponent(panel, DisplayAssembler.getScreenWidth()/2 - panelWidth/2, 
				DisplayAssembler.getScreenHeight()/2 - panelHeight/2,
				JLayeredPane.POPUP_LAYER);
	}
	
	/**
	 * Reloads all properties from the original property list.
	 */
	private void repaint() {
		for (int i = 0; i < propertyList.size(); i++)
			model.set(i, propertyList.get(i).getNameAndStatus());
	}

	/**
	 * Used after degrading, findNext Index finds the next highest property of the same type
	 */
	private int findNextIndexIncrease(int curIndex) {
		int newIndex = curIndex + 1;
		
		if(curIndex >= propertyList.size() - 1 || propertyList.get(curIndex).getType() != propertyList.get(newIndex).getType()) {
			newIndex = curIndex - 1;
			
			// Then move down until the types no longer match
			while((newIndex >= 0
					&& propertyList.get(curIndex).getType() == propertyList.get(newIndex).getType()))
				newIndex--;
			
			// Move up to the first property of that type that does match
			newIndex++;
		}
		
		return newIndex;
	}

	// This function is called twice everytime the "valueChanges"?  Part of Swing?
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		toggleButtons(propertyList.get(lsm.getMinSelectionIndex()));
	}

	private void checkCash(Player player) {
		if(player.getCash() > 0) {
			defeat.setText("Join Back In The Game");
		}
	}
	
	private void toggleButtons(PropertySpace selectedProperty) {
		// Control downgrade button
		if (selectedProperty.isDowngradeable())
			downgrade.setEnabled(true);
		else
			downgrade.setEnabled(false);

		// Control Mortgage button
		if (selectedProperty.isRenovated())
			mortgage.setEnabled(false);
		else
			mortgage.setEnabled(true);

		// Update the button words based on state of property
		if (selectedProperty.getState().equals(SpaceMortgaged.Instance()))
			mortgage.setText("Unmortgage");
		 else
			mortgage.setText("Mortgage");
	}
}
