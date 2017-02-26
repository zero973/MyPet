package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Room extends Activity{

    String name = "250801", kind, roomColor;
    //int money;
    RelativeLayout room;

    ImageButton IBFood, IBHealth, IBAchievement, IBPet;
    TextView tvPetName;
    Button changePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        IBPet = (ImageButton)findViewById(R.id.imageButtonPet);
        IBFood = (ImageButton)findViewById(R.id.imageButtonFood);
        IBHealth = (ImageButton)findViewById(R.id.imageButtonHealth);
        IBAchievement = (ImageButton)findViewById(R.id.imageButtonAchievement);
        changePet = (Button)findViewById(R.id.buttonChangePet);
        tvPetName = (TextView)findViewById(R.id.textViewNamePet);

        readFile("PetInfo");
        if (name.equals("250801")) {
            Intent intent = new Intent(Room.this, MainActivity.class);
            startActivity(intent);
        }

        changePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this, MainActivity.class);
                startActivity(intent);
            }
        });
        IBAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this, GameSchool.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        readFile("PetInfo");
    }

    public void readFile(String fileName) {
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
        }else if (roomColor.equals("Оранжевый")) {
            room.setBackgroundColor(getResources().getColor(R.color.roomColorOrange));
        }

        if(kind.equals("Собака")){
            IBPet.setBackgroundResource(R.drawable.petdog);
        }else if (kind.equals("Кошка")){
            IBPet.setBackgroundResource(R.drawable.petcat);
        }else if (kind.equals("Заяц")){
            IBPet.setBackgroundResource(R.drawable.petrabbit);
        }else if (kind.equals("Черепаха")){
            IBPet.setBackgroundResource(R.drawable.petturtle);
        }else if (kind.equals("Попугай")){
            IBPet.setBackgroundResource(R.drawable.petparrot);
        }
    }

    /*public void onclick(View v) {
        Log.w("onClick",v.getId()+"");
        Intent intent;
        switch (v.getId()) {
            case R.id.imageButtonPet:

                break;
            case R.id.imageButtonFood:

                break;
            case R.id.imageButtonHealth:

                break;
            case R.id.imageButtonAchievement:

                break;
            case R.id.buttonChangePet:

                break;
        }
    }*/
}
