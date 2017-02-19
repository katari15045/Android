package com.example.saketh.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
{
    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent myIntent = getIntent();

        String name = myIntent.getStringExtra("LabelName");
        String salute = myIntent.getStringExtra("LabelSalute");
        String username = myIntent.getStringExtra("LabelUsername");
        String email = myIntent.getStringExtra("LabelEmail");


        Toast.makeText(Main2Activity.this,
                salute + name + ", " + "your account with UID " + username
                        + " has been created successfully.For updates check your mail " + email
                , Toast.LENGTH_LONG).show();
    }
}
