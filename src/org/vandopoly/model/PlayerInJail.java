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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * PlayerFree class implements the behavior associated with the player 
 * being in jail.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PlayerInJail extends PlayerState implements Serializable{

	private static PlayerInJail INSTANCE = null;
	final static long serialVersionUID = 211;
	
	protected PlayerInJail() {
		// Exists to disable instantiation
	}
	
	public static PlayerState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerInJail();
		}
		
		return INSTANCE;
	}

	@Override
	public void movePiece(Player player, int spaces) {
		// Must get out of Jail somehow
	}
	
	public void movePiece(Player player, Dice dice) {

		int i = player.getNumOfRolls();
		player.setNumOfRolls(++i);
		
		if ((dice.getDie1() == dice.getDie2()) || (i > 2)) {	
			NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
					player.getName() + " has been removed from Academic Probation!");
			
			getOutOfJail(player);
			player.updatePosition(dice.getTotalRoll());
			player.setNumOfRolls(0);
		}
		
		// If they got out because they rolled three times, they are forced 
		// to pay 50
		if (i == 3)
			player.updateCash(-50);

	}
	
	@Override
	public void collectRent(Player payee, int amount, Player payer) {
		if (payer.getIndex() != payee.getIndex()) {
			payee.updateCash(amount);
			payer.updateCash(-amount);
			NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
					payer.getName() + " paid $" + amount + " in rent to " + payee.getName());
		}
	}
	
	@Override
	public void goToJail(Player player) {
		// Empty - player is already in jail
	}
	
	@Override
	public void getOutOfJail(Player player) {
		player.changeState(PlayerFree.Instance());
	}
	
	public String toString() {
		return "PlayerInJail";
	}

}
