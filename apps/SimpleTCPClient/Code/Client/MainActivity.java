package com.example.root.simpletcpclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity
{
    EditText editTextIPAddress;
    EditText editTextPortNumber;
    EditText editTextMessage;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIPAddress = (EditText) findViewById(R.id.editTextServerIP);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        buttonSend = (Button) findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            String serverIPAddress;
            int portNumber;

            @Override
            public void onClick(View v)
            {
                Thread thread = new Thread()
                {
                    public  void  run()
                    {
                        serverIPAddress = editTextIPAddress.getText().toString();
                        portNumber = Integer.parseInt( editTextPortNumber.getText().toString() );

                        try
                        {
                            Socket socket = new Socket(serverIPAddress, portNumber);
                            DataOutputStream dataOutputStream = new DataOutputStream( socket.getOutputStream() );
                            dataOutputStream.writeUTF( editTextMessage.getText().toString() );
                            dataOutputStream.flush();
                            dataOutputStream.close();
                            socket.close();
                        }

                        catch(UnknownHostException e)
                        {
                            e.printStackTrace();
                        }

                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                Toast.makeText(MainActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
