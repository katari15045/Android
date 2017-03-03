package com.example.root.simpletcpclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by root on 2/3/17.
 */

public class RunnableThread implements Runnable
{
    private Socket socket;
    private int serverPort;
    private String serverInetAddress;

    private DataOutputStream dataOutputStream;
    private String message;

    public RunnableThread(String inpInetAddress, int portNumber, String inpMessage)
    {
        serverInetAddress = inpInetAddress;
        serverPort = portNumber;
        message = inpMessage;
    }

    @Override
    public void run()
    {
        try
        {
            connectToServer();
            sendData();
            closeConnection();
        }
        catch(Exception e)
        {
            System.out.println( e.getStackTrace() );
        }
    }

    private void connectToServer() throws IOException
    {
        System.out.println("Connecting to server...");
        socket = new Socket(serverInetAddress, serverPort);
        System.out.println("Connected to server -> " + socket.getInetAddress() + ":" + socket.getPort() + " on port " + socket.getLocalPort() );

    }

    private void sendData() throws IOException
    {
        dataOutputStream = new DataOutputStream( socket.getOutputStream() );
        dataOutputStream.writeUTF(message);
    }

    private void closeConnection() throws  IOException
    {
        dataOutputStream.close();
        socket.close();
    }
}
