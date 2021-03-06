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
 * UpgradeablePropertyLevel1 class implements the behavior associated with the 
 * upgradeable property space being upgraded to level 1.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class UpgradeablePropertyLevel1 extends UpgradeablePropertyState implements Serializable {
	
	private static UpgradeablePropertyLevel1 INSTANCE = null;
	final static long serialVersionUID = 223;
	
	protected UpgradeablePropertyLevel1() {
		// Exists to disable instantiation
	}
	
	public static UpgradeablePropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradeablePropertyLevel1();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[1], player);
	}
	
	protected void renovate(UpgradeablePropertySpace p) {
		p.changeState(UpgradeablePropertyLevel2.Instance());
		NotificationManager.getInstance().notifyObservers
		(Notification.UPDATE_PROPERTIES, new Player(p.getOwner()));
		
	}
	
	protected void downgrade(UpgradeablePropertySpace p) {
		p.changeState(PropertyOwned.Instance());
		NotificationManager.getInstance().notifyObservers
		(Notification.UPDATE_PROPERTIES, new Player(p.getOwner()));
	}
	
	protected boolean isUpgradeable(UpgradeablePropertySpace p) {
		return p.getOwner().monopolyUpgradeValid(p);
	}
	
	protected boolean isDowngradeable(UpgradeablePropertySpace p) {
		return p.getOwner().monopolyDowngradeValid(p);
	}
	
	protected String getNameAndStatus() {
		return " (Level 1)";
	}
	
	public int getLevel() {
		return 1;
	}
	
	public String toString() {
		return "UpgradeablePropertyLevel1";
	}
}
