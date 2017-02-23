package com.example.root.gridlayout;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        handleButtons();
    }

    private void initializeViews()
    {
        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFive = (Button) findViewById(R.id.buttonFive);
    }

    private void handleButtons()
    {
        handleButtoneOne();
        handleButtonTwo();
        handleButtonThree();
        handleButtonFour();
        handleButtonFive();
    }

    private void handleButtoneOne()
    {
        buttonOne.setOnClickListener( new View.OnClickListener()
        {
            View rootView;

            @Override
            public void onClick(View v)
            {
                rootView = buttonOne.getRootView();
                rootView.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.yellow) );
            }
        }
        );
    }

    private void handleButtonTwo()
    {
        buttonTwo.setOnClickListener(new View.OnClickListener()
        {
            View rootView;

            @Override
            public void onClick(View v)
            {
                rootView = buttonTwo.getRootView();
                rootView.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.green) );
            }
        });
    }

    private void handleButtonThree()
    {
        buttonThree.setOnClickListener(new View.OnClickListener()
        {
            View rootView;

            @Override
            public void onClick(View v)
            {
                rootView = buttonThree.getRootView();
                rootView.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.maroon_red) );
            }
        });
    }

    private void handleButtonFour()
    {
        buttonFour.setOnClickListener(new View.OnClickListener()
        {
            View rootView;

            @Override
            public void onClick(View v)
            {
                rootView = buttonFour.getRootView();
                rootView.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.orange) );
            }
        });
    }

    private void handleButtonFive()
    {
        buttonFive.setOnClickListener( new View.OnClickListener()
        {
            View rootView;

            @Override
            public void onClick(View v)
            {
                rootView = buttonFive.getRootView();
                rootView.setBackgroundColor( ContextCompat.getColor(MainActivity.this, R.color.white) );
            }
        }
        );
    }
}
