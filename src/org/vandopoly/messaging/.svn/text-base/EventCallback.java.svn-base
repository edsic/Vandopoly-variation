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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * EventCallback is a class that can store both an object and a method that needs to be updated
 * EventCallback is meant to be used with NotificationManager and is based off of Objective C's
 * iPhone callbacks.
 * 
 * @author James Kasten
 */
public class EventCallback {

	private Object object_;
	private Method method_;

	public EventCallback(Object object, Method method) {
		object_ = object;
		method_ = method;
	}

	public void notifyObserver(Object updatedObject, String event, boolean terminal) {
		try {
			if (method_.getParameterTypes().length == 1)
				method_.invoke(object_, updatedObject);
			else if (method_.getParameterTypes().length == 0)
				method_.invoke(object_);
			else if (method_.getParameterTypes().length == 2)
				method_.invoke(object_, updatedObject, event);
			else if (method_.getParameterTypes().length == 3)
				method_.invoke(object_, updatedObject, event, terminal);
			else
				System.err.println(this + " does not contain 0 or 1 parameter");
		} catch (IllegalAccessException e) {
			String error = "An IllegalAccessException has occured while trying to"
					+ "notify "	+ object_.getClass() + " with method " + method_.getName();
			System.err.println(error);
		} catch (InvocationTargetException e) {
			String error = "An InvocationTargetException has occured while trying to"
					+ "notify " + object_.getClass() + " with method " + method_.getName()
					+ "\n I suggest going to target method, catching exception and printing stack trace";
			System.err.println(error);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			String error = "An IllegalArgumentException has occured while trying to"
					+ "notify " + object_.getClass() + " with method " + method_.getName()
					+ "\n Remember all methods that are updated are expected to have either"
					+ " an object parameter, an object and String parameter, or no parameters at all";
			System.err.println(error);
		}
	}

	// Needed for the HashMap
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventCallback))
			return false;
		EventCallback callback = (EventCallback) obj;

		return object_.equals(callback.object_)
				&& method_.equals(callback.method_);
	}

	public Object getObject() {
		return object_;
	}

	public Method getMethod() {
		return method_;
	}

	public String toString() {
		return "Class: " + object_.getClass() + ", Method: "
				+ method_.getName();
	}
}
