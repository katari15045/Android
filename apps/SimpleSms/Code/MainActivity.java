package com.example.root.simplesms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText editTextMobileNumber;
    EditText editTextMessage;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMobileNumber = (EditText) findViewById(R.id.EditTextMobileNumber);
        editTextMessage = (EditText) findViewById(R.id.EditTextMessage);
        buttonSend = (Button) findViewById(R.id.ButtonSend);

        buttonSend.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String mobileNumber = editTextMobileNumber.getText().toString();
                String message = editTextMessage.getText().toString();
                sendMessage( message, mobileNumber );
            }

            private void sendMessage(String message, String mobileNumber)
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobileNumber, null, message, null, null);
            }
        });
    }
}