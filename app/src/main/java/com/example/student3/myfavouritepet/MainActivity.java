package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    public String name;

    String[] KindsMass = {"Собака", "Кошка", "Попугай", "Заяц", "Черепаха"}, roomColors = {"Синяя", "Коричневая", "Голубая", "Жёлтая", "Алая"};
    Spinner SpinnerKind, SpinnerRoomColor;
    EditText EditTextName;
    Button butGoGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //Адаптер для спиннера с полом
        ArrayAdapter<String> adapterKind = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, KindsMass);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Адаптер спиннера с цветом комнаты
        ArrayAdapter<String> adapterRoomColor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomColors);
        adapterRoomColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditTextName = (EditText) findViewById(R.id.editTextName);
        butGoGame = (Button) findViewById(R.id.buttonGoGame);
        SpinnerKind = (Spinner) findViewById(R.id.choosedKindAnimal);
        SpinnerKind.setAdapter(adapterKind);
        SpinnerRoomColor = (Spinner) findViewById(R.id.choosedRoomColor);
        SpinnerRoomColor.setAdapter(adapterRoomColor);

        butGoGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = EditTextName.getText().toString();
                    if(name.compareTo("") == 0){
                        name = "Иван";
                        writeFile("PetInfo", name, KindsMass[SpinnerKind.getSelectedItemPosition()], roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
                        finish();
                    }else {
                        writeFile("PetInfo", name, KindsMass[SpinnerKind.getSelectedItemPosition()], roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
                        finish();
                    }
                }
        });
    }

    void writeFile(String fileName, String petName, String kind, String color) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            // пишем данные
            bw.write(String.format("%s\n%s\n%s\n", petName, kind, color));
            // закрываем поток
            bw.close();
            Log.d("Успех", "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}