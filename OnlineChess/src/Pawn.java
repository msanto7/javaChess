
/*
 * This class represents a pawn 
 */
public class Pawn extends Piece {

	// constructor 
	public Pawn(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Board board, BoardSquare start, BoardSquare end) {	
		
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
			return "P";
		}
		else {
			return "p";
		}			
	}
	
} // Ends Class
