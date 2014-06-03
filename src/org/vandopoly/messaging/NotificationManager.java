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

package org.vandopoly.messaging;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/*
 * NotificationManager is a Singleton designed to act as a centralized
 * Publisher/Subscriber Notification service.  The Singleton centralized approach 
 * was chosen to make networked games a feasible task in the future. 
 * 
 * To ensure that messaging doesn't grow out of control, objects will have the opportunity
 * to subscribe to specific events.  The flexibility
 * in subscribing should allow the NotificationManager to work for all of the 
 * projects messaging needs.
 * 
 * Important: Event subscribing requires that a unique method name be associated 
 * with "update()" function.  So update(Player player) and update(Property prop) are
 * considered the same function and the addObserver function will pick the function
 * arbitrarily.
 * 
 * @author James Kasten
 */
public class NotificationManager {
	// Used for event subscribing
	// EventCallback holds the object to be updated and the method to be called
	private HashMap<String, ArrayList<EventCallback>> listsOfEventObservers_ = null;
	private static NotificationManager INSTANCE = null;
	
	Class twoParameters[] = {Object.class, String.class};
	Class threeParameters[] = {Object.class, String.class, boolean.class};
	
	private NotificationManager() {
		listsOfEventObservers_ = new HashMap<String, ArrayList<EventCallback>>();
	}
	
	public static NotificationManager getInstance() {
		if (INSTANCE == null)
			INSTANCE = new NotificationManager();
		
		return INSTANCE;		
	}
	
	private void addObserver(String event, Object subscriber, Method callBackMethod) {
		//Check if an ArrayList doesn't exist yet for key value
		if (!listsOfEventObservers_.containsKey(event))
			listsOfEventObservers_.put(event, new ArrayList<EventCallback>());
		
		EventCallback observer = new EventCallback(subscriber, callBackMethod);
		ArrayList<EventCallback> list = listsOfEventObservers_.get(event);
		
		if (!list.contains(observer))
			list.add(observer);
		else
			System.err.println(observer + " multiple attempts to subscribe to " + event);
	}
	
	// The public interface for addObserver specifically for events.
	public void addObserver(String event, Object subscriber, String callbackMethodName) {
		Method callbackMethod = null;
		
		// Attempt to find a method with the same name and an Object as the parameter
		// Throws an exception if there isn't a method of this type
		try {
			callbackMethod = subscriber.getClass().getMethod(callbackMethodName, Object.class);
		}
		// Essentially catch the potential exception and continue trying to find the type of method
		catch(Exception e) {
		}
		
		// If the previous try didn't work, look for a method that does not have any parameters
		if (callbackMethod == null) {
			try {
				callbackMethod = subscriber.getClass().getMethod(callbackMethodName);
			}
			catch(Exception e) {
			}
		}
		
		// Try to find a method with 2 parameters... Object and event name
		if (callbackMethod == null) {
			try {
				callbackMethod = subscriber.getClass().getMethod(callbackMethodName, twoParameters);
			}
			catch(Exception e) {
			}
		}
		
		// Finally for networking check if there method is looking for a terminal flag.
		if (callbackMethod == null) {
			try {
				callbackMethod = subscriber.getClass().getMethod(callbackMethodName, threeParameters);
			}
			catch (Exception e) {
			}
			
			// Print error message if still cannot find an appropriate method
			if(callbackMethod == null) {
				String error = "Unable to find " + callbackMethodName + " in class " +
						subscriber.getClass() + " and has therefore not been added as a " +
						"subscriber to " + event + " Events";
				System.err.println(error);
			}
		}
		
		addObserver(event, subscriber, callbackMethod);
	}
	
	// Completely removes observer object from all event Observer lists
	public void removeEventObserver(Object obj) {
		Collection<ArrayList<EventCallback>> unionObservers = listsOfEventObservers_.values();
		
		for (ArrayList<EventCallback> observerList : unionObservers) {
			
			//Iterate from high to low because ArrayList shifts everything down
			for (int i = observerList.size() - 1; i >= 0; i--) {
				EventCallback observer = observerList.get(i);
				if (observer.getObject().equals(obj))
					observerList.remove(i);
			}
		}
	}
	
	// Removes an observer object from a specific event list
	public void removeEventObserver(String event, Object obj) {
		ArrayList<EventCallback> observerList = listsOfEventObservers_.get(event);
		
		//Iterate from high to low because ArrayList shifts everything down
		for (int i = observerList.size() - 1; i >= 0; i--) {
			EventCallback observer = observerList.get(i);
			if (observer.getObject().equals(obj))
				observerList.remove(i);
		}
	}
	
	//***************************************************************************
	// Watch Out: Not protected from removals while notifying - Fix after testing
	//***************************************************************************
	// This will cause errors - suggestion - use some sort of synchronized or semaphore
	// Typical use player.notifyObservers(PURCHASE_PROPERTY, this);
	public void notifyObservers(String event, Object updatedObject) {
		notifyObservers(event, updatedObject, false);
	}
	
	// Used for networking and effectively stopping the notification from propagating over the wire
	public void notifyObservers(String event, Object updatedObject, boolean terminal) {
		ArrayList<EventCallback> observerList = listsOfEventObservers_.get(event);

		if (observerList != null) {
			for (int i = observerList.size() - 1; i >= 0; i--)
				observerList.get(i).notifyObserver(updatedObject , event, terminal);
		}
		else
			System.err.println("No Observers have ever subscribed to event "+event);
	}
}