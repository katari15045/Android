package com.github.katari15045.sensor.sim;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.katari15045.sensor.Earth;
import com.github.katari15045.sensor.R;

// What is LAC & CID :  http://www.instructables.com/id/Track-your-location-without-using-GPS-using-LAC-a/
//                      http://vastinfos.com/2016/07/difference-between-fine-and-coarse-locations-android-gps/

public class SimActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private TelephonyManager telephonyManager = null;
    private static boolean isTracked = false;
    private static SimDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static SimEventListener simEventListener = null;
    private static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.cell_id));
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
        Log.d("SAK", "SimActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_sim_textview_data);
        buttonTracking = findViewById(R.id.activity_sim_button_tracking);
        buttonTracking.setOnClickListener(new SimTrackingListener());
        buttonExport = findViewById(R.id.activity_sim_button_export);
        buttonExport.setOnClickListener(new SimExportListener());
        imageViewDelete = findViewById(R.id.activity_sim_imageview_delete);
        imageViewDelete.setOnClickListener(new SimDeleteListener());
    }

    private void initSensor(){
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        textViewData.setText(getResources().getString(R.string.reading));
    }

    public static void updateLocation(String lac, String cid){
        String timeStamp = Earth.getCurTimeStamp();
        StringBuilder sb = new StringBuilder();
        sb.append("LAC : ").append(lac).append("\n");
        sb.append("CID : ").append(cid);
        textViewData.setText(sb.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("time_stamp", timeStamp);
        contentValues.put("lac", lac);
        contentValues.put("cid", cid);
        getDb().insert("sim", null, contentValues);
    }

    public void startTracking(){
        Log.d("SAK", "SimActivity::startTracking()");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker
                .PERMISSION_GRANTED){
            Log.d("SAK", "SimActivity::Location permission is not granted!");
            Toast.makeText(this, getResources().getString(R.string.no_location_per),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        simEventListener = new SimEventListener();
        telephonyManager.listen(simEventListener, PhoneStateListener.LISTEN_CELL_LOCATION);
        openDb();
    }

    private static void openDb(){
        dbHelper = new SimDbHelper(context);
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
        Log.d("SAK", "SimActivity::stopTracking()");
        textViewData.setText(getResources().getString(R.string.reading));
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        telephonyManager.listen(simEventListener, PhoneStateListener.LISTEN_NONE);
    }

    public static void setIsTracked(boolean isTracked){
        SimActivity.isTracked = isTracked;
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

class SimTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        SimActivity simActivity = (SimActivity) SimActivity.getContext();
        SimActivity.setIsTracked(!SimActivity.getIsTracked());
        if(SimActivity.getIsTracked()){
            simActivity.startTracking();
        }else{
            simActivity.stopTracking();
        }
    }
}

// Tutorial : https://stackoverflow.com/questions/17968883/android-phonestatelistener-oncelllocationchanged
class SimEventListener extends PhoneStateListener{
    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if(!SimActivity.getIsTracked()){
            return;
        }
        Log.d("SAK", "SimActivity::Location Changed!");
        String combLoc = location.toString();
        String[] strArray = combLoc.split(",");
        String lac = strArray[0].substring(1, strArray[0].length());
        String cid = strArray[1];
        Log.d("SAK", "LAC -> " + lac + ", CID -> " + cid);
        SimActivity.updateLocation(lac, cid);
    }
}

class SimExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "SimActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(SimActivity.getContext(), "sim.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "lac", "cid"};
        Cursor cursor = SimActivity.getDb().query("sim", cols, null,
                null, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Time Stamp,LAC,CID\n");
        while(cursor.moveToNext()){
            String timeSTamp = cursor.getString(0);
            String lac = cursor.getString(1);
            String cid = cursor.getString(2);
            sb.append(timeSTamp).append(",").append(lac).append(",").append(cid).append("\n");
        }
        return  sb;
    }
}

class SimDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        SimActivity.getDb().execSQL("delete from sim");
        Toast.makeText(SimActivity.getContext(), SimActivity.getContext().getResources().
                getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}





