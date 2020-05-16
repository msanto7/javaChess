
/*
 * This class represents a chess player 
 * 
 */
public class Player { 
	
	// properties 
	public String playerName;
    public boolean whiteSide;
    
    // constructor
    public Player(String name, boolean isWhiteSide) {
    	this.playerName = name;
    	this.whiteSide = isWhiteSide;
    }

	
	// getters
	public String getPlayerName() {
		return playerName;
	}
	
	
    public boolean isWhiteSide() 
    { 
        return this.whiteSide == true; 
    } 

} 
  
