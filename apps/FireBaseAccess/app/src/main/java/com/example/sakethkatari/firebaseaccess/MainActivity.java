package com.example.sakethkatari.firebaseaccess;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAddress;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customizeStatusBar();
        customizeActionBar();
        initializeViews();
        buttonSend.setOnClickListener( new ButtonListener() );
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Firebase");
        actionBar.
        actionBar.setBackgroundDrawable( new ColorDrawable( ContextCompat.getColor(MainActivity.this, R.color.blue) ));
    }

    private void customizeStatusBar()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(MainActivity.this, R.color.blue) );
        }

    }

    private void initializeViews()
    {
        editTextName = (EditText) findViewById(R.id.idEditTextName);
        editTextAddress = (EditText) findViewById(R.id.idEditTextAddress);
        buttonSend = (Button) findViewById(R.id.idButtonSend);
    }

    private class ButtonListener implements View.OnClickListener
    {
        private String inputName;
        private String inputAddress;
        private DatabaseReference databaseReference;
        private String newKey;

        @Override
        public void onClick(View v)
        {
            getDataFromUser();

            if( !isDataValid() )
            {
                return;
            }

            User user = new User();
            user.create(inputName, inputAddress);
            databaseReference = FirebaseDatabase.getInstance().getReference("user");
            newKey = databaseReference.push().getKey();
            databaseReference.child(newKey).setValue(user);

            Toast.makeText(MainActivity.this, "Data Sent to Firebase!", Toast.LENGTH_SHORT).show();
        }

        private void getDataFromUser()
        {
            inputName = editTextName.getText().toString();
            inputAddress = editTextAddress.getText().toString();
        }

        private boolean isDataValid()
        {
            if( inputName.isEmpty() )
            {
                Toast.makeText( MainActivity.this, "Name can't be empty", Toast.LENGTH_SHORT ).show();
                return  false;
            }

            if( inputAddress.isEmpty() )
            {
                Toast.makeText( MainActivity.this, "Address can't be empty", Toast.LENGTH_SHORT ).show();
                return  false;
            }

            return true;
        }
    }
}
