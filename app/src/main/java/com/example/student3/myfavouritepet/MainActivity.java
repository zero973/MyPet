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

    String[] GenderMass = {"Мужской", "Женский"}, roomColors = {"Синий", "Розовый"};
    Spinner SpinnerMale, SpinnerRoomColor;
    EditText EditTextName;
    Button butGoGame;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Адаптер для спиннера с полом
        ArrayAdapter<String> adapterMale = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GenderMass);
        adapterMale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптер спиннера с цветом комнаты
        ArrayAdapter<String> adapterRoomColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomColors);
        adapterRoomColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditTextName = (EditText) findViewById(R.id.editTextName);
        butGoGame = (Button) findViewById(R.id.buttonGoGame);
        SpinnerMale = (Spinner) findViewById(R.id.choosedMale);
        SpinnerMale.setAdapter(adapterMale);
        SpinnerRoomColor = (Spinner) findViewById(R.id.choosedRoomColor);
        SpinnerRoomColor.setAdapter(adapterRoomColor);

        sPref = getPreferences(MODE_PRIVATE);
        name = sPref.getString("PetName", "");
        Log.d("Имя питомца", name);
        try {
            if (!name.equals("")) {
                SendData();
                Log.d("Перехрод в", "Комнату");
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
                    String text = EditTextName.getText().toString();
                    if(text.compareTo("") == 0){
                        Log.d("Проблема", "Имя питомца не введено");
                        Toast.makeText(getApplicationContext(), "Введите имя питомца!", Toast.LENGTH_LONG);
                    }else {
                        SendData();
                        Intent intent = new Intent(MainActivity.this, Room.class);
                        startActivity(intent);
                    }
        }});
    }

    void SendData(){
        Log.d("SendData","Отправляю");
        Intent intent = new Intent(MainActivity.this, Room.class);
        intent.putExtra("petName", name);
        intent.putExtra("Gender", GenderMass[SpinnerMale.getSelectedItemPosition()]);
        intent.putExtra("RoomColor", roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
        Log.d("SendData","Отправленный цвет: " + roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
        startActivity(intent);
        Log.d("SendData","Выполнено");
    }
}