package com.example.root.alertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this, "You are positive!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                //builder.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog,null);
                builder.setView(myView);
                builder.setCancelable(false);
                AlertDialog dialogUsername = builder.create();
                dialogUsername.show();
            }
        }
        );

    }
}
