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
 * Represents a piece on the right side of the board moving in the down direction
 * 
 * @author James Kasten
 */
public class PieceDown extends PieceState {
	
	private static PieceDown INSTANCE = null;
	
	protected PieceDown() {
		// Exists to disable instantiation
	}
	
	public static PieceDown Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceDown();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		assert(newSpace < 40);
		
		while(currentSpace != newSpace) {
					
			piece.pixelY_ = moveSquareDown(piece, piece.pixelX_, piece.pixelY_, 1);
					
			currentSpace = (currentSpace + 1) % 40;
			
			// Move piece appropriately in Corner Space
			if (currentSpace == 0) {	
				piece.pixelY_ = moveSquareDown(piece, piece.pixelX_, piece.pixelY_, leftDownYBuffer);
				piece.pixelX_ = moveSquareLeft(piece, piece.pixelX_, piece.pixelY_, xBuffer);
				
				piece.changeState(PieceLeft.Instance());
				piece.getState().move(piece, currentSpace, newSpace);
				break;
			}
		}
	}
}