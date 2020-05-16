
/*
 * This class represents the actual chess board 
 */

public class Board { 
	BoardSquare[][] squares = new BoardSquare[8][8]; 
  
    public Board() { 
        this.resetBoard(); 
    } 
  
    public BoardSquare getBox(int x, int y) throws Exception { 
  
        if (x < 0 || x > 7 || y < 0 || y > 7) { 
            throw new Exception("Index out of bound"); 
        } 
  
        return squares[x][y]; 
    }
  
    // initialize the starting board 
    public void resetBoard() { 
        // initialize white pieces 
    	squares[0][0] = new BoardSquare(0, 0, new Rook(true)); 
    	squares[0][1] = new BoardSquare(0, 1, new Knight(true)); 
    	squares[0][2] = new BoardSquare(0, 2, new Bishop(true)); 
        //... 
    	squares[1][0] = new BoardSquare(1, 0, new Pawn(true)); 
    	squares[1][1] = new BoardSquare(1, 1, new Pawn(true)); 
        //... 
  
        // initialize black pieces 
    	squares[7][0] = new BoardSquare(7, 0, new Rook(false)); 
    	squares[7][1] = new BoardSquare(7, 1, new Knight(false)); 
    	squares[7][2] = new BoardSquare(7, 2, new Bishop(false)); 
        //... 
    	squares[6][0] = new BoardSquare(6, 0, new Pawn(false)); 
    	squares[6][1] = new BoardSquare(6, 1, new Pawn(false)); 
        //... 
  
        // initialize remaining boxes without any piece 
        for (int i = 2; i < 6; i++) { 
            for (int j = 0; j < 8; j++) { 
            	squares[i][j] = new BoardSquare(i, j, new EmptySquare(false)); 
            } 
        } 
    }
    
    // method to print the command line version of the board 
    public String printBoard() {
    	
    	// string used to store the board
    	String board = "";
    	
    	// iterate through the board and concatenate all of the pieces  
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			board = board + this.squares[i][j].piece.getClass().toString();
    		}
    		board = board + "\n";
    	}
    	
    	// return the game board string 
    	return board;
    }
    
} // End Class 