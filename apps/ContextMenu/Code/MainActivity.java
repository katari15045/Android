package com.example.root.floatingcontextualmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonSports;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSports = (Button) findViewById(R.id.buttonSports);
        registerForContextMenu(buttonSports);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.floating_contextual_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        int itemID = item.getItemId();

        if( itemID == R.id.itemSoccer )
        {
            Toast.makeText(MainActivity.this, getString(R.string.string_soccer_item), Toast.LENGTH_SHORT).show();
        }

        else if( itemID == R.id.itemTennis )
        {
            Toast.makeText(MainActivity.this, getString(R.string.string_tennis_item), Toast.LENGTH_SHORT).show();
        }

        else if( itemID == R.id.itemBaseBall )
        {
            Toast.makeText(MainActivity.this, getString(R.string.string_baseball_item), Toast.LENGTH_SHORT).show();
        }

        else if( itemID == R.id.itemBadminton )
        {
            Toast.makeText(MainActivity.this, getString(R.string.string_badminton_item), Toast.LENGTH_SHORT).show();
        }

        else if( itemID == R.id.itemBasketBall )
        {
            Toast.makeText(MainActivity.this, getString(R.string.string_basketball_item), Toast.LENGTH_SHORT).show();
        }

        else
        {
            return super.onContextItemSelected(item);
        }

        return true;
    }
}
