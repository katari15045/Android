package com.github.katari15045.sensor.gps;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import java.util.ArrayList;
import java.util.List;

public class GpsActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private static boolean isTracked = false;
    private static LocationManager locationManager = null;
    private static GpsDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static GpsEventListener gpsEventListener = null;
    private static Context context = null;
    private static String gpsProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.gps));
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
        Log.d("SAK", "GpsActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_gps_textview_data);
        buttonTracking = findViewById(R.id.activity_gps_button_tracking);
        buttonTracking.setOnClickListener(new GpsTrackingListener());
        buttonExport = findViewById(R.id.activity_gps_button_export);
        buttonExport.setOnClickListener(new GpsExportListener());
        imageViewDelete = findViewById(R.id.activity_gps_imageview_delete);
        imageViewDelete.setOnClickListener(new GpsDeleteListener());
    }

    private void initSensor(){
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        textViewData.setText(getResources().getString(R.string.reading));
    }

    // Tutoria-l : https://www.youtube.com/watch?v=4S2nJh3Jxuw
    // Tutorial-2 : https://www.youtube.com/watch?v=5fjwDx8fOMk
    public void startTracking(){
        Log.d("SAK", "GpsActivity::startTracking()");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED){
            Log.d("SAK", "GpsActivity::Location permission is not granted!");
            Toast.makeText(this, getResources().getString(R.string.no_location_per),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d("SAK", "GpsActivity::Location is turned OFF!!!");
            Toast.makeText(this, getResources().getString(R.string.no_location),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        List<String> providers = locationManager.getProviders(true);
        int count = 0;
        Location location = null;
        while(count < providers.size()){
            location = locationManager.getLastKnownLocation(providers.get(count));
            if(location != null){
                break;
            }
            count = count+1;
        }
        if(location != null){
            Log.d("SAK", "GpsActivity::startTracking() Cur loc found!!!");
            gpsProvider = providers.get(count);
            Log.d("SAK", "Provider : " + gpsProvider);
            updateLocation(location);
        }else{
            Log.d("SAK", "GpsActivity::cur loc not found!");
            startTracking();
        }
        gpsEventListener = new GpsEventListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, gpsEventListener);
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        openDb();
    }

    public static void updateLocation(Location location){
        String timeStamp = Earth.getCurTimeStamp();
        String latitute = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        String altitude = String.valueOf(location.getAltitude());
        StringBuilder sb = new StringBuilder();
        sb.append("Latitude : ").append(latitute).append("\n");
        sb.append("Longitude : ").append(longitude).append("\n");
        sb.append("Altitude : ").append(altitude).append(" metres");
        textViewData.setText(sb.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("time_stamp", timeStamp);
        contentValues.put("latitude", latitute);
        contentValues.put("longitude", longitude);
        contentValues.put("altitude", altitude);
        getDb().insert("gps", null, contentValues);
    }

    private static void openDb(){
        dbHelper = new GpsDbHelper(context);
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
        Log.d("SAK", "GpsActivity::stopTracking()");
        textViewData.setText(getResources().getString(R.string.reading));
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        locationManager.removeUpdates(gpsEventListener);
    }

    public static void setIsTracked(boolean isTracked){
        GpsActivity.isTracked = isTracked;
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

class GpsTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        GpsActivity gpsActivity = (GpsActivity) GpsActivity.getContext();
        GpsActivity.setIsTracked(!GpsActivity.getIsTracked());
        if(GpsActivity.getIsTracked()){
            gpsActivity.startTracking();
        }else{
            gpsActivity.stopTracking();
        }
    }
}

class GpsEventListener implements LocationListener{
    @Override
    public void onLocationChanged(Location location) {
        Log.d("SAK", "GpsActivity::Location changed!");
        if(!GpsActivity.getIsTracked()){
            return;
        }
        GpsActivity.updateLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

class GpsExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "GpsActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(GpsActivity.getContext(), "gps.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "latitude", "longitude", "altitude"};
        Cursor cursor = GpsActivity.getDb().query("gps", cols, null,
                null, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Time Stamp,Latitude,Longitude,Altitude in metres\n");
        while(cursor.moveToNext()){
            String timeSTamp = cursor.getString(0);
            String latitude = cursor.getString(1);
            String longitude = cursor.getString(2);
            String altitude = cursor.getString(3);
            sb.append(timeSTamp).append(",").append(latitude).append(",").append(longitude)
                    .append(",").append(altitude).append("\n");
        }
        return  sb;
    }
}

class GpsDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        GpsActivity.getDb().execSQL("delete from gps");
        Toast.makeText(GpsActivity.getContext(), GpsActivity.getContext().getResources().
                getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}





