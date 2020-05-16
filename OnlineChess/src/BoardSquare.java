
/*
 * This class represents a single square or location on the game board 
 */
public class BoardSquare {

	// square properties 
	public Piece piece;
	public int file;
	public int rank;

	// constructor
	public BoardSquare(int file, int rank, Piece piece) { 
		this.setPiece(piece); 
	    this.setFile(file); 
	    this.setRank(rank); 
	}
	
	// getters 

	public Piece getPiece() {
		return this.piece;
	}
	
	public int getFile() {
		return this.file;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	// setters - used when a move is made

	public void setPiece(Piece p) {
		this.piece = p;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

} // Ends Class






