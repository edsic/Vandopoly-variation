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
 * SpaceUnowned class implements the behavior associated with the 
 * property space being unowned.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class SpaceUnowned extends SpaceState implements Serializable{

	private static SpaceUnowned INSTANCE = null;
	final static long serialVersionUID = 221;
	
	protected SpaceUnowned() {
		// Exists to disable instantiation
	}
	
	public static SpaceState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new SpaceUnowned();
		}
		
		return INSTANCE;
	}
	
	public void landOn(Player player, PropertySpace property) {
            if (!(player instanceof AIPlayer)) {
		NotificationManager.getInstance().notifyObservers(Notification.UNOWNED_PROPERTY, property);
            }
	}
	
	public String toString() {
		return "SpaceUnowned";
	}
}
