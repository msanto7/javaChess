import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class Server implements Runnable {
	
	private int port;
	private boolean running = false;
	private Selector selector;
	private ServerSocket serverSocket;
	private ByteBuffer buffer;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}