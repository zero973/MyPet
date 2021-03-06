package com.example.student3.myfavouritepet.HelpClasses.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// создаем таблицу с полями
        db.execSQL("create table PetTable (id integer primary key autoincrement,name text,PetType integer,RoomColor integer, money integer);");
        db.execSQL("create table FoodTable (id integer primary key autoincrement,Watermelon integer,Pear integer,Strawberry integer, Apple integer, Lemon integer, Carrot integer, Potato integer, Icecream integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
