package com.github.katari15045.sensor;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class Earth {

    // Tutorial : https://www.youtube.com/watch?v=7CEcevGbIZU
    public static void writeToFile(Context context, String filename, String data){
        if(!isExternalStorageWritable()){
            Log.d("SAK", "External storage is not writable!");
            return;
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(context, context.getResources().getString(R.string.cant_write),
                    Toast.LENGTH_LONG).show();
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(context, context.getResources().getString(R.string.post_export),
                    Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isExternalStorageWritable() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }
        return false;
    }

    public static double get2Decimals(double inp){
        return BigDecimal.valueOf(inp).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String getCurTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new java.util.Date());
    }
}
