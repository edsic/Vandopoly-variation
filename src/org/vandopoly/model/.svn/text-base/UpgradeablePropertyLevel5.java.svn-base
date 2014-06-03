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
 * UpgradeablePropertyLevel5 class implements the behavior associated with the 
 * upgradeable property space being upgraded to level 5 (highest).
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class UpgradeablePropertyLevel5 extends UpgradeablePropertyState implements Serializable{
	
	private static UpgradeablePropertyLevel5 INSTANCE = null;
	final static long serialVersionUID = 227;
	
	protected UpgradeablePropertyLevel5() {
		// Exists to disable instantiation
	}
	
	public static UpgradeablePropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradeablePropertyLevel5();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[5], player);
	}
	
	protected void renovate(UpgradeablePropertySpace p) {
		System.err.println("Renovate called on UpgradeablePropertyLevel5");
	}
	
	protected void downgrade(UpgradeablePropertySpace p) {
		p.changeState(UpgradeablePropertyLevel4.Instance());
		NotificationManager.getInstance().notifyObservers
		(Notification.UPDATE_PROPERTIES, new Player(p.getOwner()));
	}
	
	protected boolean isUpgradeable(UpgradeablePropertySpace p) {
		return false;
	}
	
	protected boolean isDowngradeable(UpgradeablePropertySpace p) {
		return true;
	}
	
	protected String getNameAndStatus() {
		return " (Level 5)";
	}
	
	public int getLevel() {
		return 5;
	}
	
	public String toString() {
		return "UpgradeablePropertyLevel5";
	}
}
