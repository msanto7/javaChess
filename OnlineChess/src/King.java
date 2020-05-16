
/*
 * This class represents the King piece on the board 
 */
public class King extends Piece {

	public King(boolean white) {
		super(white);
	}
	
	@Override
    public boolean canMove(Board board, BoardSquare start, BoardSquare end) 
    { 
        // can't move the king to a square already occupied 
        if (end.getPiece().isWhite() == this.isWhite()) { 
            return false; 
        } 
  
        int x = Math.abs(start.getFile() - end.getFile()); 
        int y = Math.abs(start.getRank() - end.getRank()); 
        if (x + y == 1) { 
            // check if this move will not result in the king 
            // being attacked if so return true 
            return true; 
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
			return "K";
		}
		else {
			return "k";
		}			
	}
		

}
