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
import java.util.Random;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Dice class is a model class simulating the roll of a dice
 * 
 * @author James Kasten
 */
public class Dice implements Serializable {
	private static final long serialVersionUID = 107;
	private int die1_, die2_;
	private int numInRowDoubles_;
	private Random generator_ = new Random();
	
	// Can be used to roll both dice at once and return the total
	public int roll() {
		die1_ = generator_.nextInt(6) + 1;
		die2_ = generator_.nextInt(6) + 1;
		
		if (die1_ != die2_)
			numInRowDoubles_ = 0;
		else if (numInRowDoubles_ < 4)
			numInRowDoubles_++;
		
		// Represents case where previous player went to Jail, counter must be reset
		else
			numInRowDoubles_ = 1;
		
		// Notify interested parties about the change of Die state
		NotificationManager.getInstance().notifyObservers(Notification.ROLL_DICE, this);
		
		return die1_ + die2_;
	}
	
	// Roll the dies individually and return their value
	public int rollDie1() {
		// The generator gets an integer from 0 up to but not including 6
		// therefore we must add one to it.
		die1_ = generator_.nextInt(6) + 1;
		return die1_;
	}
	public int rollDie2() {
		die2_ = generator_.nextInt(6) + 1;
		return die2_;
	}
	
	// Get the value of each die
	public int getDie1() {
		return die1_;
	}
	public int getDie2() {
		return die2_;
	}
	public int getTotalRoll() {
		return die1_ + die2_;
	}
	public int getNumInRowDoubles() {
		return numInRowDoubles_;
	}
	// toString method
	public String toString() {
		return "(" + this.getDie1() + ", " + this.getDie2() + ")";
	}
	
}

