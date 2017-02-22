package com.example.root.simplesms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                final String acknowledeSent = "Message Sent!!!";
                final String acknowledgeDelivered = "Message Delivered!!!";

                PendingIntent pendingIntentSent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(acknowledeSent), 0);
                PendingIntent pendingIntentDelivered = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(acknowledgeDelivered), 0);

                registerReceiver(new BroadcastReceiver()
                {

                    public void onReceive(Context context, Intent intent)
                    {
                        if( getResultCode() == Activity.RESULT_OK )
                        {
                            Toast.makeText(MainActivity.this, acknowledeSent, Toast.LENGTH_LONG ).show();
                        }

                        else if( getResultCode() == SmsManager.RESULT_ERROR_GENERIC_FAILURE )
                        {
                            Toast.makeText(MainActivity.this, "Generic Failure.", Toast.LENGTH_LONG ).show();
                        }

                        else if( getResultCode() == SmsManager.RESULT_ERROR_NO_SERVICE )
                        {
                            Toast.makeText(MainActivity.this, "No Service.", Toast.LENGTH_LONG ).show();
                        }
                    }
                }, new IntentFilter(acknowledeSent));

                registerReceiver(new BroadcastReceiver()
                {

                    public void onReceive(Context context, Intent intent)
                    {
                        if( getResultCode() == Activity.RESULT_OK )
                        {
                            Toast.makeText(MainActivity.this, acknowledgeDelivered, Toast.LENGTH_LONG ).show();
                        }

                        else if( getResultCode() == Activity.RESULT_CANCELED )
                        {
                            Toast.makeText(MainActivity.this, "Message not delivered.", Toast.LENGTH_LONG ).show();
                        }
                    }
                }, new IntentFilter(acknowledgeDelivered));

                sendMessage( message, mobileNumber, pendingIntentSent, pendingIntentDelivered );
            }

            private void sendMessage(String message, String mobileNumber, PendingIntent pendingIntentSent, PendingIntent pendingIntentDelivered)
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobileNumber, null, message, pendingIntentSent, pendingIntentDelivered);
            }
        });
    }
}
