package org.vandopoly.ui;


/*
 * Represents a piece on the left side of the board moving in the up direction
 * 
 * @author James Kasten
 */
public class PieceUp extends PieceState {
	
	private static PieceUp INSTANCE = null;
	
	protected PieceUp() {
		// Exists to disable instantiation
	}
	
	public static PieceUp Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceUp();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		
		while(currentSpace != newSpace) {
			
			piece.pixelY_ = moveSquareUp(piece, piece.pixelX_, piece.pixelY_, 1);
			currentSpace++;
			
			// Move space appropriately in Corner Space
			if (currentSpace >= 20) {
				piece.pixelY_ = moveSquareUp(piece, piece.pixelX_, piece.pixelY_, upRightYBuffer);
				
				piece.pixelX_ = moveSquareRight(piece, piece.pixelX_, piece.pixelY_, xBuffer);
		
				piece.changeState(PieceRight.Instance());
				piece.getState().move(piece, currentSpace, newSpace);
				break;
			}
		}
	}
}
