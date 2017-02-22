package com.example.saketh.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    ListView lv;
    List<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = new LinkedList<String>();
        myList.add("About");
        myList.add("Settings");
        myList.add("Sounds");
        myList.add("Storage");
        myList.add("Wifi");
        myList.add("Display");
        myList.add("Developer Options");
        myList.add("System UI Tuner");
        myList.add("Accounts");
        myList.add("Accessibility");
        myList.add("More...");
        myList.add("Help");

        lv = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,myList);
        lv.setAdapter(myArrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    Toast.makeText(MainActivity.this,"About Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1)
                {
                    Toast.makeText(MainActivity.this,"Settings Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 2)
                {
                    Toast.makeText(MainActivity.this,"Sounds Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 3)
                {
                    Toast.makeText(MainActivity.this,"Storage Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 4)
                {
                    Toast.makeText(MainActivity.this,"Wifi Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 5)
                {
                    Toast.makeText(MainActivity.this,"Display Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 6)
                {
                    Toast.makeText(MainActivity.this,"Developer Options Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 7)
                {
                    Toast.makeText(MainActivity.this,"System UI Tuner Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 8)
                {
                    Toast.makeText(MainActivity.this,"Accounts Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 9)
                {
                    Toast.makeText(MainActivity.this,"Accessibility Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 10)
                {
                    Toast.makeText(MainActivity.this,"More... Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 11)
                {
                    Toast.makeText(MainActivity.this,"Help Page",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
