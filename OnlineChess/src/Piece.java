
/* 
 * This class represents a chess piece 
 */
public class Piece {
	
    private boolean killed = false; 
    private boolean white = false; 
  
    public Piece(boolean white) 
    { 
        this.setWhite(white); 
    } 
  
    public boolean isWhite() 
    { 
        return this.white == true; 
    } 
  
    public void setWhite(boolean white) 
    { 
        this.white = white; 
    } 
  
    public boolean isKilled() 
    { 
        return this.killed == true; 
    } 
  
    public void setKilled(boolean killed) 
    { 
        this.killed = killed; 
    } 
  
    public boolean canMove(Board board, BoardSquare start, BoardSquare end) {
    	return true;
    }
	
	
    // to string 
	//public abstract String toString();
	
	public String toString() {
		return this.getClass().toString();
	}

} // Ends Class 
