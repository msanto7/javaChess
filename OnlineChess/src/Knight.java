
/*
 * This class represents the Knight piece
 */
public class Knight extends Piece {

	// constructor 
	public Knight(boolean white) {
		super(white);
	}
	
	@Override
	public boolean canMove(Board board, BoardSquare start, BoardSquare end) {
		
		if (end.getPiece().isWhite() == this.isWhite()) {
			return false;
		}		
		
		/*
		 * int file = Math.abs(start.getFile() - end.getFile()); int rank =
		 * Math.abs(start.getRank() - end.getRank()); return file * rank == 2;
		 */
		
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
			return "N";
		}
		else {
			return "n";
		}			
	}
	
	
} // Ends Class 
