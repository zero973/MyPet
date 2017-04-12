package com.example.student3.myfavouritepet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// создаем таблицу с полями
        db.execSQL("create table myDataTable (" + "id integer primary key autoincrement," + "name text," + "PetType text," +
                "RoomColor text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
