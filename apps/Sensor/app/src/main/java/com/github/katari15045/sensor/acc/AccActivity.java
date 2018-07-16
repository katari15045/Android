package com.github.katari15045.sensor.acc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.katari15045.sensor.Earth;
import com.github.katari15045.sensor.R;

public class AccActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private static boolean isTracked = false;
    private static SensorManager sensorManager = null;
    private static Sensor accelrometre = null;
    private static AccDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static boolean isAbsent = false;
    private static AccEventListener accEventListener = null;
    private static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.accelerometre));
        captureViews();
        initSensor();
        if(isAbsent){
            Toast.makeText(this, getResources().getString(R.string.no_sensor),
                    Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTracked){
            startTracking();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("SAK", "AccActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_acc_textview_data);
        buttonTracking = findViewById(R.id.activity_acc_button_tracking);
        buttonTracking.setOnClickListener(new AccTrackingListener());
        buttonExport = findViewById(R.id.activity_acc_button_export);
        buttonExport.setOnClickListener(new AccExportListener());
        imageViewDelete = findViewById(R.id.activity_acc_imageview_delete);
        imageViewDelete.setOnClickListener(new AccDeleteListener());
    }

    private void initSensor(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelrometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelrometre == null){
            isAbsent = true;
        }
        textViewData.setText(getResources().getString(R.string.reading));
    }

    public void startTracking(){
        Log.d("SAK", "AccActivity::startTracking()");
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        accEventListener = new AccEventListener();
        sensorManager.registerListener(accEventListener, accelrometre, SensorManager.SENSOR_DELAY_UI);
        openDb();
    }

    private static void openDb(){
        dbHelper = new AccDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private static void closeDb(){
        if(db != null){
            db.close();
        }
        if(dbHelper != null){
            dbHelper.close();
        }
    }

    public void stopTracking(){
        Log.d("SAK", "AccActivity::stopTracking()");
        textViewData.setText(getResources().getString(R.string.reading));
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        sensorManager.unregisterListener(accEventListener);
    }

    public static void setIsTracked(boolean isTracked){
        AccActivity.isTracked = isTracked;
    }

    public static boolean getIsTracked(){
        return isTracked;
    }

    public static TextView getTextViewData(){
        return textViewData;
    }

    public static SQLiteDatabase getDb(){
        if(db == null || !db.isOpen()){
            openDb();
        }
        return db;
    }

    public static Context getContext(){
        return  context;
    }
}

class AccTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        AccActivity accActivity = (AccActivity) AccActivity.getContext();
        AccActivity.setIsTracked(!AccActivity.getIsTracked());
        if(AccActivity.getIsTracked()){
            accActivity.startTracking();
        }else{
            accActivity.stopTracking();
        }
    }
}

class AccEventListener implements SensorEventListener{
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!AccActivity.getIsTracked()){
            return;
        }
        String timeStamp = Earth.getCurTimeStamp();
        StringBuilder sb = new StringBuilder();
        String x, y, z;
        x = String.valueOf(Earth.get2Decimals(sensorEvent.values[0]));
        y = String.valueOf(Earth.get2Decimals(sensorEvent.values[1]));
        z = String.valueOf(Earth.get2Decimals(sensorEvent.values[2]));
        sb.append("X : ").append(x).append("\n");
        sb.append("Y : ").append(y).append("\n");
        sb.append("Z : ").append(z);
        AccActivity.getTextViewData().setText(sb.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("time_stamp", timeStamp);
        contentValues.put("x", x);
        contentValues.put("y", y);
        contentValues.put("z", z);
        AccActivity.getDb().insert("acc", null, contentValues);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

class AccExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "AccActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(AccActivity.getContext(), "accelerometre.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "x", "y", "z"};
        Cursor cursor = AccActivity.getDb().query("acc", cols, null,
                null, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Time Stamp,X,Y,Z\n");
        while(cursor.moveToNext()){
            String timeSTamp = cursor.getString(0);
            String x = cursor.getString(1);
            String y = cursor.getString(2);
            String z = cursor.getString(3);
            sb.append(timeSTamp).append(",").append(x).append(",").append(y).append(",").append(z)
                    .append("\n");
        }
        return  sb;
    }
}

class AccDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        AccActivity.getDb().execSQL("delete from acc");
        Toast.makeText(AccActivity.getContext(), AccActivity.getContext().getResources().
                        getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}





