

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
        //try
        //{ 
            Scanner input = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 5056); 
      
            // obtaining input and out streams 
            DataInputStream inputStream = new DataInputStream(s.getInputStream()); 
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream()); 
      
//            // need to interact with the handler to decide what the client wants to do when they connect 
//            while (true)  
//            { 
//                System.out.println(inputStream.readUTF()); 
//                String toSend = input.nextLine(); 
//                outputStream.writeUTF(toSend); 
//                  
//                // If client sends exit,close this connection  
//                if(toSend.equals("Exit")) 
//                { 
//                    System.out.println("Closing this connection : " + s); 
//                    s.close(); 
//                    System.out.println("Connection closed"); 
//                    break; 
//                } 
//                  
//                // printing server response  
//                String received = inputStream.readUTF(); 
//                System.out.println(received); 
//            } 
//              
//            // closing resources 
//            input.close(); 
//            inputStream.close(); 
//            outputStream.close(); 
//        }
//        catch(Exception e){ 
//            e.printStackTrace(); 
//        }
            
            // sendMessage thread 
            Thread sendMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 
                    while (true) { 
      
                        // read the message to deliver. 
                        String msg = input.nextLine(); 
                          
                        try { 
                            // write on the output stream 
                            outputStream.writeUTF(msg); 
                        } catch (IOException e) { 
                            e.printStackTrace(); 
                        } 
                    } 
                } 
            }); 
              
            // readMessage thread 
            Thread readMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 
      
                    while (true) { 
                        try { 
                            // read the message sent to this client 
                            String msg = inputStream.readUTF(); 
                            System.out.println(msg); 
                        } catch (IOException e) { 
      
                            e.printStackTrace(); 
                        } 
                    } 
                } 
            }); 
      
            sendMessage.start(); 
            readMessage.start();
            
    } // Ends main 
    
} // Ends Class 