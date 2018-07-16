package com.github.katari15045.sensor.wifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class WifiDbHelper extends SQLiteOpenHelper {

    public WifiDbHelper(Context context){
        super(context, "wifi_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table wifi( time_stamp varchar(256), ssid varchar(128)," +
                " bssid varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists wifi");
        onCreate(sqLiteDatabase);
    }


}
