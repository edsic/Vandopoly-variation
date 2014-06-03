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

/*
 * SpaceState class defines an interface for encapsulating the behavior
 * associated with a particular Concrete State.
 * State class for the State pattern.
 * 
 * Parent of UpgradeablePropertyState, PropertyState and UtilityState
 * 
 * @author Allie Mazzia
 */
public class SpaceState implements Serializable {
	final static long serialVersionUID = 238;
	
	protected void changeState(PropertySpace space, SpaceState newState) {
		space.changeState(newState);
	}
	
	protected void landOn(Player player, PropertySpace property) {}
	
	protected boolean isUpgradeable(UpgradeablePropertySpace p) {
		return false;
	}
	
	protected boolean isDowngradeable(UpgradeablePropertySpace p) {
		return false;
	}
	
	// All normal properties (railroads/utilities) do not consult the state for renovated info
	protected boolean isRenovated(UpgradeablePropertySpace p) {
		return true;
	}
	
	protected void renovate(UpgradeablePropertySpace p) {
		System.err.println("SpaceState renovate called - Not supposed to happen");
                System.out.println("SpaceState renovate called");
	}
	
	protected void downgrade(UpgradeablePropertySpace p) {
		System.err.println("SpaceState downgrade called - Not supposed to happen");
	}
	
	// Meant to represent when a new property of type PropertySpace is purchased
	protected void ownershipIncrease(PropertySpace p) {
		System.err.println("SpaceState ownershipIncrease called - Not supposed to happen");
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		System.err.println("SpaceState ownershipDecrease called - Not supposed to happen");
	}
	
	public int getLevel() {
		return 0;
	}
	
	// Used by the utility states
	protected int getMultiplier() {
		// This should never get called
		return 0;
	}
	
	protected String getNameAndStatus() {
		return "";
	}
}
