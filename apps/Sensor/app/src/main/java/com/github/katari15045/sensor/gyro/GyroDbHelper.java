package com.github.katari15045.sensor.gyro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class GyroDbHelper extends SQLiteOpenHelper {

    public GyroDbHelper(Context context){
        super(context, "gyro_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table gyro( time_stamp varchar(256), x varchar(128)," +
                " y varchar(128), z varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists gyro");
        onCreate(sqLiteDatabase);
    }


}
