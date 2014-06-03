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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;


/*
 * TradeProposalPopUp is used in networked games when a player proposes a trade
 * to another player. Allows a player to accept or reject a trade
 * 
 * @author Allie Mazzia
 */
public class TradeProposalPopUp {

	public TradeProposalPopUp(ArrayList<String[]> tradesProposed, Player proposer) {
		final ArrayList<String[]> trades = tradesProposed;
		final Player proposingPlayer = proposer;
		
		int cash0 = Integer.parseInt(trades.get(0)[0]);
		String[] properties0 = trades.get(1);
		int cash1 = Integer.parseInt(trades.get(2)[0]);
		String[] properties1 = trades.get(3);
		
		JPanel basePanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
	
		// Create layouts and buttons
		int hGap = 15;
		GridLayout baseGridLayout = new GridLayout(3, 1, 0, 0);
		GridLayout gridLayout1 = new GridLayout(1, 1, 0, 0);
		GridLayout gridLayout2 = new GridLayout(1, 2, 0, 0);
	
		// The pop-up should have enough rows to list each property on one row,
		// so we must calculate which set of properties is largest
		int maxRows = 0;
		if (properties0.length > properties1.length) 
				maxRows = properties0.length + 2;
		else 
				maxRows = properties1.length + 2;
		
		GridLayout gridLayout3 = new GridLayout(maxRows, 2, hGap, 0);
		System.out.println("maxrows: " + maxRows);
		
		basePanel.setLayout(null);
		panel1.setLayout(null);
		panel2.setLayout(null);
		panel3.setLayout(null);
		
		Font labelFont = new Font("arial", Font.PLAIN, 20);
		Font buttonFont = new Font("arial", Font.PLAIN, 18);
		Font titleFont = new Font("arial", Font.PLAIN, 14);
		Font listFont = new Font("arial", Font.PLAIN, 17);
	
		JLabel titleLabel = new JLabel(proposingPlayer.getName() + " would like to trade with you.");
		titleLabel.setFont(labelFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.setLayout(gridLayout1);
		panel1.add(titleLabel);
		panel1.setBackground(new Color(236, 234, 166));
		
		JLabel subTitle1 = new JLabel("They will give you:");
		subTitle1.setFont(labelFont);
		subTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		panel3.setLayout(gridLayout3);
		panel3.add(subTitle1);
		panel3.setBackground(new Color(236, 234, 166));
		
		JLabel subTitle2 = new JLabel("In exchange for:");
		subTitle2.setFont(labelFont);
		subTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		panel3.add(subTitle2);

		JLabel cashLabel1 = new JLabel("Cash: $" + cash0);
		cashLabel1.setFont(listFont);
		panel3.add(cashLabel1);
		cashLabel1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel cashLabel2 = new JLabel("Cash: $" + cash1);
		cashLabel2.setFont(listFont);
		cashLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel3.add(cashLabel2);

		int i = 0;
		int j = 0;
		boolean addLeft = true;
		while ((i < properties0.length) || (j < properties1.length)) {
			JLabel temp;
			if (addLeft && (i < properties0.length)) {
				temp = new JLabel(properties0[i]);
				temp.setFont(listFont);
				temp.setHorizontalAlignment(SwingConstants.CENTER);
				panel3.add(temp);
				addLeft = false;
				++i;
			}
			else if (addLeft && (i >= properties0.length)) {
				panel3.add(new JLabel("   "));
				addLeft = false;
			}
			else if (!addLeft && (j < properties1.length)) {
				temp = new JLabel(properties1[j]);
				temp.setFont(listFont);
				temp.setHorizontalAlignment(SwingConstants.CENTER);
				panel3.add(temp);
				addLeft = true;
				++j;
			}
			else {
				panel3.add(new JLabel("    "));
				addLeft = true;
			}
		}

		JButton accept = new JButton("Accept");
		accept.setFont(buttonFont);
		accept.setVisible(true);
	
		JButton reject = new JButton("Reject");
		reject.setFont(buttonFont);
		reject.setVisible(true);
	
		panel2.add(accept);
		panel2.add(reject);
		panel2.setLayout(gridLayout2);
	
		basePanel.setLayout(baseGridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
	
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(blackline, "Trade Proposal");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(panel1);
		basePanel.add(panel3);
		basePanel.add(panel2);
		basePanel.setVisible(true);
	
		int xCoord = (int) (DisplayAssembler.getScreenWidth() - basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int) (DisplayAssembler.getScreenHeight() - basePanel.getPreferredSize().getHeight()) / 2;
	
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);
	
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				popup.hide();
				String proposerName[] = {proposingPlayer.getName()};
				trades.add(proposerName);
				NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
				NotificationManager.getInstance().notifyObservers(Notification.TRADE_ACCEPTED, trades, true);
			}
		});
		reject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				popup.hide();
				NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
				// Sends trade decision of "No Trade"
				NotificationManager.getInstance().notifyObservers(Notification.TRADE_REJECTED, null, true);
			}
		});
	
		popup.show();
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
	}

}
