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
import java.util.ArrayList;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Model class that is a descendant of Space and represents a tax space on the board
 * 
 * @author Allie Mazzia
 */
public class TaxSpace extends Space implements Serializable {
	private int fee_;
	private double percentageFee_;
	
	final static long serialVersionUID = 222;
	
	public TaxSpace() {
		name_ = "NONE";
		fee_ = 0;
		percentageFee_ = 0.0;
	}

	public TaxSpace(String name) {
		name_ = name;
		fee_ = 0;
		percentageFee_ = 0.0;
	}
	
	public TaxSpace(String name, int fee, double percentageFee) {
		name_ = name;
		fee_ = fee;
		percentageFee_ = percentageFee;
	}
	
	public void landOn(Player p) {
		int assets = p.getCash();
		ArrayList<PropertySpace> properties = p.getProperties();
		for (int i = 0; i < properties.size(); ++i) {
			assets += properties.get(i).getMortgageValue();
		}
		
		int value = 0;
		String message = p.getName() + " paid $";
		
		// Pay the minimum of either 10% of current assets or 200 dollar fee
		if(name_.equals("Pay Tuition")) {
			value = Math.min((int)(assets * .1), 200);
			message += value + " in tuition";
		}
		// Represents luxury tax or Parking Ticket space
		else {
			value = 75;
			message += value + " in parking tickets";
		}
		
		
		p.updateCash(-value);
		NotificationManager.getInstance().notifyObservers(Notification.UPDATE_SCHOLARSHIP_FUND, 
				new Integer(value));
		NotificationManager.getInstance().notifyObservers(Notification.ACTION_MESSAGE, 
				message);
			
	}

	// Getters and setters
	public void setFee(int fee) {
		this.fee_ = fee;
	}

	public int getFee() {
		return fee_;
	}

	public void setPercentageFee(double percentageFee) {
		percentageFee_ = percentageFee;
	}

	public double getPercentageFee() {
		return percentageFee_;
	}

}
