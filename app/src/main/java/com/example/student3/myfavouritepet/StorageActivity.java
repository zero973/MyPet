package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StorageActivity extends Activity implements View.OnClickListener{

    static class FoodInfo{
        static int FoodId = 0, FoodIndex = 0;
    }

    private TextView tvCountWatermelon, tvCountPear, tvCountStrawberry, tvCountApple, tvCountLemon, tvCountMorkov, tvCountPotato, tvCountIcecream;

    public static byte[] foodCounts = new byte[8], foodCosts = {15, 5, 8, 3, 10, 10, 7, 25};

    private int countLinesInDB = 0;
    private String[] MassOfFoodNames = {"Watermelon", "Pear", "Strawberry", "Apple", "Lemon", "Morkov", "Potato", "Icecream"};
    private int[] foodsImageButtonId = {R.drawable.watermelon, R.drawable.pear, R.drawable.strawberry, R.drawable.apple, R.drawable.lemon, R.drawable.morkovka, R.drawable.potato, R.drawable.icecream};

    private Context c = this;
    private Intent intent = null;

    private void main(){
        setContentView(R.layout.storage_activity);
        tvCountWatermelon = (TextView)findViewById(R.id.tvCountWatermelon);
        tvCountPear = (TextView)findViewById(R.id.tvCountPear);
        tvCountStrawberry = (TextView)findViewById(R.id.tvCountStrawberry);
        tvCountApple = (TextView)findViewById(R.id.tvCountApple);
        tvCountLemon = (TextView)findViewById(R.id.tvCountLemon);
        tvCountMorkov = (TextView)findViewById(R.id.tvCountMorkov);
        tvCountPotato = (TextView)findViewById(R.id.tvCountPotato);
        tvCountIcecream = (TextView)findViewById(R.id.tvCountIcecream);
        ImageButton IBplusWaterMelon = (ImageButton)findViewById(R.id.plusWaterMelon);
        ImageButton IBplusPear = (ImageButton)findViewById(R.id.plusPear);
        ImageButton IBplusStrawberry = (ImageButton)findViewById(R.id.plusStrawberry);
        ImageButton IBplusApple = (ImageButton)findViewById(R.id.plusApple);
        ImageButton IBplusLemon = (ImageButton)findViewById(R.id.plusLemon);
        ImageButton IBplusMorkov = (ImageButton)findViewById(R.id.plusMorkov);
        ImageButton IBplusPotato = (ImageButton)findViewById(R.id.plusPotato);
        ImageButton IBplusIcecream = (ImageButton) findViewById(R.id.plusIcecream);
        IBplusWaterMelon.setOnClickListener(this);
        IBplusPear.setOnClickListener(this);
        IBplusStrawberry.setOnClickListener(this);
        IBplusApple.setOnClickListener(this);
        IBplusLemon.setOnClickListener(this);
        IBplusMorkov.setOnClickListener(this);
        IBplusPotato.setOnClickListener(this);
        IBplusIcecream.setOnClickListener(this);
        ImageButton IBWaterMelon = (ImageButton) findViewById(R.id.Watermelon);
        ImageButton IBPear = (ImageButton) findViewById(R.id.Pear);
        ImageButton IBStrawberry = (ImageButton) findViewById(R.id.Strawberry);
        ImageButton IBApple = (ImageButton)findViewById(R.id.Apple);
        ImageButton IBLemon = (ImageButton)findViewById(R.id.Lemon);
        ImageButton IBMorkov = (ImageButton)findViewById(R.id.Morkov);
        ImageButton IBPotato = (ImageButton)findViewById(R.id.Potato);
        ImageButton IBIcecream = (ImageButton)findViewById(R.id.Icecream);
        IBWaterMelon.setOnClickListener(this);
        IBPear.setOnClickListener(this);
        IBStrawberry.setOnClickListener(this);
        IBApple.setOnClickListener(this);
        IBLemon.setOnClickListener(this);
        IBMorkov.setOnClickListener(this);
        IBPotato.setOnClickListener(this);
        IBIcecream.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
        countLinesInDB = GetCountLinesInDBTable();
        if (countLinesInDB < Pet.getPetIndex())
            AddNewFoodCountsToDBTable();
        else
            ReadFoodCounts();
        ChangeTVCounts();
    }

    @Override
    public void onClick(View v) {
        intent = null;
        SceneView.WhoCalled = SceneView.EWhoCalled.Food;
        FindElement(v.getId());
        switch (v.getId()) {
            case R.id.plusWaterMelon: BuyFood(0); break;
            case R.id.plusPear: BuyFood(1);break;
            case R.id.plusStrawberry: BuyFood(2);break;
            case R.id.plusApple: BuyFood(3);break;
            case R.id.plusLemon: BuyFood(4);break;
            case R.id.plusMorkov: BuyFood(5);break;
            case R.id.plusPotato: BuyFood(6); break;
            case R.id.plusIcecream: BuyFood(7); break;
            case R.id.Watermelon: CheckOnZeroCount(0); break;
            case R.id.Pear: CheckOnZeroCount(1); break;
            case R.id.Strawberry: CheckOnZeroCount(2); break;
            case R.id.Apple: CheckOnZeroCount(3); break;
            case R.id.Lemon: CheckOnZeroCount(4); break;
            case R.id.Morkov: CheckOnZeroCount(5); break;
            case R.id.Potato: CheckOnZeroCount(6); break;
            case R.id.Icecream: CheckOnZeroCount(7); break;
        }
        SetAction();
        if (intent != null) startActivity(intent);
    }

    private void FindElement(int elementId){

    }

    private void SetAction(){

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
        if (countLinesInDB >= Pet.getPetIndex())
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
            for (int i = 1;  i < Pet.getPetIndex(); i++)
                c.moveToNext();
            for (int i = 0; i < MassOfFoodNames.length; i++)
                foodCounts[i] = (byte) c.getInt(c.getColumnIndex(MassOfFoodNames[i]));
        }
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
        db.update("FoodTable", cv, "id = ?", new String[]{Pet.getPetIndex()+""});
        dbHelper.close();
    }

    private void CheckOnZeroCount(int index){
        if (foodCounts[index] > 0) {
            intent = new Intent(this, CaressActivity.class);
            FoodInfo.FoodId = foodsImageButtonId[index];
            FoodInfo.FoodIndex = index;
        }else
            ShowToast("Сначала купите еду!", c);
    }

    private void BuyFood(int FoodIndex){
        if (Pet.getMoney() - foodCosts[FoodIndex] > -1)
            if (CheckFoodOnOverflow(FoodIndex))
                Pet.setMoney(Pet.getMoney() - foodCosts[FoodIndex]);
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
