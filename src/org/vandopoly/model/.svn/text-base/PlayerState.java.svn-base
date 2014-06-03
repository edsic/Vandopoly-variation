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
 * PlayerState class defines an interface for encapsulating the behavior
 * associated with a particular Concrete State.
 * State class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PlayerState implements Serializable{
	
	final static long serialVersionUID = 212;
	
	public void movePiece(Player player, int spaces) {};
	
	public void movePiece(Player player, Dice dice) {};
	
	public void collectRent(Player payee, int amount, Player player) {};
	
	public void goToJail(Player player) {};
	
	public void getOutOfJail(Player player) {};
	
	protected void changeState(Player player, PlayerState newState) {
		player.changeState(newState);
	}

}
