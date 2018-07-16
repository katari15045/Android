package com.example.sakethkatari.accesspostgresql;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextIP = null;
    private EditText editTextPort = null;
    private EditText editTextDBName = null;
    private EditText editTextUsername = null;
    private EditText editTextPassword = null;
    private EditText editTextCommand = null;
    private TextView textViewResult = null;
    private RadioGroup radioGroup = null;
    private Button buttonExecute = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();
        collectViews();
        buttonExecute.setOnClickListener( new ExecuteListener(editTextIP, editTextPort, editTextDBName, editTextUsername,
                                                                editTextPassword, editTextCommand, radioGroup, textViewResult, MainActivity.this) );
    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void collectViews()
    {
        editTextIP = (EditText) findViewById(R.id.main_edit_text_ip);
        editTextPort = (EditText) findViewById(R.id.main_edit_text_port);
        editTextDBName = (EditText) findViewById(R.id.main_edit_text_db_name);
        editTextUsername = (EditText) findViewById(R.id.main_edit_text_username);
        editTextPassword = (EditText) findViewById(R.id.main_edit_text_password);
        editTextCommand = (EditText) findViewById(R.id.main_edit_text_command);
        radioGroup = (RadioGroup) findViewById(R.id.main_radio_group);
        textViewResult = (TextView) findViewById(R.id.main_text_view_output);
        buttonExecute = (Button) findViewById(R.id.main_button_execute);
    }
}

class ExecuteListener implements View.OnClickListener
{
    private EditText editTextIP = null;
    private EditText editTextPort = null;
    private EditText editTextDBName = null;
    private EditText editTextUsername = null;
    private EditText editTextPassword = null;
    private EditText editTextCommand = null;
    private RadioGroup radioGroup = null;
    private TextView textViewResult = null;
    private Context context = null;
    private boolean isQuery;

    public ExecuteListener(EditText editTextIP, EditText editTextPort, EditText editTextDBName, EditText editTextUsername,
                           EditText editTextPassword, EditText editTextCommand, RadioGroup radioGroup, TextView textViewResult, Context context)
    {
        this.editTextIP = editTextIP;
        this.editTextPort = editTextPort;
        this.editTextDBName = editTextDBName;
        this.editTextUsername = editTextUsername;
        this.editTextPassword = editTextPassword;
        this.editTextCommand = editTextCommand;
        this.radioGroup = radioGroup;
        this.textViewResult = textViewResult;
        this.context = context;
    }

    public void onClick(View view)
    {
        if( radioGroup.getCheckedRadioButtonId() == R.id.main_radio_button_query )
        {
            isQuery = true;
        }

        else if( radioGroup.getCheckedRadioButtonId() == R.id.main_radio_button_update )
        {
            isQuery = false;
        }


        PostGreSQL postGreSQL = new PostGreSQL( editTextIP.getText().toString(), editTextPort.getText().toString(),
                editTextDBName.getText().toString(), editTextUsername.getText().toString(),
                editTextPassword.getText().toString(), editTextCommand.getText().toString(), isQuery);

        Thread dbThread = new Thread(postGreSQL);

        try
        {
            dbThread.start();
            dbThread.join();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        textViewResult.setText( postGreSQL.getResult() );
    }
}

















