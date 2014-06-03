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

package org.vandopoly.messaging.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.vandopoly.messaging.NotificationManager;

/*
 * MessagingTests
 * Uses the JUnit testing library to run multiple tests.
 * The tests have no crossover effects on one another and can be run
 * at the same time
 * 
 * @author James Kasten
 */
public class MessagingTests extends TestCase {
	
	private String EVENT1 = "Test";
	private String EVENT2 = "Test2";
	private String EVENT3 = "Test3";
	private String EVENT4 = "Test4";
	
	private Boolean withParameter = false;
	private Boolean withParameter2 = false;
	private Boolean noParameter = false;
	
	private Boolean twoParameters = false;
	private Boolean threeParameters = false;
	
	// Sets up "shared" objects among tests.
	// Any changes to objects during a test is confined to that test
	protected void setUp() throws Exception {
		super.setUp();
		NotificationManager manager = NotificationManager.getInstance();
		
		manager.addObserver(EVENT1, this, "callbackWithParameter");
		manager.addObserver(EVENT1, this, "callbackNoParameter");
		manager.addObserver(EVENT1, this, "cb3Parameters");
		manager.addObserver(EVENT2, this, "callbackWithParameter2");
		manager.addObserver(EVENT3, this, "cb2Parameters");
		manager.addObserver(EVENT4, this, "cb3Parameters");
	}
	
	// Must be used to cleanup after a .setUp()
	protected void tearDown() throws Exception {
		super.tearDown();
		NotificationManager.getInstance().removeEventObserver(this);
	}
	
	// Takes all functions that have .testXXX...() and turns them into
	// separate tests
	public static Test suite(){
		return new TestSuite(MessagingTests.class);
	}
	
	// Tests both a parameter function and a no parameter function
	public void testNotifyObservers1() {
		NotificationManager.getInstance().notifyObservers(EVENT1, null);
		assertTrue(withParameter);
		assertTrue(noParameter);
		assertTrue(!withParameter2);
		assertTrue(!twoParameters);
		// Terminal should be false so this should return false
		assertTrue(!threeParameters);
	}
	
	// Test if the parameter function is recognized and is called appropriately while not calling any others
	public void testNotifyObservers2() {
		NotificationManager.getInstance().notifyObservers(EVENT2, null);
		assertTrue(withParameter2);
		assertTrue(!withParameter);
		assertTrue(!noParameter);
		assertTrue(!twoParameters);
	}
	
	// Test if the 2 parameter function is recognized and is called appropriately
	public void testTwoParameter() {
		NotificationManager.getInstance().notifyObservers(EVENT3, null);
		assertTrue(twoParameters);
		assertTrue(!withParameter2);
		assertTrue(!withParameter);
		assertTrue(!noParameter);
	}
	
	// Test if the 3 parameter function is recognized and is called appropriately
	public void testThreeParameter() {
		NotificationManager.getInstance().notifyObservers(EVENT4, null, true);
		assertTrue(threeParameters);
		assertTrue(!twoParameters);
		assertTrue(!withParameter2);
		assertTrue(!withParameter);
		assertTrue(!noParameter);
	}
	
	// Should print out a red line that no observers were subscribed
	public void testNoObservers() {
		NotificationManager.getInstance().notifyObservers("NoObservers", null);
		System.out.println("Should Print out \"No observers have ever subscribed "+
				"to event NoObservers\"");
	}
	
	// Should produce 2 red line statements in the console window
	public void testMultipleAddObserver() {
		NotificationManager.getInstance().addObserver(EVENT1, this, "callbackWithParameter");
		NotificationManager.getInstance().addObserver(EVENT1, this, "callbackWithParameter");
		assertTrue(true);
		System.out.println("If two red Class: class org.vandopoly... messages are printed "+
				"above performed correctly");
	}
	
	public void callbackWithParameter(Object updatedObject) {
		withParameter = true;
	}	
	
	public void callbackNoParameter() {
		noParameter = true;
	}
	public void callbackWithParameter2(Object updatedObject) {
		withParameter2 = true;
	}
	public void cb2Parameters(Object obj, String event) {
		if(event.equals(EVENT3))
			twoParameters = true;
	}
	public void cb3Parameters(Object obj, String event, boolean terminal) {
		threeParameters = terminal;
	}
}
