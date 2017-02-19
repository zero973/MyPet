package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Room extends Activity{

    public String name, gender, roomColor;
    RelativeLayout room;
    SharedPreferences sPref;

    ImageButton IBFood, IBHealth, IBAchievement;
    TextView tvPetName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        loadData();
        //Покраска комнаты
        RecolorRoom();
        //Обработка нажатий на индикаторы
        IBFood = (ImageButton)findViewById(R.id.imageButtonFood);
        IBHealth = (ImageButton)findViewById(R.id.imageButtonHealth);
        IBAchievement = (ImageButton)findViewById(R.id.imageButtonAchievement);
        tvPetName = (TextView)findViewById(R.id.textViewNamePet);
        Log.d("В комнате","Имя питомца: "+name);
        tvPetName.setText("Привет, "+name+"!");

        IBFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        IBHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        IBAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Room.this, GameSchool.class);
                startActivity(intent);
            }
        });
    }

    void loadDataOnsPref() {
        sPref = getPreferences(MODE_PRIVATE);
        name = sPref.getString("PetName", "");
        gender = sPref.getString("Gender", "");
        roomColor = sPref.getString("RoomColor", "");
    }

    void loadData(){
        Log.d("В комнате", "загружаю данные");
        Intent intent = getIntent();
        name = intent.getStringExtra("petName");
        gender = intent.getStringExtra("Gender");
        roomColor = intent.getStringExtra("RoomColor");
        Log.d("Цвет комнаты: ", roomColor);
        Log.d("В комнате", "загрузил данные");
    }

    void saveData() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("PetName", name);
        ed.putString("Gender", gender);
        ed.putString("RoomColor", roomColor);
        ed.commit();
    }

    void RecolorRoom(){
        room = (RelativeLayout) findViewById(R.id.room);
        if (roomColor.equals("Синий")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorBlue));
        }else if(roomColor.equals("Розовый")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorPink));
        }else {
            Toast.makeText(this ,"Не удалось загрузить цвет", Toast.LENGTH_SHORT).show();
        }
    }
}
