import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
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
                 
                // maybe here I can check if the server has other connections or threads running...if it does not...then we want to print 
                // System.out.println("You have been connected to play chess. Please wait until we find another player to match you with...");
                // if it does have other connections we need to ask each player if they want to play and connect them together somehow 
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream inputStream = new DataInputStream(s.getInputStream()); 
                DataOutputStream outputStream = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
                
                // we want to instantiate a new client handler object for this new client 
                ClientHandler clientHandler = new ClientHandler(s, "client " + numConnectedClients, inputStream, outputStream);
  
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



  
// ClientHandler class ************************************ 
class ClientHandler implements Runnable
{ 
	// client handler object properties 
	Scanner input = new Scanner(System.in);
	private String clientName;
    final DataInputStream inputStream; 
    final DataOutputStream outputStream; 
    final Socket s;
    boolean isActive;
  
    // Constructor used by server for communication channels 
    public ClientHandler(Socket s, String name, DataInputStream inputStream, DataOutputStream outputStream)  
    { 
        this.s = s; 
        this.inputStream = inputStream; 
        this.outputStream = outputStream;
        this.clientName = name;
        this.isActive = true;
    } 
  
    @Override
    public void run()  
    { 
    	// used for the clients command line input 
        String clientInput; 
        
        while (true)  
        { 
            try { 
  
				/*
				 * // Ask user what he wants outputStream.
				 * writeUTF("Thank you for choosing chess. Type Yes to play a game. \n"+
				 * "Type Exit to disconnect.");
				 * 
				 * // receive the answer from client clientInput = inputStream.readUTF();
				 * 
				 * if(clientInput.equals("Exit")) { System.out.println("Client " + this.s +
				 * " sends exit..."); System.out.println("Closing this connection.");
				 * this.s.close(); System.out.println("Connection closed"); break; }
				 * 
				 * // we need to figure out how to handle queuing waiting players and player
				 * selection switch (clientInput) {
				 * 
				 * case "Yes" : outputStream.writeUTF("Okay lets get started "); break;
				 * 
				 * case "No" : outputStream.writeUTF("Okay we can wait for another player. ");
				 * break;
				 * 
				 * default: outputStream.writeUTF("Invalid input"); break; }
				 */ 
            	
            	// receive the string 
                clientInput = inputStream.readUTF(); 
                  
                System.out.println(clientInput); 
                  
                if(clientInput.equals("logout")){ 
                    this.isActive = false; 
                    this.s.close(); 
                    break; 
                } 
                  
                // break the string into message and recipient part 
                StringTokenizer st = new StringTokenizer(clientInput, "#"); 
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken(); 
  
                // search for the recipient in the connected devices list. 
                // ar is the vector storing client of active users 
                for (ClientHandler mc : Server.activeClients)  
                { 
                    // if the recipient is found, write on its 
                    // output stream 
                    if (mc.clientName.equals(recipient) && mc.isActive == true)  
                    { 
                        mc.outputStream.writeUTF(this.clientName+" : "+MsgToSend); 
                        break; 
                    } 
                } 
            } catch (IOException e) { 
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