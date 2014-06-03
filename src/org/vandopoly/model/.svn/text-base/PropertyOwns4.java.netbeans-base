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
 * PropertyOwns4 class implements the behavior associated with the 
 * property space being owned by a player who also owns three other property
 * spaces of the same kind.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PropertyOwns4 extends PropertyState implements Serializable {

	private static PropertyOwns4 INSTANCE = null;
	final static long serialVersionUID = 216;
	
	protected PropertyOwns4() {
		// Exists to disable instantiation
	}
	
	public static PropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyOwns4();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[3], player);
	}
	
	// Meant to represent when a new property of type PropertySpace is purchased
	protected void ownershipIncrease(PropertySpace p) {
		System.err.println("PropertyOwns4 ownershipIncrease called - not supposed to happen");
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		p.changeState(PropertyOwns3.Instance());
	}
	
	protected String getNameAndStatus() {
		return " (4 Owned)";
	}
	
	public String toString() {
		return "PropertyOwns4";
	}
}
