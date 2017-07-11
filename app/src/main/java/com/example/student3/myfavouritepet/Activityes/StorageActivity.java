package com.example.student3.myfavouritepet.Activityes;

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

import com.example.student3.myfavouritepet.HelpClasses.Animation.PrepareForAnimation;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;
import com.example.student3.myfavouritepet.HelpClasses.Food.Apple;
import com.example.student3.myfavouritepet.HelpClasses.Food.BaseFood;
import com.example.student3.myfavouritepet.HelpClasses.Food.Carrot;
import com.example.student3.myfavouritepet.HelpClasses.Food.Icecream;
import com.example.student3.myfavouritepet.HelpClasses.Food.Lemon;
import com.example.student3.myfavouritepet.HelpClasses.Food.Pear;
import com.example.student3.myfavouritepet.HelpClasses.Food.Potato;
import com.example.student3.myfavouritepet.HelpClasses.Food.Strawberry;
import com.example.student3.myfavouritepet.HelpClasses.Food.Watermelon;
import com.example.student3.myfavouritepet.HelpClasses.Service.DBHelper;
import com.example.student3.myfavouritepet.HelpClasses.Service.Pet;
import com.example.student3.myfavouritepet.HelpClasses.States.BaseState;
import com.example.student3.myfavouritepet.HelpClasses.States.Feed;
import com.example.student3.myfavouritepet.R;

import java.util.ArrayList;

public class StorageActivity extends Activity implements View.OnClickListener{

    private TextView tvCountWatermelon, tvCountPear, tvCountStrawberry, tvCountApple, tvCountLemon, tvCountMorkov, tvCountPotato, tvCountIcecream;

    private ArrayList<BaseFood> foodList = new ArrayList<>();

    private int countLinesInDB = 0;

    private Context cnt = this;
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

        FillFoodList();
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
        switch (v.getId()) {
            case R.id.plusWaterMelon: BuyFood(foodList.get(0)); break;
            case R.id.plusPear: BuyFood(foodList.get(1));break;
            case R.id.plusStrawberry: BuyFood(foodList.get(2));break;
            case R.id.plusApple: BuyFood(foodList.get(3));break;
            case R.id.plusLemon: BuyFood(foodList.get(4));break;
            case R.id.plusMorkov: BuyFood(foodList.get(5));break;
            case R.id.plusPotato: BuyFood(foodList.get(6)); break;
            case R.id.plusIcecream: BuyFood(foodList.get(7)); break;
            case R.id.Watermelon: CheckOnZeroCount(foodList.get(0)); break;
            case R.id.Pear: CheckOnZeroCount(foodList.get(1)); break;
            case R.id.Strawberry: CheckOnZeroCount(foodList.get(2)); break;
            case R.id.Apple: CheckOnZeroCount(foodList.get(3)); break;
            case R.id.Lemon: CheckOnZeroCount(foodList.get(4)); break;
            case R.id.Morkov: CheckOnZeroCount(foodList.get(5)); break;
            case R.id.Potato:  CheckOnZeroCount(foodList.get(6)); break;
            case R.id.Icecream: CheckOnZeroCount(foodList.get(7)); break;
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
        if (countLinesInDB >= Pet.getPetIndex())
            UpdateDataBase();
        else
            AddNewFoodCountsToDBTable();
    }

    private void FillFoodList(){
        foodList.add(new Watermelon());
        foodList.add(new Pear());
        foodList.add(new Strawberry());
        foodList.add(new Apple());
        foodList.add(new Lemon());
        foodList.add(new Carrot());
        foodList.add(new Potato());
        foodList.add(new Icecream());
    }

    private void ReadFoodCounts(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c;
        BaseFood food;
        try {
            c = db.query("FoodTable", null, null, null, null, null, null);
        }catch (Exception e){
            dbHelper.close();
            return;
        }
        if (c.moveToFirst()) {
            for (int i = 1; i < Pet.getPetIndex(); i++)
                c.moveToNext();
            for (int i = 0; i < foodList.size(); i++) {
                food = foodList.get(i);
                try {
                    food.setCurrentCount((byte) c.getInt(c.getColumnIndex(foodList.get(i).getFoodName())));
                } catch (ZeroCountFoodException e) {}
                foodList.set(i, food);
            }
        }
    }

    private void AddNewFoodCountsToDBTable(){
        DBHelper dbHelper = new DBHelper(this);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        BaseFood food;
        for (int i = 0; i < foodList.size(); i++) {
            cv.put(foodList.get(i).getFoodName(), 0);
            food = foodList.get(i);
            try {
                food.setCurrentCount((byte) 0);
            } catch (ZeroCountFoodException e) {}
            foodList.set(i, food);
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
        for (int i = 0; i < foodList.size(); i++)
            cv.put(foodList.get(i).getFoodName(), foodList.get(i).getCurrentCount());
        db.update("FoodTable", cv, "id = ?", new String[]{Pet.getPetIndex()+""});
        dbHelper.close();
    }

    private void CheckOnZeroCount(BaseFood food){
        if (food.getCurrentCount() > 0) {
            PrepareForAnimation PFA = new PrepareForAnimation();
            SetFood(food);
            PFA.setState(Room.states[0]);
            intent = new Intent(this, PFA.getClass());
        }
        else
            ShowToast("Сначала купите еду!", cnt);
    }

    private void BuyFood(BaseFood food){
        if (Pet.getMoney() - food.getCost() > -1)
            if (CheckFoodOnOverflow(food)) {
                Pet.setMoney(Pet.getMoney() - food.getCost());
                ChangeCountFoodInList(food);
            }
            else
                ShowToast("На складе больше не помещается!", this);
        else
            ShowToast("Не хватает монет!", this);
        ChangeTVCounts();
    }

    private void ChangeCountFoodInList(BaseFood food){
        int index = foodList.indexOf(food);
        try {
            food.setCurrentCount((byte) (food.getCurrentCount()+1));
            foodList.set(index, food);
        } catch (ZeroCountFoodException e) {}
    }

    private void ChangeTVCounts(){
        tvCountWatermelon.setText(""+foodList.get(0).getCurrentCount());
        tvCountPear.setText(""+foodList.get(1).getCurrentCount());
        tvCountStrawberry.setText(""+foodList.get(2).getCurrentCount());
        tvCountApple.setText(""+foodList.get(3).getCurrentCount());
        tvCountLemon.setText(""+foodList.get(4).getCurrentCount());
        tvCountMorkov.setText(""+foodList.get(5).getCurrentCount());
        tvCountPotato.setText(""+foodList.get(6).getCurrentCount());
        tvCountIcecream.setText(""+foodList.get(7).getCurrentCount());
    }

    private void SetFood(BaseFood food){
        Room.states[0].setFood(food);
    }

    private boolean CheckFoodOnOverflow(BaseFood food){
        if(food.getCurrentCount()+1 < 101)
            return true;
        else
            return false;
    }

    public static void ShowToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
