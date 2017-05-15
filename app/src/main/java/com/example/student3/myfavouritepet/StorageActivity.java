package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StorageActivity extends Activity implements View.OnClickListener{

    private TextView tvCountWatermelon, tvCountPear, tvCountStrawberry, tvCountApple, tvCountLemon, tvCountMorkov, tvCountPotato, tvCountIcecream;
    private ImageButton IBplusWaterMelon, IBplusPear, IBplusStrawberry, IBplusApple, IBplusLemon, IBplusMorkov, IBplusPotato, IBplusIcecream;
    private ImageButton IBWaterMelon, IBPear, IBStrawberry, IBApple, IBLemon, IBMorkov, IBPotato, IBIcecream;

    public static byte[] foodCounts = new byte[8], foodCosts = {15, 5, 8, 3, 10, 10, 7, 25};
    public static int FoodIndex = 0;

    private int countLinesInDB = 0;
    private String[] MassOfFoodNames = {"Watermelon", "Pear", "Strawberry", "Apple", "Lemon", "Morkov", "Potato", "Icecream"};

    private Context c;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_activity);
        tvCountWatermelon = (TextView)findViewById(R.id.tvCountWatermelon);
        tvCountPear = (TextView)findViewById(R.id.tvCountPear);
        tvCountStrawberry = (TextView)findViewById(R.id.tvCountStrawberry);
        tvCountApple = (TextView)findViewById(R.id.tvCountApple);
        tvCountLemon = (TextView)findViewById(R.id.tvCountLemon);
        tvCountMorkov = (TextView)findViewById(R.id.tvCountMorkov);
        tvCountPotato = (TextView)findViewById(R.id.tvCountPotato);
        tvCountIcecream = (TextView)findViewById(R.id.tvCountIcecream);
        IBplusWaterMelon = (ImageButton)findViewById(R.id.plusWaterMelon);
        IBplusPear = (ImageButton)findViewById(R.id.plusPear);
        IBplusStrawberry = (ImageButton)findViewById(R.id.plusStrawberry);
        IBplusApple = (ImageButton)findViewById(R.id.plusApple);
        IBplusLemon = (ImageButton)findViewById(R.id.plusLemon);
        IBplusMorkov = (ImageButton)findViewById(R.id.plusMorkov);
        IBplusPotato = (ImageButton)findViewById(R.id.plusPotato);
        IBplusIcecream = (ImageButton)findViewById(R.id.plusIcecream);
        IBplusWaterMelon.setOnClickListener(this);
        IBplusPear.setOnClickListener(this);
        IBplusStrawberry.setOnClickListener(this);
        IBplusApple.setOnClickListener(this);
        IBplusLemon.setOnClickListener(this);
        IBplusMorkov.setOnClickListener(this);
        IBplusPotato.setOnClickListener(this);
        IBplusIcecream.setOnClickListener(this);
        IBWaterMelon = (ImageButton)findViewById(R.id.Watermelon);
        IBPear = (ImageButton)findViewById(R.id.Pear);
        IBStrawberry = (ImageButton)findViewById(R.id.Strawberry);
        IBApple = (ImageButton)findViewById(R.id.Apple);
        IBLemon = (ImageButton)findViewById(R.id.Lemon);
        IBMorkov = (ImageButton)findViewById(R.id.Morkov);
        IBPotato = (ImageButton)findViewById(R.id.Potato);
        IBIcecream = (ImageButton)findViewById(R.id.Icecream);
        IBWaterMelon.setOnClickListener(this);
        IBPear.setOnClickListener(this);
        IBStrawberry.setOnClickListener(this);
        IBApple.setOnClickListener(this);
        IBLemon.setOnClickListener(this);
        IBMorkov.setOnClickListener(this);
        IBPotato.setOnClickListener(this);
        IBIcecream.setOnClickListener(this);
        c = getApplicationContext();
        if (countLinesInDB <= Room.petIndex)
            AddNewFoodCountsToDBTable();
        else
            ReadFoodCounts();
        ChangeTVCounts();
    }

    @Override
    public void onClick(View v) {
        SceneView.WhoCalled = 2;
        switch (v.getId()) {
            case R.id.plusWaterMelon: BuyFood(0); break;
            case R.id.plusPear: BuyFood(1);break;
            case R.id.plusStrawberry: BuyFood(2);break;
            case R.id.plusApple: BuyFood(3);break;
            case R.id.plusLemon: BuyFood(4);break;
            case R.id.plusMorkov: BuyFood(5);break;
            case R.id.plusPotato: BuyFood(6);break;
            case R.id.plusIcecream: BuyFood(7);break;
            case R.id.Watermelon: CheckOnZeroCount(0); break;
            case R.id.Pear: CheckOnZeroCount(1); break;
            case R.id.Strawberry: CheckOnZeroCount(2); break;
            case R.id.Apple: CheckOnZeroCount(3); break;
            case R.id.Lemon: CheckOnZeroCount(4); break;
            case R.id.Morkov: CheckOnZeroCount(5); break;
            case R.id.Potato: CheckOnZeroCount(6); break;
            case R.id.Icecream: CheckOnZeroCount(7); break;
        }
        if (intent != null) startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        countLinesInDB = GetCountLinesInDBTable();
        ChangeTVCounts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countLinesInDB >= Room.petIndex)//С какого индекса начинать? - Айнур, help
            UpdateDataBase();
        else
            AddNewFoodCountsToDBTable();
    }

    private void ReadFoodCounts(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c;
        try {
            c = db.query("FoodTable", null, null, null, null, null, null);
        }catch (Exception e){
            dbHelper.close();
            return;
        }
        if (c.moveToFirst()) {
            for (int i = 0;  i < Room.petIndex; i++)//С какого индекса начинать? - Айнур, help
                c.moveToNext();
            for (int i = 0; i < MassOfFoodNames.length; i++)
                foodCounts[i] = (byte)c.getInt(c.getColumnIndex(MassOfFoodNames[i]));
        }else return;
        return;
    }

    private void AddNewFoodCountsToDBTable(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < MassOfFoodNames.length; i++) {
            cv.put(MassOfFoodNames[i], 0);
            foodCounts[i] = 0;
        }
        db.insert("FoodTable", null, cv);
        dbHelper.close();
    }

    private int GetCountLinesInDBTable(){
        int result = 0;
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c;
        try {
            c = db.query("FoodTable", null, null, null, null, null, null);
        }catch (Exception e){
            dbHelper.close();
            return result;
        }
        while (c.moveToNext())
            result++;
        return result;
    }

    private void UpdateDataBase() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < MassOfFoodNames.length; i++)
            cv.put(MassOfFoodNames[i], foodCounts[i]);
        //db.update("FoodTable", cv, "id = ?", new String[]{Room.petIndex+""}); НЕКОРРЕКТНАЯ РАБОТА
        dbHelper.close();
    }

    private void CheckOnZeroCount(int index){
        if (foodCounts[index] > 0) {
            intent = new Intent(this, CaressActivity.class);
            FoodIndex = index+1;
        }else
            ShowToast("Сначала купите еду!", c);
    }

    private void BuyFood(int FoodIndex){
        if (Room.money - foodCosts[FoodIndex] > -1)
            if (CheckFoodOnOverflow(FoodIndex))
                Room.money -= foodCosts[FoodIndex];
            else
                ShowToast("На складе больше не помещается!", this);
        else
            ShowToast("Не хватает монет!", this);

        ChangeTVCounts();
    }

    private void ChangeTVCounts(){
        tvCountWatermelon.setText(""+foodCounts[0]);
        tvCountPear.setText(""+foodCounts[1]);
        tvCountStrawberry.setText(""+foodCounts[2]);
        tvCountApple.setText(""+foodCounts[3]);
        tvCountLemon.setText(""+foodCounts[4]);
        tvCountMorkov.setText(""+foodCounts[5]);
        tvCountPotato.setText(""+foodCounts[6]);
        tvCountIcecream.setText(""+foodCounts[7]);
    }

    private boolean CheckFoodOnOverflow(int foodIndex){
        if(foodCounts[foodIndex]+1 < 101) {
            foodCounts[foodIndex]++;
            return true;
        }
        else
            return false;
    }

    public static void ShowToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
