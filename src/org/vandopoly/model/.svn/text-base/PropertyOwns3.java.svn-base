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
 * PropertyOwns3 class implements the behavior associated with the 
 * property space being owned by a player who also owns two other property
 * spaces of the same kind.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PropertyOwns3 extends PropertyState implements Serializable {

	private static PropertyOwns3 INSTANCE = null;
	final static long serialVersionUID = 215;
	
	protected PropertyOwns3() {
		// Exists to disable instantiation
	}
	
	public static PropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyOwns3();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[2], player);
	}
	
	// Meant to represent when a new property of type PropertySpace is purchased
	protected void ownershipIncrease(PropertySpace p) {
		p.changeState(PropertyOwns4.Instance());
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		p.changeState(PropertyOwns2.Instance());
	}
	
	protected String getNameAndStatus() {
		return " (3 Owned)";
	}
	
	public String toString() {
		return "PropertyOwns3";
	}
}
