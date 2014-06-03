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

package org.vandopoly.ui;


/*
 * Represents a piece on the bottom side of the board moving left in direction
 * 
 * @author James Kasten
 */
public class PieceLeft extends PieceState {

	private static PieceLeft INSTANCE = null;

	protected PieceLeft() {
		// Exists to disable instantiation
	}

	public static PieceLeft Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceLeft();
		}

		return INSTANCE;
	}

	public void move(final Piece piece, int currentSpace, int newSpace) {
		while (currentSpace != newSpace) {
			piece.pixelX_ = moveSquareLeft(piece, piece.pixelX_, piece.pixelY_, 1);
			currentSpace++;

			// Move space appropriately in Corner Space
			if (currentSpace >= 10) {
				piece.pixelX_ = moveSquareLeft(piece, piece.pixelX_, piece.pixelY_, xBuffer);
				
				piece.pixelY_ = moveSquareUp(piece, piece.pixelX_, piece.pixelY_, leftDownYBuffer);
				
				piece.changeState(PieceUp.Instance());
				piece.getState().move(piece, currentSpace, newSpace);
				break;
			}
		}
	}
}