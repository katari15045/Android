package com.example.saketh.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EditText etName;
    private EditText etEMail;
    private EditText etUsername;
    private EditText etPassword;
    private Button butSignup;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.EditTextName);
        etEMail = (EditText) findViewById(R.id.EditTextEMail);
        etUsername = (EditText) findViewById(R.id.EditTextUsername);
        etPassword = (EditText) findViewById(R.id.EditTextPassword);
        butSignup = (Button) findViewById(R.id.ButtonSignUp);
        rg = (RadioGroup) findViewById(R.id.RadioGroupOne);

        butSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String salute;
                String name = etName.getText().toString();
                String email = etEMail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                int rbId = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(rbId);

                if(rb.getText().toString().equals("Male"))
                {
                    salute = "Mr.";
                }

                else
                {
                    salute = "Ms.";
                }

                Intent myIntent = new Intent(MainActivity.this,Main2Activity.class);
                myIntent.putExtra("LabelSalute",salute);
                myIntent.putExtra("LabelName",name);
                myIntent.putExtra("LabelEmail",email);
                myIntent.putExtra("LabelUsername", username);
                myIntent.putExtra("LabelPassword",password);
                startActivity(myIntent);
            }
        });


    }
}
