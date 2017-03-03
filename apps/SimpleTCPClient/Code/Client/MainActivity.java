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

        initializeViews();

        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            String serverIPAddress;
            int portNumber;
            String message;

            Thread thread;
            RunnableThread runnableThread;

            @Override
            public void onClick(View v)
            {
                getInputFromUser();
                initializeThread();

                thread.start();
                Toast.makeText(MainActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
            }

            private void getInputFromUser()
            {
                serverIPAddress = editTextIPAddress.getText().toString();
                portNumber = Integer.parseInt( editTextPortNumber.getText().toString() );
                message = editTextMessage.getText().toString();
            }

            private void initializeThread()
            {
                runnableThread = new RunnableThread(serverIPAddress, portNumber, message);
                thread = new Thread(runnableThread);
            }
        });
    }

    private void initializeViews()
    {
        editTextIPAddress = (EditText) findViewById(R.id.editTextServerIP);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        buttonSend = (Button) findViewById(R.id.buttonSend);

    }
}
