package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.ContentValues;
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

    public String name, PetType, RoomColor;
    String[] KindsMass = {"Собака", "Кошка", "Попугай", "Заяц", "Черепаха"}, roomColors = {"Синяя", "Коричневая", "Голубая", "Жёлтая", "Алая"};
    ArrayList<String> namesOldPets = new ArrayList<String>(), oldPetTypes = new ArrayList<String>(), oldRoomColors = new ArrayList<String>();

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
                    writeFile("PetInfo", namesOldPets.get(position), oldPetTypes.get(position), oldRoomColors.get(position));
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!CheckDataBase()) {
            Log.e("CheckDataBase()", "Дата база пуста");
            SpinnerOldKinds.setVisibility(View.INVISIBLE);
        }
        else {
            SpinnerOldKinds.setVisibility(View.VISIBLE);
            Log.e("CheckDataBase()", "В дата базе есть данные");
            SpinnerOldKinds.setAdapter(adapterOldKinds);
            SpinnerOldKinds.setSelected(false);
        }

        butGoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = EditTextName.getText().toString();
                if (name.compareTo("") == 0) {
                    name = "Иван";
                    writeFile("PetInfo", name, KindsMass[SpinnerKind.getSelectedItemPosition()],
                            roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
                    PutData();
                    finish();
                } else {
                    writeFile("PetInfo", name, KindsMass[SpinnerKind.getSelectedItemPosition()],
                            roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
                    PutData();
                    finish();
                }
            }
        });
        SpinnerOldKinds.setSelected(true);
    }

    void writeFile(String fileName, String petName, String kind, String color) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.format("%s\n%s\n%s\n", petName, kind, color));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean CheckDataBase(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.query("myDataTable", null, null, null, null, null, null);
        }catch (Exception e){return false;}
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int nameColIndex = c.getColumnIndex("name");
            int PetTypeColIndex = c.getColumnIndex("PetType");
            int RoomColorColIndex = c.getColumnIndex("RoomColor");
            do {// получаем значения по номерам столбцов и пишем все в лог
                namesOldPets.add(c.getString(nameColIndex));
                oldPetTypes.add(c.getString(PetTypeColIndex));
                oldRoomColors.add(c.getString(RoomColorColIndex));
                Log.d("Работа с БД","name = " + c.getString(nameColIndex) + ", PetType = " + c.getString(PetTypeColIndex)
                        + ", RoomColor = " + c.getString(RoomColorColIndex));
            } while (c.moveToNext());
        }else return false;
        c.close();
        dbHelper.close();
        return true;
    }

    void PutData(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // подготовим данные для вставки в виде пар:наименование столбца - значение
        cv.put("name", name);
        cv.put("PetType", KindsMass[SpinnerKind.getSelectedItemPosition()]);
        cv.put("RoomColor", roomColors[SpinnerRoomColor.getSelectedItemPosition()]);
        db.insert("myDataTable", null, cv);
        dbHelper.close();
    }
}