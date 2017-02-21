package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

    public String name;

    String[] KindsMass = {"Собака", "Кошка", "Попугай"}, roomColors = {"Синий", "Розовый", "Зелёный", "Красный", "Жёлтый", "Коричневый", "Оранжевый"};
    Spinner SpinnerKind, SpinnerRoomColor;
    EditText EditTextName;
    Button butGoGame;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Адаптер для спиннера с полом
        ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, KindsMass);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптер спиннера с цветом комнаты
        ArrayAdapter<String> adapterRoomColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomColors);
        adapterRoomColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditTextName = (EditText) findViewById(R.id.editTextName);
        butGoGame = (Button) findViewById(R.id.buttonGoGame);
        SpinnerKind = (Spinner) findViewById(R.id.choosedKindAnimal);
        SpinnerKind.setAdapter(adapterKind);
        SpinnerRoomColor = (Spinner) findViewById(R.id.choosedRoomColor);
        SpinnerRoomColor.setAdapter(adapterRoomColor);

        try{
            sPref = getPreferences(MODE_PRIVATE);
            name = sPref.getString("PetName", "");
            Log.d("Имя питомца", name);
        }catch (Exception e){}
        try {
            if (!name.equals("")) {
                SendData();
                Intent intent = new Intent(MainActivity.this, Room.class);
                startActivity(intent);
            }
        }
        catch (Exception e){
            Log.e("Ошибка", e.toString());
        }

        butGoGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = EditTextName.getText().toString();
                    if(name.compareTo("") == 0){
                        Toast.makeText(getApplicationContext(), "Введите имя питомца!", Toast.LENGTH_LONG);
                    }else {
                        saveData();
                        SendData();
                        Intent intent = new Intent(MainActivity.this, Room.class);
                        startActivity(intent);
                    }
                }
        });
    }

    void SendData(){
        Helper.Name = name;
        Helper.Color = roomColors[SpinnerRoomColor.getSelectedItemPosition()];
        Helper.Kind = KindsMass[SpinnerKind.getSelectedItemPosition()];
    }

    void saveData() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("PetName", name);
        ed.putString("Kind", KindsMass[SpinnerKind.getSelectedItemPosition()]);
        ed.putString("RoomColor", roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
        ed.commit();
    }
}