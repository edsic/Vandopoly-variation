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
 * Piece State is the base state of the Piece state pattern
 * 
 * @author James Kasten
 */
public class PieceState {
	DisplayAssembler display = DisplayAssembler.getInstance();

	protected double xBuffer = .8;
	protected double upRightYBuffer = .5;
	protected double leftDownYBuffer = .4;

	protected void changeState(Piece piece, PieceState newState) {
		piece.changeState(newState);
	}

	protected void move(Piece piece, int currentSpace, int newSpace) {

	}

	protected int moveSquareLeft(final Piece piece, int currentX, final int currentY, double percent) {
		
		for (int i = 0; i < DisplayAssembler.getSpaceWidth() * percent; i++) {
			display.animateComponentLocation(piece.getIcon(), --currentX, currentY);
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		return currentX;
	}

	protected int moveSquareRight(Piece piece, int currentX, int currentY, double percent) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth() * percent; i++) {
			display.animateComponentLocation(piece.getIcon(), ++currentX, currentY);
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		return currentX;
	}

	protected int moveSquareUp(Piece piece, int currentX, int currentY, double percent) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth() * percent; i++) {
			display.animateComponentLocation(piece.getIcon(), currentX, --currentY);
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		return currentY;
	}

	protected int moveSquareDown(Piece piece, int currentX, int currentY, double percent) {
		for (int i = 0; i < DisplayAssembler.getSpaceWidth() * percent; i++) {
			display.animateComponentLocation(piece.getIcon(), currentX, ++currentY);
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		return currentY;
	}
}