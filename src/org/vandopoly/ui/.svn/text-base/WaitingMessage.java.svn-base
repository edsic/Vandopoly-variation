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
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Displays a popup message without any buttons and also throws a show card notification
 * to stop user from taking any actions.
 * 
 * Initial function was to wait for trade proposals to go through and keep game concurrent.
 * 
 * @author James Kasten
 */
public class WaitingMessage extends JPanel {
	
	Popup popup;
	PopupFactory factory;
	JPanel basePanel;
	int xCoord, yCoord;
	
	static final long serialVersionUID = 183;
	
	public WaitingMessage(String message) {  	
		Vector<String> lines = processText(message);
		int numOfLabels = lines.size();
		
		factory = PopupFactory.getSharedInstance();
		
		basePanel = new JPanel();
		JPanel messagePanel = new JPanel();
		JLabel labels[] = new JLabel[numOfLabels];
				
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(1, 1, hGap, vGap);
		GridLayout gridLayout2 = new GridLayout(numOfLabels, 1, 0, 0);
		
		basePanel.setLayout(null);
		messagePanel.setLayout(null);
		
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		for (int i = 0; i < numOfLabels; ++i) {
			labels[i]  = new JLabel(lines.elementAt(i));
			labels[i].setFont(labelFont);
			messagePanel.add(labels[i]);
		}
		
		basePanel.setLayout(gridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
		messagePanel.setLayout(gridLayout2);
		messagePanel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, "Waiting");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(messagePanel);
		basePanel.setVisible(true);
	
		xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				basePanel.getPreferredSize().getWidth()) / 2;
		yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				basePanel.getPreferredSize().getHeight()) / 2;
		
		factory = PopupFactory.getSharedInstance();
		popup = factory.getPopup(this, basePanel, xCoord, yCoord);
    }
	
	public void showWaitingMessage() {
		popup = factory.getPopup(this, basePanel, xCoord, yCoord);
		popup.show();
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
	}
	
	public void hideWaitingMessage() {
		try {
			popup.hide();
			NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
