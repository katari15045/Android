package com.example.root.multipleactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by root on 28/2/17.
 */

public class SwedenActivity extends AppCompatActivity
{
    Button buttonBackToIndia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sweden);

        buttonBackToIndia = (Button) findViewById(R.id.buttonBackToIndia);

        Intent intentFromIndia = getIntent();
        String messageFromIndia = intentFromIndia.getStringExtra("LabelMessageFromIndia");
        Toast.makeText( SwedenActivity.this, messageFromIndia, Toast.LENGTH_SHORT ).show();

        buttonBackToIndia.setOnClickListener(new View.OnClickListener()
        {
            String messageFromSweden;

            @Override
            public void onClick(View v)
            {
                messageFromSweden = "Had a nice time in Sweden!";
                Intent intentToIndia = new Intent(SwedenActivity.this, MainActivity.class);
                intentToIndia.putExtra("LabelMessageFromSweden", messageFromSweden);
                startActivity(intentToIndia);
            }
        });

    }
}
