import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;

public class TCPServer
{
	private static Thread threadOne;
	private static int portNumber;

	public static void main(String[] args)
	{
		portNumber = 7000;
		threadOne = new Thread()
		{
			public void run()
			{
				System.out.println("Server Started!");

				try
				{
					ServerSocket serverSocket = new ServerSocket(portNumber);

					while(true)
					{
						Socket socket = serverSocket.accept();
						DataInputStream dataInputStream = new DataInputStream( socket.getInputStream() );
						System.out.println("Data from client -> " + dataInputStream.readUTF() );
						dataInputStream.close();
						socket.close();
					}
				}

				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		};

		threadOne.start();
	}

}