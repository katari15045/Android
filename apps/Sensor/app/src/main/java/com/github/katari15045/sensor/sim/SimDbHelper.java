package com.github.katari15045.sensor.sim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class SimDbHelper extends SQLiteOpenHelper {

    public SimDbHelper(Context context){
        super(context, "sim_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table sim( time_stamp varchar(256), lac varchar(128)," +
                " cid varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists sim");
        onCreate(sqLiteDatabase);
    }


}
