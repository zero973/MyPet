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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends Activity {

    public String name;
    String[] KindsMass = {"Собака", "Кошка", "Попугай", "Заяц", "Черепаха"}, roomColors = {"Синяя", "Коричневая", "Голубая", "Жёлтая", "Алая"};
    static ArrayList<String> namesOldPets, oldPetTypes, oldRoomColors;
    public static ArrayList<Integer> moneyList = new ArrayList<>();

    Spinner SpinnerKind, SpinnerRoomColor, SpinnerOldKinds;
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
        //Адаптер спиннера с прошлыми питомцами
        ArrayAdapter<String> adapterOldKinds = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namesOldPets);
        adapterRoomColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditTextName = (EditText) findViewById(R.id.editTextName);
        butGoGame = (Button) findViewById(R.id.buttonGoGame);
        SpinnerKind = (Spinner) findViewById(R.id.choosedKindAnimal);
        SpinnerKind.setAdapter(adapterKind);
        SpinnerRoomColor = (Spinner) findViewById(R.id.choosedRoomColor);
        SpinnerRoomColor.setAdapter(adapterRoomColor);
        SpinnerOldKinds = (Spinner)findViewById(R.id.choosedOldData);

        SpinnerOldKinds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    writeFile("PetInfo", namesOldPets.get(position), oldPetTypes.get(position-1), oldRoomColors.get(position-1));
                    Room.petIndex = position;
                    Room.money = moneyList.get(position-1);
                    Room.IsIWentFromMainActivity = true;
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (namesOldPets.size() == 1)
            SpinnerOldKinds.setVisibility(View.INVISIBLE);
        else {
            SpinnerOldKinds.setVisibility(View.VISIBLE);
            SpinnerOldKinds.setAdapter(adapterOldKinds);
        }

        butGoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = EditTextName.getText().toString();
                if (name.compareTo("") == 0)
                    name = "Иван";
                writeFile("PetInfo", name, KindsMass[SpinnerKind.getSelectedItemPosition()],
                        roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
                PutData();
                if (moneyList.isEmpty())
                    Room.petIndex = 0;
                else
                    Room.petIndex = moneyList.size();
                Room.IsIWentFromMainActivity = true;
                finish();
            }
        });
        SpinnerOldKinds.setSelected(true);
    }

    private void writeFile(String fileName, String petName, String kind, String color) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.format("%s\n%s\n%s\n", petName, kind, color));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void PutData(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // подготовим данные для вставки в виде пар:наименование столбца - значение
        cv.put("name", name);
        cv.put("PetType", KindsMass[SpinnerKind.getSelectedItemPosition()]);
        cv.put("RoomColor", roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
        cv.put("money", 100);
        db.insert("myDataTable", null, cv);
        dbHelper.close();
    }
}