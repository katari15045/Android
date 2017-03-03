import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class RunnableThread implements Runnable
{
	private ServerSocket serverSocket;
	private Socket socket;
	private int portNumber;

	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	private String clientMessage;


	public RunnableThread(int inpPortNumber)
	{
		portNumber = inpPortNumber;
	}

	@Override
	public void run()
	{
		
		while(true)
		{
			try
			{
				serverSocket = new ServerSocket(portNumber);
				System.out.printf("\nWaiting for client on port " + serverSocket.getLocalPort() + "...\n\n");
				socket = serverSocket.accept();
				System.out.println("Client -> " + socket.getInetAddress() + ":" + socket.getPort() +  " Connected");
				receiveDataFromClient();
				closeConnection();
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void receiveDataFromClient() throws IOException
	{
		dataInputStream = new DataInputStream( socket.getInputStream() );

		clientMessage = dataInputStream.readUTF();
		System.out.println("Client Message -> " + clientMessage);
	}


	private void closeConnection() throws IOException
	{
		dataInputStream.close();
		socket.close();
		serverSocket.close();
	}
}