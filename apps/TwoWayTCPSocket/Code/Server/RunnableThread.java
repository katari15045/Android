import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.HashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class RunnableThread implements Runnable
{
	private ServerSocket serverSocket;
	private Socket socket;
	private int portNumber;

	private HashMap<String, String> hashMap;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	private String username;
	private String password;
	private String output;


	public RunnableThread(HashMap<String,String> inpHashMap, int inpPortNumber)
	{
		hashMap = inpHashMap;
		portNumber = inpPortNumber;
	}

	@Override
	public void run()
	{
		
		while(true)
		{
			try
			{
				connectToClient();
				receiveDataFromClient();
				verifyCredentials();
				sendDataToClient();
				closeConnection();
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void connectToClient() throws IOException
	{
		serverSocket = new ServerSocket(portNumber);
		System.out.printf("\nWaiting for client on port " + serverSocket.getLocalPort() + "...\n\n");
		socket = serverSocket.accept();
		System.out.println("Client -> " + socket.getInetAddress() + ":" + socket.getPort() +  " Connected");
	}

	private void receiveDataFromClient() throws IOException
	{
		dataInputStream = new DataInputStream( socket.getInputStream() );

		username = dataInputStream.readUTF();
		password = dataInputStream.readUTF();
		System.out.println( "Username -> " + username );
		System.out.println( "Password -> " + password );
	}

	private void verifyCredentials()
	{
		if( hashMap.containsKey(username) )
		{
			if( hashMap.get(username).equals(password) )
			{
				output = "Credentials verified!";
			}

			else
			{
				output = "Invalid Credentials!";
			}
		}

		else
		{
			output = "No such username!";
		}

		System.out.println(output);
	}

	private void sendDataToClient() throws IOException
	{
		dataOutputStream = new DataOutputStream( socket.getOutputStream() );
		dataOutputStream.writeUTF(output);
	}

	private void closeConnection() throws IOException
	{
		dataInputStream.close();
		socket.close();
		serverSocket.close();

		System.out.println("Client Disconnected!");
	}
}