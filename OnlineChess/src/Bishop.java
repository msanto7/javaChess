
/*
 * This class represents a Bishop
 */
public class Bishop extends Piece {

	// constructor 
	public Bishop(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Board board, BoardSquare start, BoardSquare end) {
		
		if (end.getPiece().isWhite() == this.isWhite()) {
			return false;
		}		
		
		return true;
	}
	
	// translate to either white (caps) or black (lowerCase) 
	@Override
	public String toString() {
		if (this.isKilled()) {
			return "-";
		}
		else if (this.isWhite()) {
			return "B";
		}
		else {
			return "b";
		}			
	}
	
	
} // Ends Class 
