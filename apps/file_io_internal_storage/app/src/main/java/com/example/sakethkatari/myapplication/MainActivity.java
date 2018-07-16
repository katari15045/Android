package com.example.sakethkatari.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextPetrol = null;
    private EditText editTextDiesel = null;
    private EditText editTextAir = null;
    private TextView textViewResult = null;
    private Button buttonSubmit = null;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        createFile("sample.txt", "10\n20\n30\nSaketh\n40\n50\n60\nKatari\n");

        editTextPetrol = (EditText) findViewById(R.id.editTextPetrol);
        editTextDiesel = (EditText) findViewById(R.id.editTextDiesel);
        editTextAir = (EditText) findViewById(R.id.editTextAir);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener( new MyListener(editTextPetrol, editTextDiesel, editTextAir, textViewResult, context) );
    }

    private void createFile(String filename, String data)
    {
        FileOutputStream fos = null;

        try
        {
            fos =context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write( data.getBytes() );
            fos.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class MyListener implements View.OnClickListener
{
    private EditText editTextPetrol = null;
    private EditText editTextDiesel = null;
    private EditText editTextAir = null;
    private TextView textViewResult = null;
    private  Context context = null;

    private String petrol = null;
    private String diesel = null;
    private String air = null;
    private String filename = null;
    private String result = null;

    MyListener(EditText editTextPetrol, EditText editTextDiesel, EditText editTextAir, TextView textViewResult, Context context)
    {
        this.editTextPetrol = editTextPetrol;
        this.editTextDiesel = editTextDiesel;
        this.editTextAir = editTextAir;
        this.textViewResult = textViewResult;
        this.context = context;
    }

    @Override
    public void onClick(View view)
    {
        petrol = editTextPetrol.getText().toString();
        diesel = editTextDiesel.getText().toString();
        air = editTextAir.getText().toString();
        filename = "sample.txt";

        result = foo(filename, petrol, diesel, air );

        if( result == null )
        {
            result = "No entry found!";
        }

        textViewResult.setText(result);
    }

    private String foo(String filename, String petrol, String diesel, String air)
    {
        FileReader fileReader = null;
        BufferedReader br = null;
        String currentPetrol = null, currentDiesel = null, currentAir = null, currentResult = null;
        File file = null;


        try
        {
            file = new File(context.getFilesDir(), filename);
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);

            while(true)
            {
                currentPetrol = br.readLine();

                if( currentPetrol == null )
                {
                    currentResult = null;
                    break;
                }

                currentDiesel = br.readLine();

                if( currentDiesel == null )
                {
                    currentResult = null;
                    break;
                }

                currentAir = br.readLine();

                if( currentAir == null )
                {
                    currentResult = null;
                    break;
                }

                currentResult = br.readLine();

                if( currentResult == null )
                {
                    break;
                }

                if( currentPetrol.equals(petrol) && currentDiesel.equals(diesel) && currentAir.equals(air) )
                {
                    break;
                }
            }

            fileReader.close();
            br.close();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return currentResult;
    }
}






