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
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * ActionMessage is a singleton that can take any String, animate it and display it
 * on the board for a few seconds before disappearing.  
 * 
 * To use just call ActionMessage.getInstance().newMessage(String message);
 * @author James Kasten
 */
public class ActionMessage extends JLabel {
	
	private static ActionMessage INSTANCE;
	
	private int screenWidth = DisplayAssembler.getScreenWidth();
	
	// Variables used for the bounds of the message
	private int leftSide = DisplayAssembler.getBoxSize() + 7;
	private int rightSide = leftSide + DisplayAssembler.getSpaceWidth() * 9;
	private int totalSize = rightSide - leftSide;
	
	// Contains all of the fonts used during the animation
	private Font actionFont[];
	private int numOfFonts = 35;
	
	static final long serialVersionUID = 101;

	// Singleton so that it can be used by all classes
	public static ActionMessage getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ActionMessage();
		
		return INSTANCE;
	}
	
	private ActionMessage() {
		actionFont = new Font[numOfFonts];
		
		for (int i = 0; i < numOfFonts; i++)
			actionFont[i] = new Font("broadway", Font.BOLD, (i+1));
		
		this.setForeground(Color.yellow);
		this.setBounds(0, 0, screenWidth, 100);
		this.setVisible(true);
		DisplayAssembler.getInstance().addComponent(this, 0, 0, JLayeredPane.POPUP_LAYER);
	}
	
	// Main method used to present a message
	public void newMessage(final String message) {
		
		final int maxFontSize = findProperSize(message);
		
		// Spawns a new thread for the animation
		new Thread() {
			public void run() {
				// Used so that multiple threads do not display at the same time.
				synchronized(ActionMessage.this) {
					
					// Removes old font and Text before displaying and going through loop
					ActionMessage.this.setFont(actionFont[0]);
					ActionMessage.this.setText(message);
					setLocation(message, 0);
					ActionMessage.this.setVisible(true);
				
					try {
						// Slowly increase font size and reset the location to center the text
						for (int i = 0; i < maxFontSize; i++) {
							setLocation(message, i);
							ActionMessage.this.setFont(actionFont[i]);
						
							Thread.sleep(8);
						}
						
						// After the text is done displaying, pause before removing it.
						Thread.sleep(2000);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
					ActionMessage.this.setVisible(false);
				}
			}
		}.start();
	}
	
	// Makes sure that the message will fit into the alotted range for the message
	// If not, change the max size of the font so that the message will fit
	private int findProperSize(String message) {
		int size = numOfFonts - 1;
		
		while(DisplayAssembler.getInstance().getFontMetrics(actionFont[size]).stringWidth(message) > totalSize) {
			size-=2;
		}
		
		return size;
	}
	
	// Centers the message on the screen between the rightSide and the leftSide
	private void setLocation(String message, int fontSelection) {
		
		// Font metrics are used because font letters are not all the same size
		FontMetrics metrics = DisplayAssembler.getInstance().getFontMetrics(actionFont[fontSelection]);
		
		DisplayAssembler.getInstance().animateComponentLocation(this, 
				(leftSide + ((rightSide - leftSide) / 2) - (metrics.stringWidth(message)) / 2), 
				leftSide - metrics.getHeight() + 10);
	}
}
