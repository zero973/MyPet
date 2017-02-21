package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Room extends Activity{

    public String name, kind, roomColor;
    RelativeLayout room;
    SharedPreferences sPref;

    ImageButton IBFood, IBHealth, IBAchievement, IBPet;
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
        IBPet = (ImageButton)findViewById(R.id.imageButtonPet);
        tvPetName = (TextView)findViewById(R.id.textViewNamePet);
        tvPetName.setText(name);

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

    void loadData(){
        name = Helper.Name;
        kind = Helper.Kind;
        roomColor = Helper.Color;
    }

    void saveData() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("PetName", name);
        ed.putString("Kind", kind);
        ed.putString("RoomColor", roomColor);
        ed.commit();
    }

    void RecolorRoom(){
        room = (RelativeLayout) findViewById(R.id.room);
        if (roomColor.equals("Синий")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorBlue));
        }else if(roomColor.equals("Розовый")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorPink));
        }else if (roomColor.equals("Красный")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorRed));
        }else if (roomColor.equals("Зелёный")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorGreen));
        }else if (roomColor.equals("Жёлтый")){
            room.setBackgroundColor(getResources().getColor(R.color.MathBut));
        }else if (roomColor.equals("Коричневый")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorBrown));
        }else if (roomColor.equals("Оранжевый")){
            room.setBackgroundColor(getResources().getColor(R.color.roomColorOrange));
        }else{
            room.setBackgroundColor(getResources().getColor(R.color.roomColorBlue));
        }

        /*if(kind.equals("Собака")){
            IBPet.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.petdog));
        }else if (kind.equals("Кошка")){
            IBPet.setBackgroundResource(R.drawable.petcat);  Caused by: java.lang.NullPointerException
        }else {
            IBPet.setBackgroundResource(R.drawable.petparrot);
        }*/
    }
}
