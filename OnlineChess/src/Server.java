import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
/*
 * This class runs the initial server code and kicks off new threads to handle any clients 
 * 
 * COSC 636 
 */

public class Server {
	
	// server properties 
	static Vector<ClientHandler> activeClients = new Vector<>();
	static int numConnectedClients = 0;
	
    public static void main(String[] args) throws IOException  
    { 
        // server is listening on port 5056 
        ServerSocket serverSocket = new ServerSocket(5056); 
          
        // server remains open for connections
        while (true)  
        { 
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                s = serverSocket.accept(); 
                 
                // print to the server that a new client has been connected 
                System.out.println("A new client is connected : " + s); 
                  
                // need input and output streams for communication between clients and the handlers 
                DataInputStream inputStream = new DataInputStream(s.getInputStream()); 
                DataOutputStream outputStream = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
                
                // we want to instantiate a new client handler object for this new client 
                ClientHandler clientHandler = new ClientHandler(s, "client " + numConnectedClients, inputStream, outputStream, activeClients);
  
                // create a thread for this new client to remain connected to the server 
                Thread threadConnection = new Thread(clientHandler);
                
                // add this client to our list of active chess players 
                activeClients.add(clientHandler);
  
                // start the thread for this specific client (executes the run method)
                threadConnection.start(); 
                
                // we have stated a thread for a new client so update our count 
                numConnectedClients++;
                  
            } 
            catch (Exception e){
            	// close the socket and print error if necessary 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } // Ends Main
    
} // Ends Class



  
/* ClientHandler class ************************************ 
 * 
 * this class handles an active client on a thread created by the server
 * 
 */

class ClientHandler implements Runnable
{ 
	// client handler object properties 
	Scanner input = new Scanner(System.in);
	private String clientName;
    final DataInputStream inputStream; 
    final DataOutputStream outputStream; 
    final Socket s;
    boolean isActive;
    Vector<ClientHandler> activePlayers;
    Game currentGame;
  
    // Constructor used by server for communication channels 
    public ClientHandler(Socket s, String name, DataInputStream inputStream, DataOutputStream outputStream, Vector<ClientHandler> activePlayers)  
    { 
        this.s = s; 
        this.inputStream = inputStream; 
        this.outputStream = outputStream;
        this.clientName = name;
        this.isActive = true;
        this.activePlayers = activePlayers;
    } 
  
    @Override
    public void run()  
    { 
    	// used for the clients command line input 
        String clientInput; 
        
        while (true)  
        { 
            try { 
            	
            	// print to the user the list of clients that are available to play a chess game 
            	outputStream.writeUTF("Here is the list of clients you can play with: \n");            	
            	for (int i = 0; i < activePlayers.size(); i++) {
            		outputStream.writeUTF(activePlayers.get(i).clientName);
            	}
            	outputStream.writeUTF("To start a game type ('play#client [insert client number]') \n");   
            	
            	// receive the string 
                clientInput = inputStream.readUTF();                   
                System.out.println(clientInput);                                 
                
                // allow the player to log off and close their connection with the server when desired 
                if(clientInput.equals("logout")){
                	
                    this.isActive = false; 
                    this.s.close(); 
                    break;
                    
                } 
                  
                // break the string into message and recipient part 
                StringTokenizer st = new StringTokenizer(clientInput, "#"); 
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken();
                
                // Start a Game 
                // p1 will be the player that starts the game
                // p2 will just use there handler to communicate back and forth the moves I suppose 
                if (MsgToSend.contentEquals("play")) {
                	
                	
                	Player p1 = new Player("this", true);
                    Player p2 = new Player(recipient, false);
                    currentGame = new Game();
                    currentGame.initialize(p1, p2);
                    String boardString = currentGame.board.printBoard();
                    
                    // Send the board to both players 
                    System.out.println(boardString);                    
                    MsgToSend = boardString;                    
                    outputStream.writeUTF("");
                    
                    // search for the recipient in the connected devices list. 
                    // ar is the vector storing client of active users 
                    for (ClientHandler mc : Server.activeClients)  
                    { 
                        // if the recipient is found, write on its 
                        // output stream 
                        if (mc.clientName.equals(recipient) && mc.isActive == true)  
                        {
                        	mc.outputStream.writeUTF("");
                            mc.outputStream.writeUTF(MsgToSend); 
                            break; 
                        } 
                                               
                    }
                    
                    // keep a loop for the game...
                    while (true) {
                    	
                    	// receive the string 
                        clientInput = inputStream.readUTF();
                        
                        // break the string into message and recipient part 
                        st = new StringTokenizer(clientInput, "#"); 
                        MsgToSend = st.nextToken(); 
                        String moveCoordinates = st.nextToken();
                    	
                    	// continue to wait for input and if the input is move...then update the board
                    	if (MsgToSend.contentEquals("move")) {
                        	
                        	// in this case we want to take in the integer values for the start and end positions
                        	String[] positions = moveCoordinates.split(" ");
                        	int[] IntPositions = new int[positions.length]; 
                        	
                        	// convert string values to integer 
                        	for (int i = 0; i < positions.length; i++) {                        		              
                        		IntPositions[i] = Integer.parseInt(positions[i]);
                        	}
                        	
                        	// take this players move and apply it to the game board 
                        	currentGame.playerMove(p1, IntPositions[0], IntPositions[1], IntPositions[2], IntPositions[3]);
                        	
                        	// print the game board to both the player that moved and the opponent 
                        	boardString = currentGame.board.printBoard();                        	
                        	System.out.println(boardString);
                        	
                        	
                        	// search for the recipient in the connected devices list. 
                            // ar is the vector storing client of active users 
                            for (ClientHandler mc : Server.activeClients)  
                            { 
                                // if the recipient is found, write on its 
                                // output stream 
                                if (mc.clientName.equals(recipient) && mc.isActive == true)  
                                {
                                	mc.outputStream.writeUTF("");
                                    mc.outputStream.writeUTF(boardString); 
                                    break; 
                                } 
                                
                                // reprint the game board to the player that just moved 
                                if (mc.clientName.equals(this.clientName)) {
                                	mc.outputStream.writeUTF("");
                                    mc.outputStream.writeUTF(boardString); 
                                    break; 
                                }
                            }
                        	
                        }                    	
                    } // End Game loop                                        
                }
                
				// in this case a game has already been initialized by the opponent...and their
				// Client Handler
				// will create and handle all of the game info
				// so in the case we want to listen for moves from our client
				if (MsgToSend.contentEquals("move")) {

					// in this case we want to take in the integer values for the start and end
					// positions
					String[] positions = recipient.split(" ");
					int[] IntPositions = new int[positions.length];

					// convert string values to integer
					for (int i = 0; i < positions.length; i++) {
						IntPositions[i] = Integer.parseInt(positions[i]);
					}

					// search for the recipient in the connected devices list.
					// ar is the vector storing client of active users
					for (ClientHandler mc : Server.activeClients) {
						// if we haven't initialized the game on this client handler then we will use
						// the opponents
						if (mc.clientName.equals("client 1") && mc.isActive == true) {
							String boardString;
							mc.currentGame.playerMove(mc.currentGame.players[1], IntPositions[0], IntPositions[1],
									IntPositions[2], IntPositions[3]);
							boardString = mc.currentGame.board.printBoard();
							System.out.println(boardString);
							mc.outputStream.writeUTF("");
							mc.outputStream.writeUTF(boardString);
							//break;
						}
					}

					while (true) {
						
						// receive the string 
                        clientInput = inputStream.readUTF();
                        
                        // break the string into message and recipient part 
                        st = new StringTokenizer(clientInput, "#"); 
                        MsgToSend = st.nextToken(); 
                        String moveCoordinates = st.nextToken();
						
						positions = moveCoordinates.split(" ");
						IntPositions = new int[positions.length];

						// convert string values to integer
						for (int i = 0; i < positions.length; i++) {
							IntPositions[i] = Integer.parseInt(positions[i]);
						}

						// search for the recipient in the connected devices list.
						// ar is the vector storing client of active users
						for (ClientHandler mc : Server.activeClients) {
							// if we haven't initialized the game on this client handler then we will use
							// the opponents
							if (mc.clientName.equals("client 1") && mc.isActive == true) {
								String boardString;
								mc.currentGame.playerMove(mc.currentGame.players[1], IntPositions[0], IntPositions[1],
										IntPositions[2], IntPositions[3]);
								boardString = mc.currentGame.board.printBoard();
								System.out.println(boardString);
								mc.outputStream.writeUTF("");
								mc.outputStream.writeUTF(boardString);
								break;
							}
						}

					}

				}                  
                
                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        } 
          
        try
        { 
            // closing resources 
            this.inputStream.close(); 
            this.outputStream.close(); 
              
        } catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 






























