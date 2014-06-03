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

package org.vandopoly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.ui.MessagePopUp;

/*
 * Model class that is a descendant of Space and represents a 
 * chance space on the board
 * 
 * @author Allie Mazzia
 */
public class ChanceCardSpace extends Space implements Serializable {
	
	Vector<Card> stack_;
	ListIterator<Card> itr_;
	ArrayList<Player> players_;
	private static ChanceCardSpace INSTANCE = null;
	public static final int NUMBER = 15;
	
	final static long serialVersionUID = 206;

	protected ChanceCardSpace(ArrayList<Player> players) {
		stack_ = new Vector<Card>(NUMBER);
		
		stack_.add(new CardTypeOutOfJail());
		stack_.add(new CardTypeMove("Take a walk to the Commons Center", 39));
		stack_.add(new CardTypeMove("Advance to Featheringill Hall", 24));
		stack_.add(new CardTypeWinMoney("Win $150 from taking the dining survey!", 150));
		stack_.add(new CardTypeMove("Take a walk to the Commons Center", 39));
		stack_.add(new CardTypeWinMoney("Complete a psychology study. Receive $20", 20));
		stack_.add(new CardTypePayFund("It's a new semester! Pay $100 for books.", 100));
		stack_.add(new CardTypeMove("Caught cheating on a test. You are" +
				" immediately placed on Academic Probation", 10));
		stack_.add(new CardTypeMove("Advance to GO", 0));
		stack_.add(new CardTypeWinMoney("Your parents send you an unexpected check!"
				+ " Collect $200.", 200));
		stack_.add(new CardTypeMove("Take a ride on the Reverse Route!", 5));
		stack_.add(new CardTypeMove("Visit friends. Go directly to Chaffin Place", 19));
		stack_.add(new CardTypePayFund("Lost your phone at a frat party. Pay $50 "
				+ "for a new one.", 50));
		stack_.add(new CardTypePayFund("Traffic & Parking got you again! Pay $25 "
				+ "for a ticket", 25)); 
		stack_.add(new CardTypePayPlayers("Elected Student Body President. " +
				"Pay each player $20.", 20));
		
		players_ = players;
		itr_ = stack_.listIterator();
	}
	
	public static ChanceCardSpace Instance(ArrayList<Player> players) {
		if (INSTANCE == null) {
			INSTANCE = new ChanceCardSpace(players);
		}
		
		return INSTANCE;
	}
	
	public String toString() {
		return "Chance";
	}
	
	public void landOn(Player p) {
		Card card = drawCard();
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, card);
		NotificationManager.getInstance().notifyObservers(Notification.MESSAGE_POPUP, 
				p.getName() + " drew the following Chance card: " + card.getMessage(), false);
		new MessagePopUp(card, p, players_, "Chance");
	}
	
	public Card drawCard() {
		
		if (itr_.hasNext())
			return itr_.next();
		else {
			while(itr_.hasPrevious()) {
				itr_.previous();
			}
			return itr_.next();
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(stack_);
	}
}
