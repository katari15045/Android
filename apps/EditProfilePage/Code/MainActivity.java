package com.example.saketh.editprofile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private Button buttonEditUsername = null;
    private Button buttonEditPassword = null;
    private Button buttonEditEmail = null;
    private Button buttonEditPhoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEditUsername = (Button) findViewById(R.id.buttonEditUserName);
        buttonEditPassword = (Button) findViewById(R.id.buttonEditPassword);
        buttonEditEmail = (Button) findViewById(R.id.buttonEditEmail);
        buttonEditPhoneNumber = (Button) findViewById(R.id.buttonEditPhoneNumber);

        buttonEditUsername.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builderUsername = new AlertDialog.Builder(MainActivity.this);

                builderUsername.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Username Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                builderUsername.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_username,null);
                builderUsername.setView(myView);
                builderUsername.setCancelable(false);
                AlertDialog dialogUsername = builderUsername.create();
                dialogUsername.show();
            }
        });

        buttonEditPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_password,null);
                builder.setView(myView);
                builder.setCancelable(false);
                AlertDialog dialogUsername = builder.create();
                dialogUsername.show();
            }
        });

        buttonEditEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "E-Mail Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_email,null);
                builder.setView(myView);
                builder.setCancelable(false);
                AlertDialog dialogUsername = builder.create();
                dialogUsername.show();
            }
        });

        buttonEditPhoneNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Phone Number Updated", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                View myView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_phonenumber,null);
                builder.setView(myView);
                builder.setCancelable(false);
                AlertDialog dialogUsername = builder.create();
                dialogUsername.show();
            }
        });

    }

}
