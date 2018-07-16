package com.github.katari15045.sensor.mic;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
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

// Tutorial : https://stackoverflow.com/a/15703029/8279892
public class MicActivity extends AppCompatActivity {

    private static TextView textViewData = null;
    private static Button buttonTracking = null;
    private static Button buttonExport = null;
    private static ImageView imageViewDelete = null;
    private static MediaRecorder mediaRecorder = null;
    private static boolean isTracked = false;
    private static MicDbHelper dbHelper = null;
    public static SQLiteDatabase db = null;
    private static Context context = null;
    private static Thread micListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic);
        context = this;
        getSupportActionBar().setTitle(getResources().getString(R.string.mic));
        captureViews();
        textViewData.setText(getResources().getString(R.string.reading));
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
        Log.d("SAK", "MicActivity::onDestroy()");
        super.onDestroy();
        closeDb();
    }

    private void captureViews(){
        textViewData = findViewById(R.id.activity_mic_textview_data);
        buttonTracking = findViewById(R.id.activity_mic_button_tracking);
        buttonTracking.setOnClickListener(new MicTrackingListener());
        buttonExport = findViewById(R.id.activity_mic_button_export);
        buttonExport.setOnClickListener(new MicExportListener());
        imageViewDelete = findViewById(R.id.activity_mic_imageview_delete);
        imageViewDelete.setOnClickListener(new MicDeleteListener());
    }

    public static void update(String decibels){
        StringBuilder sb = new StringBuilder();
        String timeStamp = Earth.getCurTimeStamp();
        sb.append(decibels).append(" dB");
        textViewData.setText(sb.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("time_stamp", timeStamp);
        contentValues.put("db", decibels);
        if(db == null){
            return;
        }
        db.insert("mic", null, contentValues);
    }

    //Tutorial : https://stackoverflow.com/a/30794679/8279892
    public static double getDecibels(){
        if(mediaRecorder == null){
            return 987.654;
        }
        double maxAmp = mediaRecorder.getMaxAmplitude();
        if(maxAmp == 0){
            return  0.0;
        }
        Log.d("SAK", "MicActivity::non-zero maxAmplitude : " + maxAmp);
        return 20*Math.log10(maxAmp/32767.0);
    }

    public void startTracking(){
        Log.d("SAK", "MicActivity::startTracking()");
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PermissionChecker.PERMISSION_GRANTED){
            Log.d("SAK", "MicActivity::Record audio permission is not granted!");
            Toast.makeText(this, getResources().getString(R.string.no_record_audio),
                    Toast.LENGTH_LONG).show();
            isTracked = false;
            return;
        }
        mediaRecorder = new MediaRecorder();
        if(mediaRecorder == null){
            Log.d("SAK", "MicActivity::mediaRecorder is null");
        }
        try{
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null");
            mediaRecorder.prepare();
            mediaRecorder.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        double dBdouble = getDecibels();
        if(dBdouble == 987.654){
            return;
        }
        buttonTracking.setText(getResources().getString(R.string.stop_tracking));
        openDb();
        String dbStr = String.valueOf(Earth.get2Decimals(dBdouble));
        update(dbStr);
        Log.d("SAK", "MicActivity::Listening...");
        MicListener micListenerRunnable = new MicListener(this);;
        micListener = new Thread(micListenerRunnable);
        micListener.start();
    }

    public void stopTracking(){
        Log.d("SAK", "MicActivity::stopTracking()");
        buttonTracking.setText(getResources().getString(R.string.start_tracking));
        try{
            micListener.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        textViewData.setText(getResources().getString(R.string.reading));
    }

    public static SQLiteDatabase getDb(){
        if(db == null || !db.isOpen()){
            openDb();
        }
        return db;
    }

    private static void openDb(){
        dbHelper = new MicDbHelper(context);
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

    public static Context getContext(){
        return context;
    }

    public static MediaRecorder getMediaRecorder(){
        return  mediaRecorder;
    }

    public static void setIsTracked(boolean isTracked){
        MicActivity.isTracked = isTracked;
    }

    public static boolean getIsTracked(){
        return isTracked;
    }

    public static TextView getTextViewData(){
        return  textViewData;
    }
}

class MicTrackingListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        MicActivity micActivity = (MicActivity) MicActivity.getContext();
        MicActivity.setIsTracked(!MicActivity.getIsTracked());
        if(MicActivity.getIsTracked()){
            micActivity.startTracking();
        }else{
            micActivity.stopTracking();
        }
    }
}

class MicExportListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Log.d("SAK", "MicActivity::Export button clicked!");
        StringBuilder sb = extractData();
        Earth.writeToFile(MicActivity.getContext(), "mic.csv", sb.toString());
    }

    private StringBuilder extractData(){
        String[] cols = {"time_stamp", "db"};
        Cursor cursor = MicActivity.getDb().query("mic", cols, null,
                null, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("Time Stamp,dB\n");
        while(cursor.moveToNext()){
            String timeSTamp = cursor.getString(0);
            String decibels = cursor.getString(1);
            sb.append(timeSTamp).append(",").append(decibels).append("\n");
        }
        return  sb;
    }
}

class MicDeleteListener implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        MicActivity.getDb().execSQL("delete from mic");
        Toast.makeText(MicActivity.getContext(), MicActivity.getContext().getResources().
                getString(R.string.past_data_flushed), Toast.LENGTH_SHORT).show();
    }
}

class MicListener implements Runnable{

    private MicActivity micActivity = null;

    public MicListener(MicActivity micActivity){
        this.micActivity = micActivity;
    }

    @Override
    public void run() {
        if(!MicActivity.getIsTracked()){
            return;
        }
        String prevdB = null;
        while(MicActivity.getIsTracked()){
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
            double dB = MicActivity.getDecibels();
            if(dB == 987.654){
                return;
            }
            final String curdB = String.valueOf(Earth.get2Decimals(dB));
            if(!curdB.equals(prevdB)){
                Log.d("SAK", "MicActivity::dB changed : " + curdB);
                micActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MicActivity.update(curdB);
                    }
                });
            }
            prevdB = curdB;
        }
    }
}