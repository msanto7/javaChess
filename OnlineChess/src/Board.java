
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
  
    // populate the squares for the initial board setup 
    public void resetBoard() { 
        // init white pieces row 1
    	squares[0][0] = new BoardSquare(0, 0, new Rook(true)); 
    	squares[0][1] = new BoardSquare(0, 1, new Knight(true)); 
    	squares[0][2] = new BoardSquare(0, 2, new Bishop(true)); 
    	squares[0][3] = new BoardSquare(0, 3, new Queen(true));
    	squares[0][4] = new BoardSquare(0, 4, new King(true));
    	squares[0][5] = new BoardSquare(0, 5, new Bishop(true));
    	squares[0][6] = new BoardSquare(0, 6, new Knight(true));
    	squares[0][7] = new BoardSquare(0, 7, new Rook(true));
        
    	// init white pieces row 2
    	squares[1][0] = new BoardSquare(1, 0, new Pawn(true)); 
    	squares[1][1] = new BoardSquare(1, 1, new Pawn(true));
    	squares[1][2] = new BoardSquare(1, 2, new Pawn(true)); 
    	squares[1][3] = new BoardSquare(1, 3, new Pawn(true));
    	squares[1][4] = new BoardSquare(1, 4, new Pawn(true)); 
    	squares[1][5] = new BoardSquare(1, 5, new Pawn(true));
    	squares[1][6] = new BoardSquare(1, 6, new Pawn(true)); 
    	squares[1][7] = new BoardSquare(1, 7, new Pawn(true));
  
        // init black pieces row 1 
    	squares[7][0] = new BoardSquare(7, 0, new Rook(false)); 
    	squares[7][1] = new BoardSquare(7, 1, new Knight(false)); 
    	squares[7][2] = new BoardSquare(7, 2, new Bishop(false));
    	squares[7][3] = new BoardSquare(7, 3, new Queen(false));
    	squares[7][4] = new BoardSquare(7, 4, new King(false));
    	squares[7][5] = new BoardSquare(7, 5, new Bishop(false));
    	squares[7][6] = new BoardSquare(7, 6, new Knight(false)); 
    	squares[7][7] = new BoardSquare(7, 7, new Rook(false)); 
        
    	// init black pieces row 2
    	squares[6][0] = new BoardSquare(6, 0, new Pawn(false)); 
    	squares[6][1] = new BoardSquare(6, 1, new Pawn(false));
    	squares[6][2] = new BoardSquare(6, 2, new Pawn(false));
    	squares[6][3] = new BoardSquare(6, 3, new Pawn(false));
    	squares[6][4] = new BoardSquare(6, 4, new Pawn(false));
    	squares[6][5] = new BoardSquare(6, 5, new Pawn(false));
    	squares[6][6] = new BoardSquare(6, 6, new Pawn(false));
    	squares[6][7] = new BoardSquare(6, 7, new Pawn(false));
        
  
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
    	String boardString = "";
    	
    	// iterate through the board and concatenate all of the pieces  
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			boardString = boardString + " " + this.squares[i][j].piece.toString();
    		}
    		boardString = boardString + "\n";
    	}
    	
    	// return the game board string 
    	return boardString;
    }
    
} // End Class 