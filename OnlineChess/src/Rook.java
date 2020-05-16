
/*
 * This class represents a Rook piece 
 */
public class Rook extends Piece {

	// constructor 
	public Rook(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Board board, BoardSquare start, BoardSquare end) {
		
		if (end.getPiece().isWhite() == this.isWhite()) {
			return false;
		}		
		
		// just return true for now and sort logic later
		return true;
	}
	
	// translate to either white (caps) or black (lowerCase) 
	@Override
	public String toString() {
		if (this.isKilled()) {
			return "-";
		}
		else if (this.isWhite()) {
			return "R";
		}
		else {
			return "r";
		}			
	}
	
} // Ends Class