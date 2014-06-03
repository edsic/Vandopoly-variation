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
 * SpaceMortgaged class implements the behavior associated with the 
 * property space being owned and mortgaged.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class SpaceMortgaged extends SpaceState implements Serializable {
	
	private static SpaceMortgaged INSTANCE = null;
	final static long serialVersionUID = 220;
	
	protected SpaceMortgaged() {
		// Exists to disable instantiation
	}
	
	public static SpaceState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new SpaceMortgaged();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		//Do nothing as the space is mortgaged
	}
	
	protected boolean isRenovated(UpgradeablePropertySpace p) {
		return false;
	}
	
	protected String getNameAndStatus() {
		return " (Mortgaged)";
	}
	
	// Meant to represent when a new property of type PropertySpace is purchased
	protected void ownershipIncrease(PropertySpace p) {
		// Do nothing
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		// Do nothing
	}
	
	public String toString() {
		return "SpaceMortgaged";
	}
}
