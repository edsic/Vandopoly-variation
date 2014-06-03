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


/*  The Notification class is intended to store all known events as public
 *  static strings.  These event strings are intended to be used with the
 *  NotificationManager singleton, so that other classes can simply call
 *  NotificationManager.getInstance().addObserver(Notification.START_GAME,this,update).
 *  
 *  @author James Kasten
 */
public class Notification {
	
	// Game Events
	
	// Start Game is sent from the GameOptions class
	public static final String START_GAME = "StartGame";
	
	// Roll Dice is sent from the Dice class
	public static final String ROLL_DICE = "RollDice";
	
	// Done Rolling is sent from the DicePanel Class
	// Sent from the DicePanel because if it was sent from the Dice class, the
	// GameButtonsPanel would be updated before the animation was complete
	// Note: Could present a problem for networking as it effects the GameButtonPanel
	public static final String DONE_ROLLING = "DoneRolling";
	
	// UNOWNED_PROPERTY is sent from a property space
	// Meant to tell the purchase button that it is available for purchase
	public static final String UNOWNED_PROPERTY = "UnownedProperty";
	
	// End Turn is sent from the GameController class method actionListener
	// Note: Could present a problem for networking as it effects the GameButtonPanel and DicePanel
	public static final String END_TURN = "EndTurn";
	
	// Update Scholarship Fund sends integer amounts from which to add or subtract from the
	// scholarship fund, used with tax and card spaces
	public static final String UPDATE_SCHOLARSHIP_FUND = "UpdateScholarshipFund";
	
	// Award Scholarship Fund sends a player object who will receive the award
	// Sent in the CornerSpace class, observed by GameControllers
	public static final String AWARD_SCHOLARSHIP_FUND = "AwardScholarshipFund";
	
	// Update Properties sends a copy of the Player object that is sending the notification
	// so that the observing class can correctly update the properties owned
	public static final String UPDATE_PROPERTIES = "UpdateProperties";
	
	// Update Cash sends a copy of the Player object that is sending the notification
	// so that the observing class can correctly update the amount of cash 
	public static final String UPDATE_CASH = "UpdateCash";
	
	// Show Card sends the information from the card so that all observing classes can properly update
	// their cash or other data members
	public static final String SHOW_CARD = "ShowCard";
	
	// Special notification sent only when a CardTypeMove is drawn, to have the Game Controller
	// landOn() the specified space
	public static final String CARD_MOVE = "CardMove";
	
	// Sends a null object when the user clicks "OK" on a Chance/Comm Chest card
	public static final String REMOVE_CARD = "RemoveCard";
	
	// Sends a Property object when the owner has changed
	public static final String CHANGED_OWNER = "ChangedOwner";
	
	// Send the current player to Jail... sent by DicePanel for 3 doubles in a row
	// Does not include object
	public static final String GO_TO_JAIL = "GoToJail";
	
	// Sends a Player object to notify when players have been removed from jail by
	// using a get out of jail free card
	public static final String USED_JAIL_CARD = "UsedJailCard";
	
	// Sends a Player object to notify when players gain a get out of jail free card
	public static final String GAINED_JAIL_CARD = "GainedJailCard";
	
	// Used by Player to move piece.  Notifies game controller and contains an integer
	// referring to the space number the piece should be moved to
	public static final String PIECE_MOVE_TO = "PieceMoveTo";
	
	// Used by Player to move piece a certain number of spaces.  Notifies game controller
	// containing an integer pertaining to how many spaces to move
	public static final String PIECE_MOVE_SPACES = "PieceMoveSpaces";
	
	// Sent when the "Roll Dice" button must be disabled early - like when a player
	// rolls doubles, but they land on the "Go to Academic Probation" space
	// ** May be possible to combine this with "DoneRolling"
	public static final String END_TURN_EARLY = "EndTurnEarly";
	
	// Sent when an action message is needed - to decouple player from ui
	public static final String ACTION_MESSAGE = "ActionMessage";
	
	// Sent when a user selects their player for a networked game
	public static final String PLAYER_SELECTED = "PlayerSelected";
	
	// Sent from Utility States to the game controller to charge appropriate rent based on the dice
	// The property is sent along for rent collection information.
	public static final String UTILITY_RENT = "UtilityRent";
	
	// Sent during a networked game to update the connected player on the status of the game
	// after a turn has ended
	public static final String END_TURN_UPDATE = "EndTurnUpdate";
	
	// Sent when a trade is proposed in a networked game
	public static final String TRADE_PROPOSED = "TradeProposed";
	
	// Sent when a trade is accepted in a networked game
	public static final String TRADE_ACCEPTED = "TradeAccepted";
	
	// Used to reject a trade and remove the waiting message from the opponents computer
	public static final String TRADE_REJECTED = "TradeRejected";
	
	// Trade Finalized
	public static final String TRADE_FINALIZED = "TradeFinalized";
	
	// Used to open the trade waiting message
	// It must be closed with a TradeFinalized notification
	public static final String TRADE_WAIT_MESSAGE = "TRADE_WAIT_MESSAGE";
	
	// Sent over the network when a player lands on a card space so that
	// the other player can see their card.
	public static final String MESSAGE_POPUP = "MessagePopUp";
	
	// Sent when a player loses the game
	public static final String REMOVE_PLAYER = "RemovePlayer";
	
	// Sent after a player loses the game
	public static final String REMOVED_PLAYER = "RemovedPlayer";
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// Button Functionality
	////////////////////////////////////////////////////////////////////////////////////////////
	
	// Disable Purchase is simply a message sent from Player.purchase and the DicePanel used to 
	// stop a player from pushing the purchase button when the player should no longer be able
	// to purchase a property due to the player moving, or already purchasing the property
	public static final String DISABLE_PURCHASE = "DisablePurchase";
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// Timing
	///////////////////////////////////////////////////////////////////////////////////////////
	
	// Sent after the dice animation is finished, so pieces don't move before dice
	// are "done" rolling
	public static final String DICE_ANIMATION_DONE = "DiceAnimationDone";
}
