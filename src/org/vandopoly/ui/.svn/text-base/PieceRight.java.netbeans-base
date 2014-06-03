package org.vandopoly.ui;


/*
 * Represents a piece on the top side of the board moving in the Right direction
 * 
 * @author James Kasten
 */
public class PieceRight extends PieceState {
	
	private static PieceRight INSTANCE = null;
	
	protected PieceRight() {
		// Exists to disable instantiation
	}
	
	public static PieceRight Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PieceRight();
		}
		
		return INSTANCE;
	}
	
	public void move(final Piece piece, int currentSpace, int newSpace) {
		
		while(currentSpace != newSpace) {
			
			piece.pixelX_ = moveSquareRight(piece, piece.pixelX_, piece.pixelY_, 1);
			currentSpace++;
			
			// Move piece appropriately in Corner Space
			if (currentSpace >= 30) {
				
				piece.pixelX_ = moveSquareRight(piece, piece.pixelX_, piece.pixelY_, xBuffer);
				piece.pixelY_ = moveSquareDown(piece, piece.pixelX_, piece.pixelY_, upRightYBuffer);
				
				piece.changeState(PieceDown.Instance());
				piece.getState().move(piece, currentSpace, newSpace);
				break;
			}
		}
	}
}