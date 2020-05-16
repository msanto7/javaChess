
/*
 * This class represents an empty space on the board 
 */
public class EmptySquare extends Piece {

	public EmptySquare(boolean white) {
		super(white);
	}
	
	@Override
    public boolean canMove(Board board, BoardSquare start, BoardSquare end) 
    { 
        return false;
    }
	
	// translate to either white (caps) or black (lowerCase) 
	@Override
	public String toString() {
		return "-";
	}
		

}