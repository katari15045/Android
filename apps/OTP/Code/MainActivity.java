package com.example.root.otp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editTextMobileNumber;
    Button buttonSendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMobileNumber = (EditText) findViewById(R.id.EditTextMobileNumber);
        buttonSendOTP = (Button) findViewById(R.id.ButtonSendOTP);

        buttonSendOTP.setOnClickListener(new View.OnClickListener()
        {
            int randomMax, randomMin, otpInt;
            Random random;
            String otpString, userString;
            EditText editTextOTP;
            AlertDialog dialogUsername;

            @Override
            public  void onClick(View v)
            {
                sendOTP();

                AlertDialog.Builder builderUsername = new AlertDialog.Builder(MainActivity.this);

                builderUsername.setPositiveButton("Validate", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        editTextOTP = (EditText) dialogUsername.findViewById(R.id.EditTextOTP);
                        userString = editTextOTP.getText().toString();

                        if( otpString.equals(userString) )
                        {
                            Toast.makeText(MainActivity.this, "OTP Validated!!!", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(MainActivity.this, "Invalid OTP!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builderUsername.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.validate_otp,null);
                builderUsername.setView(myView);
                builderUsername.setCancelable(false);
                dialogUsername = builderUsername.create();
                dialogUsername.show();
            }

            private String convertIntToString(int inpInt)
            {
                Integer intObject = new Integer(inpInt);
                return intObject.toString();
            }

            private void sendOTP()
            {
                String mobileNumber = editTextMobileNumber.getText().toString();
                randomMax = 1000000;
                randomMin = 0;
                random = new Random();
                otpInt = random.nextInt( (randomMax+1) - randomMin ) + randomMin;
                otpString = convertIntToString(otpInt);
                final String acknowledgeSent = "OTP Sent!!!";
                final String acknowledgeDelivered = "OTP Delivered!!!";

                PendingIntent pendingIntentSent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(acknowledgeSent), 0);
                PendingIntent pendingIntentDelivered = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(acknowledgeDelivered), 0);

                registerReceiver(new BroadcastReceiver()
                {

                    public void onReceive(Context context, Intent intent)
                    {
                        if( getResultCode() == Activity.RESULT_OK )
                        {
                            Toast.makeText(MainActivity.this, acknowledgeSent, Toast.LENGTH_SHORT ).show();
                        }

                        else if( getResultCode() == SmsManager.RESULT_ERROR_GENERIC_FAILURE )
                        {
                            Toast.makeText(MainActivity.this, "Generic Failure.", Toast.LENGTH_SHORT ).show();
                        }

                        else if( getResultCode() == SmsManager.RESULT_ERROR_NO_SERVICE )
                        {
                            Toast.makeText(MainActivity.this, "No Service.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }, new IntentFilter(acknowledgeSent));

                registerReceiver(new BroadcastReceiver()
                {

                    public void onReceive(Context context, Intent intent)
                    {
                        if( getResultCode() == Activity.RESULT_OK )
                        {
                            Toast.makeText(MainActivity.this, acknowledgeDelivered, Toast.LENGTH_SHORT ).show();
                        }

                        else if( getResultCode() == Activity.RESULT_CANCELED )
                        {
                            Toast.makeText(MainActivity.this, "OTP not delivered.", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }, new IntentFilter(acknowledgeDelivered));

                sendMessage( otpString, mobileNumber, pendingIntentSent, pendingIntentDelivered );
            }

            private void sendMessage(String message, String mobileNumber, PendingIntent pendingIntentSent, PendingIntent pendingIntentDelivered)
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobileNumber, null, message, pendingIntentSent, pendingIntentDelivered);
            }
        }
        );
    }
}
