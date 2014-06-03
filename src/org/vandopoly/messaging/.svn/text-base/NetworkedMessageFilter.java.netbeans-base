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

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*
 * NetworkedMessageFilter is a class used to subscribe to all notifications
 * and push them onto a message queue to be sent over the socket connection
 * to the client.
 * 
 * @author Allie Mazzia
 */

public class NetworkedMessageFilter {
	public ArrayList<NetworkedMessage> messageQueue_ = null;

	// For ensuring that two methods are not both attempting to add/remove
	// at the same time
	private Semaphore addRemoveLock;

	public NetworkedMessageFilter() {
		messageQueue_ = new ArrayList<NetworkedMessage>(20);

		// Subscribe to all notifications and have each call
		// addToQueue()

		NotificationManager.getInstance().addObserver(Notification.START_GAME, this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.ROLL_DICE,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.DONE_ROLLING,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.UNOWNED_PROPERTY,
		// this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.END_TURN, this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.UPDATE_SCHOLARSHIP_FUND,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.AWARD_SCHOLARSHIP_FUND,
		// this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.UPDATE_PROPERTIES, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.UPDATE_CASH, this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.SHOW_CARD,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.CARD_MOVE,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.REMOVE_CARD,
		// this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.CHANGED_OWNER, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.GO_TO_JAIL, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.USED_JAIL_CARD, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.GAINED_JAIL_CARD, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_TO, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_SPACES, this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.END_TURN_EARLY,
		// this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.ACTION_MESSAGE, this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.UTILITY_RENT,
		// this, "addToQueue");
		// NotificationManager.getInstance().addObserver(Notification.DISABLE_PURCHASE,
		// this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.END_TURN_UPDATE, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.TRADE_PROPOSED, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.TRADE_ACCEPTED, this, "addToQueue");
		NotificationManager.getInstance().addObserver(Notification.REMOVE_PLAYER, this, "addToQueue");

		addRemoveLock = new Semaphore(1);
	}

	public void addToQueue(Object obj, String eventName, boolean isTerminal) {
		// Encapsulate each object in a NetworkedMessage and
		// add it to the queue
		if (!isTerminal) {
			NetworkedMessage temp = new NetworkedMessage(eventName, obj);
			queueAdd(temp);
		}
	}

	public synchronized void queueAdd(NetworkedMessage message) {
		messageQueue_.add(message);
		this.notifyAll();
	}

	public synchronized NetworkedMessage queueRemove() {
		NetworkedMessage temp = null;

		while (messageQueue_.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		temp = messageQueue_.remove(0);

		return temp;
	}
}