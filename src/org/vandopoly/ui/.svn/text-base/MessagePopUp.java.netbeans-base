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
import java.util.StringTokenizer;
import java.util.Vector;

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
import org.vandopoly.model.Card;
import org.vandopoly.model.Player;

/*
 * MessagePopUp class is a replica of the CardPanel class but used for displaying popups
 * for Corners Spaces and other necessary necessary game messages.
 * 
 * @author James Kasten & Allie Mazzia
 */
public class MessagePopUp extends JPanel {
	
	static final long serialVersionUID = 102;
	
	// Constructor used to display generic messages
	public MessagePopUp(String message) {  	
		Vector<String> lines = processText(message);
		int numOfLabels = lines.size();
		
		JPanel basePanel = new JPanel();
		JPanel messagePanel = new JPanel();
		JLabel labels[] = new JLabel[numOfLabels];
				
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
		GridLayout gridLayout2 = new GridLayout(numOfLabels, 1, 0, 0);
		
		basePanel.setLayout(null);
		messagePanel.setLayout(null);
		
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		for (int i = 0; i < numOfLabels; ++i) {
			labels[i]  = new JLabel(lines.elementAt(i));
			labels[i].setFont(labelFont);
			messagePanel.add(labels[i]);
		}
		
		JButton ok = new JButton("OK");
		ok.setFont(buttonFont);
		ok.setVisible(true);
		
		basePanel.setLayout(gridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
		messagePanel.setLayout(gridLayout2);
		messagePanel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, "Message");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(messagePanel);
		basePanel.add(ok);
		basePanel.setVisible(true);
	
		int xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				basePanel.getPreferredSize().getHeight()) / 2;
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(this, basePanel, xCoord, yCoord);
		
		ok.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        }
	
	    });
	
		popup.show();	
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
	
    }
	
	// Overloaded constructor used to display card messages
	public MessagePopUp(Card c, Player p, ArrayList<Player> players, String spaceName) {  	
		final Card card = c;
		final Player player = p;
		final ArrayList<Player> playerList = players;		
		
		Vector<String> lines = processText(card.getMessage());
		int numOfLabels = lines.size();
		
		JPanel basePanel = new JPanel();
		JPanel messagePanel = new JPanel();
		JLabel labels[] = new JLabel[numOfLabels];
				
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
		GridLayout gridLayout2 = new GridLayout(numOfLabels, 1, 0, 0);
		
		basePanel.setLayout(null);
		messagePanel.setLayout(null);
		
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		for (int i = 0; i < numOfLabels; ++i) {
			labels[i]  = new JLabel(lines.elementAt(i));
			labels[i].setFont(labelFont);
			messagePanel.add(labels[i]);
		}

		JButton ok = new JButton("OK");
		ok.setFont(buttonFont);
		ok.setVisible(true);
		basePanel.setLayout(gridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
		messagePanel.setLayout(gridLayout2);
		messagePanel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, spaceName);
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(messagePanel);
		basePanel.add(ok);
		basePanel.setVisible(true);
	
		int xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				basePanel.getPreferredSize().getHeight()) / 2;
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(this, basePanel, xCoord, yCoord);
		
		ok.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        	card.landOn(player, playerList);
	        }
	        
	    });

		popup.show();	
    }
	
	// Used to parse the message into appropriate-length strings
	private Vector<String> processText(String message) {

		int maxpos = 40;
		
		//Create a new Vector to hold the trimmed words
		Vector<String> trimmedWords = new Vector<String>(30);
		Vector<String> lines = new Vector<String>(5);
		
		//Use the StringTokenizer class to trim the whitespace from the words
		StringTokenizer words = new StringTokenizer(message);
		while (words.hasMoreTokens())
		{
			trimmedWords.addElement(words.nextToken());
		}
		
		//Iterate through the Vector, printing an error message if a word is reached
		// that is longer than <maxpos> and storing a count of how many characters
		// have been printed on a line
		int count = 0;
		String temp = null;
		for (int i = 0; i < trimmedWords.size(); i = i + 1)
		{
			String currentWord = trimmedWords.elementAt(i);
			if(currentWord.length() > maxpos)
			{
				System.out.print("Error: word found longer than " + maxpos + " characters. Exiting.");
				return null;
			}
			//At the beginning of a new line, so simply print the word
			if (count == 0)
			{
				temp = currentWord;
				count = currentWord.length();
			}
			//The next word is too long for the current line, so we must
			// print the word on a new line
			else if ((count + currentWord.length() + 1) > maxpos)
			{
				lines.add(temp);
				temp = currentWord;
				count = currentWord.length();
			}
			//There is space on the current line for the next word
			else
			{
				temp += " " + currentWord;
				count = count + currentWord.length() + 1;
			}
		}
		lines.add(temp);
		return lines;
	}

}
