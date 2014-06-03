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
 * Model class to represent a space on the board
 * 
 * @author Allie Mazzia
 */
public class Space implements Serializable {
	final static long serialVersionUID = 219;
	
	protected String name_;
	
	public Space() {
		name_ = "NONE";
	}
	
	public Space(Space space) {
		name_ = space.getName();
	}
	
	public void landOn(Player p) {}
	
	public String toString() {
		return "Property Name: " + name_;
	}

	// Getters and setters
	public void setName(String name) {
		name_ = name;
	}

	public String getName() {
		return name_;
	}
}
