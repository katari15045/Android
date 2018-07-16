package com.github.katari15045.sensor.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.katari15045.sensor.R;
import com.github.katari15045.sensor.acc.AccActivity;
import com.github.katari15045.sensor.gps.GpsActivity;
import com.github.katari15045.sensor.gyro.GyroActivity;
import com.github.katari15045.sensor.mic.MicActivity;
import com.github.katari15045.sensor.sim.SimActivity;
import com.github.katari15045.sensor.wifi.WifiActivity;

import java.util.ArrayList;

/**
 * Created by Saketh Katari on 20-04-2018.
 */

public class SensorListAdapter extends RecyclerView.Adapter<SensorViewHolder> {

    private Context context = null;
    private ArrayList<String> db = null;
    private View view = null;

    public SensorListAdapter(Context context, ArrayList<String> db){
        this.context = context;
        this.db = db;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sensor_list_item, parent, false);
        this.view = view;
        return new SensorViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, int position) {
        String sensorName = db.get(position);
        holder.textViewSensor.setText(sensorName);
    }

    @Override
    public int getItemCount() {
        return  db.size();
    }
}

class SensorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView textViewSensor = null;
    private Context context = null;

    public SensorViewHolder(View itemView, Context context) {
        super(itemView);
        textViewSensor = itemView.findViewById(R.id.sensor_list_item_textview);
        textViewSensor.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public void onClick(View view) {
       int pos = this.getAdapterPosition();
       Intent intent = null;
       if(pos == 0){
           intent = new Intent(context, AccActivity.class);
       }else if(pos == 1){
           intent = new Intent(context, GyroActivity.class);
       }else if(pos == 2){
           intent = new Intent(context, GpsActivity.class);
       }else if(pos == 3){
           intent = new Intent(context, SimActivity.class);
       }else if(pos == 4){
           intent = new Intent(context, WifiActivity.class);
       }else if(pos == 5){
           intent = new Intent(context, MicActivity.class);
       }
       context.startActivity(intent);
    }
}