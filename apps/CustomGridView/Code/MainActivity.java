package com.example.root.customgridview;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ArrayList<String> listButtonLabels;
    ArrayList<Integer> listColors;

    GridView gridView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillLists();

    }

    private void fillLists()
    {
        fillListOne();
        fillListTwo();

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new MyAdapter(this, MainActivity.this, listButtonLabels, listColors);
        gridView.setAdapter(adapter);
    }

    private void fillListOne()
    {
        listButtonLabels = new ArrayList<String>(5);
        int count = 0;
        Integer integer;

        while( count < 5 )
        {
            integer = count + 1;
            listButtonLabels.add(count, integer.toString() );

            count = count + 1;
        }
    }

    private void fillListTwo()
    {
        listColors = new ArrayList<Integer>(5);

        listColors.add(0, ContextCompat.getColor(MainActivity.this, R.color.orange) );
        listColors.add(1, ContextCompat.getColor(MainActivity.this, R.color.yellow) );
        listColors.add(2, ContextCompat.getColor(MainActivity.this, R.color.green) );
        listColors.add(3, ContextCompat.getColor(MainActivity.this, R.color.blue) );
        listColors.add(4, ContextCompat.getColor(MainActivity.this, R.color.violet) );
    }

}
