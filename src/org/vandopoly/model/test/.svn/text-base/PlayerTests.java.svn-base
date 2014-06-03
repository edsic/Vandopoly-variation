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

package org.vandopoly.model.test;

import org.vandopoly.model.Player;
import org.vandopoly.model.PlayerFree;
import org.vandopoly.model.PlayerInJail;

/*
 * This class is used to test state transitions and
 * rent collection/payment.
 */
public class PlayerTests {

	public static void main(String[] args) {
		Player A = new Player(0, "Bob");
		Player B = new Player(1, "Mark");

		A.goToJail();
		
		// Test 1 checks that the goToJail() function correctly
		// sends a player to jail (ie changes their state to 'InJail')
		if (A.getState() == PlayerInJail.Instance()) {
			System.out.println("Test 1: PASS");
		}
		else
			System.out.print("Test 1: FAIL");
		
		// Test 2 checks that players are created in the 
		// correct state (ie Free)
		if (B.getState() == PlayerFree.Instance()) {
			System.out.println("Test 2: PASS");
		}
		else
			System.out.println("Test 2: FAIL");
		
		A.collectRent(100, B);
		
		// Test 3 checks that the collectRent() function adds
		// the correct amount of cash to the correct player
		if (A.getCash() == 1600)
			System.out.println("Test 3: PASS");
		else
			System.out.println("Test 3: FAIL");
		
		A.updateCash(10);
		B.collectRent(10, A);
		
		// Tests 4 and 5 check that updateCash() and 
		// collectRent() work correctly for both players 
		if (A.getCash() == 1600)
			System.out.println("Test 4: PASS");
		else
			System.out.println("Test 4: FAIL");
		
		if (B.getCash() == 1410)
			System.out.println("Test 5: PASS");
		else
			System.out.println("Test 5: FAIL");
		
		A.getOutOfJail();
		B.goToJail();
		
		// Tests 6 and 7 check the goToJail() and getOutOfJail()
		// functions
		if (B.getState() == PlayerInJail.Instance()) {
			System.out.println("Test 6: PASS");
		}
		else
			System.out.print("Test 6: FAIL");
		
		if (A.getState() == PlayerFree.Instance()) {
			System.out.println("Test 7: PASS");
		}
		else
			System.out.print("Test 7: FAIL");
	}

}
