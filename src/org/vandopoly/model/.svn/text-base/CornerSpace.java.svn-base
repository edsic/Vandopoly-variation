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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Model class that is a descendant of Space and represents a corner of the board
 * 
 * @author Allie Mazzia
 */
public class CornerSpace extends Space implements Serializable {
	final static long serialVersionUID = 208;
	
	public CornerSpace() {
		name_ = "NONE";
	}
	
	public CornerSpace(String name) {
		name_ = name;
	}
	
	public void landOn(Player p) {
		if (this.name_.equals("Scholarship Fund"))
			NotificationManager.getInstance().notifyObservers(Notification.AWARD_SCHOLARSHIP_FUND, p);
		else if (this.name_.equals("Go On Academic Probation")) {
			NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
					p.getName() + " has been put on Academic Probation!");
			p.goToJail();
		}
		else if (this.name_.equals("GO")) {
			p.updateCash(400);
			NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
					p.getName() + " has been awarded $400 for landing on GO!");
		}
	}

}
