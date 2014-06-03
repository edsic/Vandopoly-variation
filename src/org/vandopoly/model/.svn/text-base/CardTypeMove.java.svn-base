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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * The CardTypeMove class represents a "Chance" or "Community Chest" card
 * that requires the player who drew the card to move to a specified space
 *
 * @author Allie Mazzia
 */
public class CardTypeMove extends Card implements Serializable {
	private int space_;
	final static long serialVersionUID = 201;
	
	public CardTypeMove() {
		message_ = "NONE";
		space_ = 0;
	}

	public CardTypeMove(String message, int space) {
		message_ = message;
		if (space > 39) {
			System.out.print("Invalid space number passed to CardTypeMove()");
			space_ = space % 40;
		}
		space_ = space;
	}
	
	public void setSpace(int space) {
		if (space > 39) {
			System.out.print("Invalid space number passed to CardTypeMove()");
			space_ = space % 40;
		}
		space_ = space;
	}

	public int getSpace() {
		return space_;
	}
	
	public void landOn(Player p, ArrayList<Player> players) {
		p.setPosition(getSpace());
		NotificationManager.getInstance().notifyObservers(Notification.CARD_MOVE, new Integer(getSpace()));
		if (getSpace() == 10)
			p.goToJail();
	}
}
