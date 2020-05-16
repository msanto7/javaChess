import java.util.List;

/*
 * This class represents a chess game, and is instantiated when a new game is being started between 2 clients 
 */

public class Game { 
	
	// properties 
    private Player[] players = new Player[2]; 
    public Board board = new Board(); 
    private Player currentTurn; 
    private GameStatus status; 
    private List<Move> movesPlayed; 
    
    // start a game for 2 players   
    public void initialize(Player p1, Player p2) 
    { 
        players[0] = p1; 
        players[1] = p2; 
  
        board.resetBoard(); 
  
        if (p1.isWhiteSide()) { 
            this.currentTurn = p1; 
        } 
        else { 
            this.currentTurn = p2; 
        } 
  
        //movesPlayed.clear(); 
        
    } 
  
    public boolean isEnd() 
    { 
        return this.getStatus() != GameStatus.ACTIVE; 
    } 
  
    public GameStatus getStatus() 
    { 
        return this.status; 
    } 
  
    public void setStatus(GameStatus status) 
    { 
        this.status = status; 
    } 
  
    public boolean playerMove(Player player, int startX,  
                                int startY, int endX, int endY) throws Exception 
    { 
        BoardSquare startBox = board.getBox(startX, startY); 
        BoardSquare endBox = board.getBox(startY, endY); 
        Move move = new Move(player, startBox, endBox); 
        return this.makeMove(move, player); 
    } 
  
    private boolean makeMove(Move move, Player player) 
    { 
        Piece sourcePiece = move.getStart().getPiece(); 
        if (sourcePiece == null) { 
            return false; 
        } 
  
        // valid player 
        if (player != currentTurn) { 
            return false; 
        } 
  
        if (sourcePiece.isWhite() != player.isWhiteSide()) { 
            return false; 
        } 
  
        // valid move? 
        if (!sourcePiece.canMove(board, move.getStart(),  
                                            move.getEnd())) { 
            return false; 
        } 
  
        // kill? 
        Piece destPiece = move.getStart().getPiece(); 
        if (destPiece != null) { 
            destPiece.setKilled(true); 
            //move.setPieceKilled(destPiece); 
        } 

  
        // store the move 
        movesPlayed.add(move); 
  
        // move piece from the stat box to end box 
        move.getEnd().setPiece(move.getStart().getPiece()); 
        move.getStart().setPiece(null); 
  
        if (destPiece != null && destPiece instanceof King) { 
            if (player.isWhiteSide()) { 
                this.setStatus(GameStatus.WHITE_WIN); 
            } 
            else { 
                this.setStatus(GameStatus.BLACK_WIN); 
            } 
        } 
  
        // set the current turn to the other player 
        if (this.currentTurn == players[0]) { 
            this.currentTurn = players[1]; 
        } 
        else { 
            this.currentTurn = players[0]; 
        } 
  
        return true; 
    } 
} 








