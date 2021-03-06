import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server  
{ 
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
  
                // create a thread for this new client to remain connected to the server 
                Thread threadConnection = new ClientHandler(s, inputStream, outputStream); 
  
                // start the thread for this specific client (executes the run method)
                threadConnection.start(); 
                  
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
class ClientHandler extends Thread  
{ 
	// client handler object properties 
    final DataInputStream inputStream; 
    final DataOutputStream outputStream; 
    final Socket s;       
  
    // Constructor used by server for communication channels 
    public ClientHandler(Socket s, DataInputStream inputStream, DataOutputStream outputStream)  
    { 
        this.s = s; 
        this.inputStream = inputStream; 
        this.outputStream = outputStream; 
    } 
  
    @Override
    public void run()  
    { 
    	// used for the clients command line input 
        String clientInput; 
        
        while (true)  
        { 
            try { 
  
                // Ask user what he wants 
            	outputStream.writeUTF("Thank you for choosing chess. Type Yes to play a game. \n"+ "Type Exit to disconnect."); 
                  
                // receive the answer from client 
            	clientInput = inputStream.readUTF(); 
                  
                if(clientInput.equals("Exit")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }                  
                  
                // we need to figure out how to handle queuing waiting players and player selection
                switch (clientInput) { 
                  
                    case "Yes" : 
                        outputStream.writeUTF("Okay lets get started "); 
                        break; 
                          
                    case "No" : 
                        outputStream.writeUTF("Okay we can wait for another player. "); 
                        break; 
                          
                    default: 
                    	outputStream.writeUTF("Invalid input"); 
                        break; 
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
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 