package com.github.katari15045.sensor.gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class GpsDbHelper extends SQLiteOpenHelper {

    public GpsDbHelper(Context context){
        super(context, "gps_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table gps( time_stamp varchar(256), latitude varchar(128)," +
                " longitude varchar(128), altitude varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists gps");
        onCreate(sqLiteDatabase);
    }

}
