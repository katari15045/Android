package com.example.root.validatefields;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextGalaxy;
    EditText editTextPlanet;
    EditText editTextContinent;
    EditText editTextCountry;

    TextInputLayout textInputLayoutGalaxy;
    TextInputLayout textInputLayoutPlanet;
    TextInputLayout textInputLayoutContinent;
    TextInputLayout textInputLayoutCountry;

    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        buttonSubmit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if( validateFields() )
                {
                    Toast.makeText(MainActivity.this, getString(R.string.string_submission_valid), Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(MainActivity.this, getString(R.string.string_submission_invalid), Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
    }

    private void initializeViews()
    {
        editTextGalaxy = (EditText) findViewById(R.id.editTextGalaxy);
        editTextPlanet = (EditText) findViewById(R.id.editTextPlanet);
        editTextContinent = (EditText) findViewById(R.id.editTextContinent);
        editTextCountry = (EditText) findViewById(R.id.editTextCountry);

        textInputLayoutGalaxy = (TextInputLayout) findViewById(R.id.textInputLayoutGalaxy);
        textInputLayoutPlanet = (TextInputLayout) findViewById(R.id.textInputLayoutPlanet);
        textInputLayoutContinent = (TextInputLayout) findViewById(R.id.textInputLayoutContinent);
        textInputLayoutCountry = (TextInputLayout) findViewById(R.id.textInputLayoutCountry);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
    }

    private boolean validateFields()
    {
        validateGalaxy();
        validatePlanet();
        validateContinent();
        validateCountry();

        if( validateGalaxy() && validatePlanet() && validateContinent() && validateCountry() )
        {
            return true;
        }

        return  false;
    }

    private boolean validateGalaxy()
    {
        boolean isValid = true;

        if( editTextGalaxy.getText().toString().isEmpty() )
        {
            isValid = false;
            textInputLayoutGalaxy.setError( getString( R.string.string_galaxy_invalid ) );
        }

        else
        {
            textInputLayoutGalaxy.setErrorEnabled(false);
        }

        return isValid;
    }

    private boolean validatePlanet()
    {
        boolean isValid = true;

        if( editTextPlanet.getText().toString().isEmpty() )
        {
            isValid = false;
            textInputLayoutPlanet.setError( getString(R.string.string_planet_invalid) );
        }

        else
        {
            textInputLayoutPlanet.setErrorEnabled(false);
        }

        return  isValid;
    }

    private boolean validateContinent()
    {
        boolean isValid = true;

        if( editTextContinent.getText().toString().isEmpty() )
        {
            isValid = false;
            textInputLayoutContinent.setError( getString(R.string.string_continent_invalid) );
        }

        else
        {
            textInputLayoutContinent.setErrorEnabled(false);
        }

        return isValid;
    }

    private boolean validateCountry()
    {
        boolean isValid = true;

        if( editTextCountry.getText().toString().isEmpty() )
        {
            isValid = false;
            textInputLayoutCountry.setError( getString(R.string.string_country_invalid) );
        }

        else
        {
            textInputLayoutCountry.setErrorEnabled(false);
        }

        return isValid;
    }
}
