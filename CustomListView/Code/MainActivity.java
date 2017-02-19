package com.example.saketh.customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
{

    ListView lv;
    LinkedList<String> listNames;
    LinkedList<Integer> listImages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        listNames = new LinkedList<String>();
        listImages = new LinkedList<Integer>();

        listNames.add("Settings");
        listNames.add("Wifi");
        listNames.add("Sounds");
        listNames.add("Security");
        listNames.add("Location");
        listNames.add("Developer Options");
        listNames.add("Accounts");
        listNames.add("SIM Card");
        listNames.add("Apps");
        listNames.add("Bluetooth");

        listImages.add(R.drawable.settings);
        listImages.add(R.drawable.wifi);
        listImages.add(R.drawable.sounds);
        listImages.add(R.drawable.security);
        listImages.add(R.drawable.location);
        listImages.add(R.drawable.developer_options);
        listImages.add(R.drawable.accounts);
        listImages.add(R.drawable.sim);
        listImages.add(R.drawable.apps);
        listImages.add(R.drawable.bluetooth);

        MyAdapter adapter = new MyAdapter(this,listImages,listNames);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position == 0)
                {
                    Toast.makeText(MainActivity.this,"Settings Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1)
                {
                    Toast.makeText(MainActivity.this,"Wifi Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 2)
                {
                    Toast.makeText(MainActivity.this,"Sounds Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 3)
                {
                    Toast.makeText(MainActivity.this,"Security Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 4)
                {
                    Toast.makeText(MainActivity.this,"Location Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 5)
                {
                    Toast.makeText(MainActivity.this,"Developers Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 6)
                {
                    Toast.makeText(MainActivity.this,"Accounts Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 7)
                {
                    Toast.makeText(MainActivity.this,"SIM Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 8)
                {
                    Toast.makeText(MainActivity.this,"Apps Page",Toast.LENGTH_SHORT).show();
                }

                else if(position == 9)
                {
                    Toast.makeText(MainActivity.this,"Bluetooth Page",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
