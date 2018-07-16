package com.github.katari15045.sensor.mic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saketh Katari on 20-04-2018.
 */

public class MicDbHelper extends SQLiteOpenHelper {

    public MicDbHelper(Context context){
        super(context, "mic_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mic( time_stamp varchar(256), db varchar(128) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists mic");
        onCreate(sqLiteDatabase);
    }
}
