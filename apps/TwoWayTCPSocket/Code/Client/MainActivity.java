package com.example.root.twowaytcpsocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextServerInetAddress;
    private EditText editTextServerPortNumber;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonSend;

    private String serverInetAddress;
    private int serverPortNumber;
    private String userName;
    private  String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        handleButtonClick();
    }

    private void initializeViews()
    {
        editTextServerInetAddress = (EditText) findViewById(R.id.editTextServerIP);
        editTextServerPortNumber = (EditText) findViewById(R.id.editTextPortNumber);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSend = (Button) findViewById(R.id.buttonSend);
    }

    private void handleButtonClick()
    {
        buttonSend.setOnClickListener( new View.OnClickListener()
        {
            private RunnableThread runnableThread;
            private Thread thread;

            @Override
            public void onClick(View v)
            {
                getUserInput();
                initializeThreads();

                thread.start();

                try
                {
                    thread.join();
                }

                catch(Exception e)
                {
                    e.printStackTrace();
                }

                Toast.makeText( MainActivity.this, runnableThread.getVerdict(), Toast.LENGTH_SHORT ).show();
            }

            private void getUserInput()
            {
                serverInetAddress = editTextServerInetAddress.getText().toString();
                serverPortNumber = Integer.parseInt( editTextServerPortNumber.getText().toString() );
                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();
            }

            private void initializeThreads()
            {
                runnableThread = new RunnableThread( serverInetAddress, serverPortNumber, userName, password );
                thread = new Thread(runnableThread);
            }
        }
        );
    }
}
