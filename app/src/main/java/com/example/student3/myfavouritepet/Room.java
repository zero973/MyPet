package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Room extends Activity implements View.OnClickListener{

    public static String name = "250801", kind, roomColor;
    public static int money = 100;
    public static int petIndex = -1;
    RelativeLayout room;

    ImageButton IBPet, IBFood, IBHealth, IBAchievement;
    Button btnChangePet;
    TextView tvPetName, tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        IBPet = (ImageButton)findViewById(R.id.imageButtonPet);
        IBPet.setOnClickListener(this);
        IBFood = (ImageButton)findViewById(R.id.imageButtonFood);
        IBFood.setOnClickListener(this);
        IBHealth = (ImageButton)findViewById(R.id.imageButtonHealth);
        IBHealth.setOnClickListener(this);
        IBAchievement = (ImageButton)findViewById(R.id.imageButtonAchievement);
        IBAchievement.setOnClickListener(this);
        btnChangePet = (Button)findViewById(R.id.buttonChangePet);
        btnChangePet.setOnClickListener(this);
        tvPetName = (TextView)findViewById(R.id.textViewNamePet);
        tvMoney = (TextView)findViewById(R.id.textViewMoney);

        readFile("PetInfo");
        if (name.equals("250801")) {
            Intent intent = new Intent(Room.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (petIndex == -1) {
            GetLastPetIndex("PetIndex");
            MainActivity mainActivity = new MainActivity();
            if (mainActivity.CheckDataBaseAndFillLists())
                money = MainActivity.moneyList.get(petIndex);
        }
        else
            WriteLastPetIndex("PetIndex");
        UpdateDataBase();
        readFile("PetInfo");
        tvMoney.setText("Монет: " + money);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonChangePet: intent = new Intent(Room.this, MainActivity.class); break;
            case R.id.imageButtonAchievement: intent = new Intent(Room.this, School.class); break;
            case R.id.imageButtonPet: break;
            case R.id.imageButtonFood: intent = new Intent(Room.this, StorageActivity.class); break;
            case R.id.imageButtonHealth: intent = new Intent(Room.this, HeartActivity.class); break;
        }
        try {//Доделай кнопки, потом уберёшь этот блок
            startActivity(intent);
        }catch (Exception e){

        }
    }

    private void RecolorRoom(){
        room = (RelativeLayout)findViewById(R.id.room);
        switch (roomColor){
            case "Синяя": room.setBackgroundResource(R.drawable.blueroom);break;
            case "Коричневая": room.setBackgroundResource(R.drawable.brownroom);break;
            case "Голубая": room.setBackgroundResource(R.drawable.blue_whiteroom);break;
            case "Жёлтая": room.setBackgroundResource(R.drawable.yellowroom);break;
            case "Алая": room.setBackgroundResource(R.drawable.alayaroom);break;
        }

        switch (kind) {
            case "Собака":
                IBPet.setBackgroundResource(R.drawable.petdog);
                break;
            case "Кошка":
                IBPet.setBackgroundResource(R.drawable.petcat);
                break;
            case "Заяц":
                IBPet.setBackgroundResource(R.drawable.petrabbit);
                break;
            case "Черепаха":
                IBPet.setBackgroundResource(R.drawable.petturtle);
                break;
            case "Попугай":
                IBPet.setBackgroundResource(R.drawable.petparrot);
                break;
        }
    }

    private void readFile(String fileName) {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String str = br.readLine();
            // читаем содержимое
            if (str != null) {
                name = str;
                kind = br.readLine();
                roomColor = br.readLine();
                RecolorRoom();
                tvPetName.setText(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GetLastPetIndex(String fileName){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            petIndex = Integer.valueOf(br.readLine());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteLastPetIndex(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.valueOf(petIndex));
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void UpdateDataBase(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("PetType", kind);
        cv.put("RoomColor", roomColor);
        cv.put("money", money);
        db.update("myDataTable", cv, "id = ?", new String[]{ String.valueOf(petIndex) });
    }

}
