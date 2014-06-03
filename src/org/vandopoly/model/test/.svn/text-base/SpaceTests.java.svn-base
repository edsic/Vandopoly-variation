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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.vandopoly.controller.GameController;
import org.vandopoly.model.CornerSpace;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.Space;
import org.vandopoly.model.TaxSpace;
import org.vandopoly.model.UpgradeablePropertySpace;

/*
 * SpaceTests is a JUnit testing class that is meant to test the space class
 * and all children of the space class.
 * 
 * @author James Kasten
 */
public class SpaceTests extends TestCase {
	Space normalSpace;
	PropertySpace property;
	
	Player samplePlayer, samplePlayer2;
	
	Space board[] = new Space[5];
	
	// Sets up "shared" objects among tests.
	// Any changes to objects during a test is confined to that test
	protected void setUp() throws Exception {
		super.setUp();
		normalSpace = new Space();
		
		new GameController(null);
		property = new PropertySpace();
		property.setPurchasePrice(100);
		
		board[0] = new UpgradeablePropertySpace("Memorial Gym", 0, 0, 100, 50, 25, 100, 200, 400, 500, 750);
		board[1] = new PropertySpace("Railroad", 8, 1, 200, 100, 25, 50, 100, 200);
		board[2] = new PropertySpace("Railroad2", 8, 2, 200, 100, 25, 50, 100, 200);
		board[3] = new CornerSpace("Scholarship Fund");
		board[4] = new TaxSpace("Pay Tuition");
		
		samplePlayer = new Player(1, "James");
		
		samplePlayer2 = new Player(2, "Frank");
	}
	
	// Must be used to cleanup after a .setUp()
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Takes all functions that have .testXXX...() and turns them into
	// separate tests
	public static Test suite(){
		return new TestSuite(SpaceTests.class);
	}
	// Basic set test
	public void testSetName() {
		normalSpace.setName("Space1");
		assertTrue(normalSpace.getName().equals("Space1"));
	}
	// Testing constructor
	public void testPropertyValue() {
		assertTrue(property.getPurchasePrice() == 100);
	}
	// Testing purchasing of property
	public void testPurchase() {
		samplePlayer.purchase((PropertySpace)board[0]);
		System.out.println(samplePlayer.getCash());
		assertTrue(samplePlayer.getCash() == 1400);
	}
	// Testing rent payments
	public void testRent() {
		samplePlayer.purchase((PropertySpace)board[0]);
		board[0].landOn(samplePlayer2);
		assertTrue(samplePlayer2.getCash() == 1475 && samplePlayer.getCash() == 1425);
	}
	// Testing tax space
	public void testTaxSpace() {
		board[4].landOn(samplePlayer);
		assertTrue(samplePlayer.getCash() == 1350);
	}
	// Testing mortgage
	public void testMortgage() {
		samplePlayer.purchase((PropertySpace)board[0]);
		samplePlayer.mortgage((PropertySpace)board[0]);
		board[0].landOn(samplePlayer2);
		assertTrue(samplePlayer2.getCash() == 1500 && samplePlayer.getCash() == 1450);
	}
	// Test unmortgage
	public void testUnmortgage() {
		samplePlayer.purchase((PropertySpace)board[0]);
		samplePlayer.mortgage((PropertySpace)board[0]);
		samplePlayer.unmortgage((PropertySpace)board[0]);
		assertTrue(samplePlayer.getCash() == 1400);
	}
	// Test level increase
	public void testLevelIncrease() {
		UpgradeablePropertySpace property = (UpgradeablePropertySpace)board[0];
		samplePlayer.purchase(property);
		property.renovate();
		property.landOn(samplePlayer2);
		
		assert(samplePlayer2.getCash() == 1400);
		assert(samplePlayer.getCash() == 1350);
	}
	
	// Test a property increasing in level then decreasing
	public void testLevelIncreaseDecrease() {
		UpgradeablePropertySpace p = (UpgradeablePropertySpace)board[0];
		samplePlayer.purchase(p);
		p.renovate();
		p.downgrade();
		p.landOn(samplePlayer2);
		
		assert(samplePlayer2.getCash() == 1475);
		assert(samplePlayer.getCash() == 1375);
	}
	
	// Test property
	public void testPropertyOwns1() {
		PropertySpace p = (PropertySpace)board[1];
		samplePlayer.purchase(p);
		p.landOn(samplePlayer2);
		assert(samplePlayer2.getCash() == 1475);
	}
	
	// Test Property Auto increase with owning two properties
	public void testPropertyOwns2() {
		PropertySpace p = (PropertySpace)board[1];
		PropertySpace p2 = (PropertySpace)board[1];
		samplePlayer.purchase(p);
		samplePlayer.purchase(p2);
		p.landOn(samplePlayer2);
		
		assert(samplePlayer2.getCash() == 1450);
	}
}