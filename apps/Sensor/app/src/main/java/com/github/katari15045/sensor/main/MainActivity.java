package com.github.katari15045.sensor.main;


import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.github.katari15045.sensor.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private SensorListAdapter adapter = null;
    private ArrayList<String> db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SAK", "MainActivity::onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getResources().getString(R.string.sensors));
        fillDb();
        recyclerView = findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SensorListAdapter(this, db);
        recyclerView.setAdapter(adapter);
    }

    private void fillDb(){
        db = new ArrayList<>(6);
        db.add(getResources().getString(R.string.accelerometre));
        db.add(getResources().getString(R.string.gyroscope));
        db.add(getResources().getString(R.string.gps));
        db.add(getResources().getString(R.string.cell_id));
        db.add(getResources().getString(R.string.wifi));
        db.add(getResources().getString(R.string.mic));

    }
}





