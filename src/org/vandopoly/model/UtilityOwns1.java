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
 * UtilityOwns1 class implements the behavior associated with the 
 * utility property space being owned.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class UtilityOwns1 extends UtilityState implements Serializable {
	
	final static long serialVersionUID = 230;

	private static UtilityOwns1 INSTANCE = null;
	
	protected UtilityOwns1() {
		// Exists to disable instantiation
	}
	
	public static UtilityState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UtilityOwns1();
		}
		
		return INSTANCE;
	}
	
	public void landOn(Player player, PropertySpace property) {
		//Rent is 4 times amount shown on dice
		System.out.println("Utility land on");
		NotificationManager.getInstance().notifyObservers(Notification.UTILITY_RENT, property);
	}
	
	public int getMultiplier() {
		return 4;
	}
	
	protected void ownershipIncrease(PropertySpace p) {
		p.changeState(UtilityOwns2.Instance());
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		System.err.println("UtiltiyOwns1 ownershipDecrease called - Not supposed to happen");
	}
	
	protected String getNameAndStatus() {
		return "";
	}

	public String toString() {
		return "UtilityOwns1";
	}
}
