package com.github.katari15045.sensor.wifi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
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

import java.util.List;

public class WifiActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private static WifiManager wifiManager = null;
    private static LocationManager locationManager = null;  // Should Turn On the location
    private static boolean isTracked = false;
    private static WifiDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static WifiEventListener wifiEventListener = null;
    private static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.wifi));
        captureViews();
        initSensor();
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
        Log.d("SAK", "WifiActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_wifi_textview_data);
        buttonTracking = findViewById(R.id.activity_wifi_button_tracking);
        buttonTracking.setOnClickListener(new WifiTrackingListener());
        buttonExport = findViewById(R.id.activity_wifi_button_export);
        buttonExport.setOnClickListener(new WifiExportListener());
        imageViewDelete = findViewById(R.id.activity_wifi_imageview_delete);
        imageViewDelete.setOnClickListener(new WifiDeleteListener());
    }

    private void initSensor(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        textViewData.setText(getResources().getString(R.string.reading));
    }

    public static void update(List<ScanResult> aps){
        if(aps.size() == 0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        ContentValues contentValues = new ContentValues();
        String timeStamp = Earth.getCurTimeStamp();
        int count = 0;
        ScanResult ap = null;
        sb.append("SSID, BSSID :\n\n");
        while(count < aps.size()){
            ap = aps.get(count);
            sb.append(ap.SSID).append(", ").append(ap.BSSID).append("\n");
            contentValues.put("time_stamp", timeStamp);
            contentValues.put("ssid", ap.SSID);
            contentValues.put("bssid", ap.BSSID);
            getDb().insert("wifi", null, contentValues);
            count = count+1;
        }
        textViewData.setText(sb.toString());
    }

    // Tutorial : https://stackoverflow.com/a/20741259/8279892
    public void startTracking(){
        Log.d("SAK", "WifiActivity::startTracking()");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PermissionChecker.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this, Manifest.permission.CHANGE_WIFI_STATE) != PermissionChecker
                .PERMISSION_GRANTED){
            Log.d("SAK", "WifiActivity::Wifi permission is not granted!");
            Toast.makeText(this, getResources().getString(R.string.no_location_per),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d("SAK", "WifiActivity::Location is turned OFF!!!");
            Toast.makeText(this, getResources().getString(R.string.no_location),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        wifiManager.startScan();
        List<ScanResult> aps = wifiManager.getScanResults();
        Log.d("SAK", "WifiActivity:: # of APs : " + aps.size());
        update(aps);
        wifiEventListener = new WifiEventListener();
        IntentFilter filter = new IntentFilter();
        // https://developer.android.com/reference/android/net/wifi/WifiManager.html#SCAN_RESULTS_AVAILABLE_ACTION
        filter.addAction("android.net.wifi.SCAN_RESULTS");
        registerReceiver(wifiEventListener, filter);
        openDb();
        Log.d("SAK", "WifiActivity::Listener registered!");
    }

    private static void openDb(){
        dbHelper = new WifiDbHelper(context);
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
        Log.d("SAK", "WifiActivity::stopTracking()");
        textViewData.setText(getResources().getString(R.string.reading));
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        unregisterReceiver(wifiEventListener);
    }

    public static void setIsTracked(boolean isTracked){
        WifiActivity.isTracked = isTracked;
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

    public static WifiManager getWifiManager(){
        return  wifiManager;
    }
}

class WifiTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        WifiActivity wifiActivity = (WifiActivity) WifiActivity.getContext();
        WifiActivity.setIsTracked(!WifiActivity.getIsTracked());
        if(WifiActivity.getIsTracked()){
            wifiActivity.startTracking();
        }else{
            wifiActivity.stopTracking();
        }
    }
}

class WifiEventListener extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SAK", "WifiActivity::onReceive()");
        WifiManager wifiManager = WifiActivity.getWifiManager();
        wifiManager.startScan();
        List<ScanResult> results = wifiManager.getScanResults();
        WifiActivity.update(results);
    }
}

class WifiExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "WifiActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(WifiActivity.getContext(), "wifi.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "ssid", "bssid"};
        Cursor cursor = WifiActivity.getDb().query("wifi", cols, null,
                null, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Time Stamp,SSID,BSSID\n");
        while(cursor.moveToNext()){
            String timeSTamp = cursor.getString(0);
            String ssid = cursor.getString(1);
            String bssid = cursor.getString(2);
            sb.append(timeSTamp).append(",").append(ssid).append(",").append(bssid).append("\n");
        }
        return  sb;
    }
}

class WifiDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        WifiActivity.getDb().execSQL("delete from wifi");
        Toast.makeText(WifiActivity.getContext(), WifiActivity.getContext().getResources().
                getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}



