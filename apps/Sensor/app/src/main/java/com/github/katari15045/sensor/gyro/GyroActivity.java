package com.github.katari15045.sensor.gyro;

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

public class GyroActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private static boolean isTracked = false;
    private static SensorManager sensorManager = null;
    private static Sensor gyro = null;
    private static GyroDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static boolean isAbsent = false;
    private static GyroEventListener gyroEventListener = null;
    private static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.gyroscope));
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
        Log.d("SAK", "GyroActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_gyro_textview_data);
        buttonTracking = findViewById(R.id.activity_gyro_button_tracking);
        buttonTracking.setOnClickListener(new GyroTrackingListener());
        buttonExport = findViewById(R.id.activity_gyro_button_export);
        buttonExport.setOnClickListener(new GyroExportListener());
        imageViewDelete = findViewById(R.id.activity_gyro_imageview_delete);
        imageViewDelete.setOnClickListener(new GyroDeleteListener());
    }

    private void initSensor(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyro == null){
            Log.d("SAK", "Gyro is absent!");
            isAbsent = true;
        }
        textViewData.setText(getResources().getString(R.string.reading));
    }

    public void startTracking(){
        Log.d("SAK", "GyroActivity::startTracking()");
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        gyroEventListener = new GyroEventListener();
        sensorManager.registerListener(gyroEventListener, gyro, SensorManager.SENSOR_DELAY_UI);
        openDb();
    }

    private static void openDb(){
        dbHelper = new GyroDbHelper(context);
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
        Log.d("SAK", "GyroActivity::stopTracking()");
        textViewData.setText(getResources().getString(R.string.reading));
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        sensorManager.unregisterListener(gyroEventListener);
    }

    public static void setIsTracked(boolean isTracked){
        GyroActivity.isTracked = isTracked;
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

class GyroTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        GyroActivity gyroActivity = (GyroActivity) GyroActivity.getContext();
        GyroActivity.setIsTracked(!GyroActivity.getIsTracked());
        if(GyroActivity.getIsTracked()){
            gyroActivity.startTracking();
        }else{
            gyroActivity.stopTracking();
        }
    }
}

class GyroEventListener implements SensorEventListener{
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!GyroActivity.getIsTracked()){
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
        GyroActivity.getTextViewData().setText(sb.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("time_stamp", timeStamp);
        contentValues.put("x", x);
        contentValues.put("y", y);
        contentValues.put("z", z);
        GyroActivity.getDb().insert("gyro", null, contentValues);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

class GyroExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "GyroActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(GyroActivity.getContext(), "gyroscope.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "x", "y", "z"};
        Cursor cursor = GyroActivity.getDb().query("gyro", cols, null,
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

class GyroDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        GyroActivity.getDb().execSQL("delete from gyro");
        Toast.makeText(GyroActivity.getContext(), GyroActivity.getContext().getResources().
                getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}





