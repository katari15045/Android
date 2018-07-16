package com.github.katari15045.sensor.acc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 19-04-2018.
 */

public class AccDbHelper extends SQLiteOpenHelper{

    public AccDbHelper(Context context){
        super(context, "acc_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table acc( time_stamp varchar(256), x varchar(128)," +
                " y varchar(128), z varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists acc");
        onCreate(sqLiteDatabase);
    }

}
