
/*
 * This class represents a valid move of a piece on our chess board 
 */
public class Move {
	
	 private Player player; 
	    private BoardSquare start; 
	    private BoardSquare end; 
	    private Piece pieceMoved; 
	    private Piece pieceKilled; 
	    private boolean castlingMove = false; 
	  
	    public Move(Player player, BoardSquare start, BoardSquare end) 
	    { 
	        this.player = player; 
	        this.start = start; 
	        this.end = end; 
	        this.pieceMoved = start.getPiece(); 
	    } 
	  
	    // getters
	    public BoardSquare getStart() {
	    	return this.start;
	    }
	    
	    public BoardSquare getEnd() {
	    	return this.end;
	    }
	   

} // Ends Class
