package com.example.root.multipleactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button buttonFlyToSweden;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFlyToSweden = (Button) findViewById(R.id.buttonFlyToSweden);

        buttonFlyToSweden.setOnClickListener(new View.OnClickListener()
        {
            String messageFromIndia;

            @Override
            public void onClick(View v)
            {
                messageFromIndia = "Peace!";
                Intent intentToSweden = new Intent(MainActivity.this,SwedenActivity.class);
                intentToSweden.putExtra("LabelMessageFromIndia",messageFromIndia);
                startActivity(intentToSweden);
            }
        });

    }
}
