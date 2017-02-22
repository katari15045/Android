package com.example.saketh.overflowmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.about:
                Toast.makeText(this, "About Page", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.contactUs:
                Toast.makeText(this,"Contact us Page",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.help:
                Toast.makeText(this,"Help Page",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.settings:
                Toast.makeText(this,"Settings Page",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.siteMap:
                Toast.makeText(this,"SiteMap Page",Toast.LENGTH_SHORT).show();
                return  true;

        }

        return true;
    }
}
