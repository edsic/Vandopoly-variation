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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.SpaceMortgaged;
import org.vandopoly.model.UpgradeablePropertySpace;

/*
 * PropertySelectionPanel is meant to provide a generic way to present an ArrayList of properties.
 * It should be usable for all presentations of multiple properties and should be adaptable to 
 * mortgaging, renovating, and selling renovations.
 * 
 * @author James Kasten
 */
public class PropertySelectionPanel implements ListSelectionListener {

	private final ArrayList<PropertySpace> propertyList;
	private JButton mortgage, renovate, downgrade;

	JFrame frame;
	final DefaultListModel model;

	int panelWidth = 350;
	int panelHeight = 260;
	int buttonHeight = 50;
	int listHeight = 120;

	public PropertySelectionPanel(final Player player) {
		
		frame = new JFrame("Monopoly Properties Owned");
		frame.setLayout(null);
		frame.setSize(panelWidth, panelHeight - 50); //minus 50 as removing mortage button
		frame.setLocation((int)((DisplayAssembler.getScreenWidth() - panelWidth) / 2),
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
		scrollPane.setBounds(0, 0, panelWidth, listHeight);
		scrollPane.setVisible(true);

		Font buttonFont = new Font("arial", Font.PLAIN, 18);

		// Set up the renovate button
		renovate = new JButton("Renovate");
		renovate.setFont(buttonFont);
		renovate.setBounds(0, listHeight, (panelWidth / 2), buttonHeight);
		renovate.setVisible(true);
		
		renovate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int index = listSelection.getAnchorSelectionIndex();

				// Represents case when no properties are selected
				if (index < 0) {
					System.out.println(player.getName() + " tried to renovate: "
							+ index + " which is invalid");
				}
				else {
					
					boolean renovated = player.renovateProperty((UpgradeablePropertySpace)propertyList.get(index));
					model.set(index, propertyList.get(index).getNameAndStatus());
					
					if (renovated) {
						int newIndex = findNextIndexDecrease(index);
						list.setSelectedIndex(newIndex);
					}
				}
			}
		});
		
		// Set up the downgrade button
		downgrade = new JButton("Downgrade");
		downgrade.setFont(buttonFont);
		downgrade.setBounds((panelWidth / 2), listHeight, (panelWidth / 2),
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
			}

		});
		
		// Set up the mortgage button
		/*
                mortgage = new JButton("Mortgage");
		mortgage.setFont(buttonFont);
		mortgage.setBounds(0, listHeight + buttonHeight, panelWidth,
				buttonHeight);
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
				else if (!propertyList.get(index).getState().toString().equals(SpaceMortgaged.Instance().toString())) {
					player.mortgage(propertyList.get(index));
					repaint();
					
					// Toggle Buttons Appropriately
					PropertySelectionPanel.this.toggleButtons(propertyList.get(index));
				} 
				else if (propertyList.get(index).getState().toString().equals(SpaceMortgaged.Instance().toString())) {
					player.unmortgage(propertyList.get(index));
					repaint();
					
					// Toggle Buttons Appropriately
					PropertySelectionPanel.this.toggleButtons(propertyList.get(index));
				}
			}
                });
                */

		frame.add(scrollPane);
		frame.add(renovate);
		//frame.add(mortgage);
		frame.add(downgrade);

		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Reloads all properties from the original property list.
	 */
	private void repaint() {
		for (int i = 0; i < propertyList.size(); i++)
			model.set(i, propertyList.get(i).getNameAndStatus());
	}
	
	/**
	 * Used after upgrading findNext Index finds the next lowest property of the same type
	 */
	private int findNextIndexDecrease(int curIndex) {
		int newIndex = curIndex - 1;
		
		if(curIndex == 0 || propertyList.get(curIndex).getType() != propertyList.get(newIndex).getType()) {
			newIndex = curIndex + 1;
			
			// Then move up until the types no longer match
			while((newIndex < propertyList.size() 
					&& propertyList.get(curIndex).getType() == propertyList.get(newIndex).getType()))
				newIndex++;
			
			// Move down to the first property of that type that does match
			newIndex--;
		}
		
		return newIndex;
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

	public void dispose() {
		frame.dispose();
	}

	// This function is called twice everytime the "valueChanges"?  Part of Swing?
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();

		toggleButtons(propertyList.get(lsm.getMinSelectionIndex()));
	}

	private void toggleButtons(PropertySpace selectedProperty) {

		// Control Renovate button
		if (selectedProperty.isUpgradeable())
			renovate.setEnabled(true);
		else
			renovate.setEnabled(false);

		// Control downgrade button
		if (selectedProperty.isDowngradeable())
			downgrade.setEnabled(true);
		else
			downgrade.setEnabled(false);

		// Control Mortgage button
		/*
                if (selectedProperty.isRenovated())
			mortgage.setEnabled(false);
		else
			mortgage.setEnabled(true);
                        

		// Update the button words based on state of property
		if (selectedProperty.getState().toString().equals(SpaceMortgaged.Instance().toString()))
			mortgage.setText("Unmortgage");
		 else
			mortgage.setText("Mortgage");
                        */
	}
}