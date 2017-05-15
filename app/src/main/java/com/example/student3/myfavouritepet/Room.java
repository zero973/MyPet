package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.student3.myfavouritepet.MainActivity.moneyList;
import static com.example.student3.myfavouritepet.MainActivity.namesOldPets;
import static com.example.student3.myfavouritepet.MainActivity.oldPetTypes;
import static com.example.student3.myfavouritepet.MainActivity.oldRoomColors;

public class Room extends Activity implements View.OnClickListener {

    public static String name = "250801250801", kind, roomColor;
    public static int money = 100, petIndex = -1, satiety = 50, caress = 50;
    private int countDeadMessage = 0;
    private boolean IsPetSatiety = true, IsPerCaress = true;
    private static final int NOTIFY_ID = 101;

    private Timer timer;
    private MyTimerTask timerTask;

    RelativeLayout room;
    ImageButton IBPet, IBFood, IBHealth, IBAchievement;
    Button btnChangePet;
    TextView tvPetName, tvMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        IBPet = (ImageButton) findViewById(R.id.imageButtonPet);
        IBPet.setOnClickListener(this);
        IBFood = (ImageButton) findViewById(R.id.imageButtonFood);
        IBFood.setOnClickListener(this);
        IBHealth = (ImageButton) findViewById(R.id.imageButtonHealth);
        IBHealth.setOnClickListener(this);
        IBAchievement = (ImageButton) findViewById(R.id.imageButtonAchievement);
        IBAchievement.setOnClickListener(this);
        btnChangePet = (Button) findViewById(R.id.buttonChangePet);
        btnChangePet.setOnClickListener(this);
        tvPetName = (TextView) findViewById(R.id.textViewNamePet);
        tvMoney = (TextView) findViewById(R.id.textViewMoney);

        readFile("PetInfo");
        if (name.equals("250801250801")) {
            Intent intent = new Intent(Room.this, MainActivity.class);
            startActivity(intent);
        } else {
            GetLastPetIndex("PetIndex");
            MoneyReadWrite(Opertion.Read, "PetMoney");
            CheckStatus();
            StartTimer();
            //Тост не выводит
            Toast.makeText(getApplicationContext(), "Сытость: " + satiety + " Настроение: " + caress, LENGTH_SHORT);
        }
    }

    //public static boolean IsIWentFromMainActivity = false;

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "Debug: invoked onPause()", LENGTH_SHORT);
        UpdateDataBase();
        WriteLastPetIndex("PetIndex");
        MoneyReadWrite(Opertion.Write, "PetMoney");
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMoney.setText("Монет: " + money);
        readFile("PetInfo");
        CheckStatus();
    }

    @Override
    public void onClick(View v) {
        //Не выводит!!!
        Toast.makeText(getApplicationContext(), "Сытость: " + satiety + " Настроение: " + caress, LENGTH_SHORT);
        //Не выводит!!!
        boolean IsNeedReplaceActivity = true;
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonChangePet:
                intent = new Intent(Room.this, MainActivity.class);
                break;
            case R.id.imageButtonAchievement:
                intent = new Intent(Room.this, School.class);
                break;
            case R.id.imageButtonPet:
                Toast.makeText(getApplicationContext(), "Сытость: " + satiety + " Настроение: " + caress, LENGTH_SHORT);
                Log.e("Инфа", "Сытость: " + satiety + " Настроение: " + caress);
                IsNeedReplaceActivity = false;
                break;
            case R.id.imageButtonFood:
                intent = new Intent(Room.this, StorageActivity.class);
                break;
            case R.id.imageButtonHealth:
                intent = new Intent(Room.this, HeartActivity.class);
                break;
        }
        if (IsNeedReplaceActivity)
            startActivity(intent);
    }

    private void RecolorRoom() {
        room = (RelativeLayout) findViewById(R.id.room);
        switch (roomColor) {
            case "Синяя":
                room.setBackgroundResource(R.drawable.blueroom);
                break;
            case "Коричневая":
                room.setBackgroundResource(R.drawable.brownroom);
                break;
            case "Голубая":
                room.setBackgroundResource(R.drawable.blue_whiteroom);
                break;
            case "Жёлтая":
                room.setBackgroundResource(R.drawable.yellowroom);
                break;
            case "Алая":
                room.setBackgroundResource(R.drawable.alayaroom);
                break;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum Opertion {
        Write, Read
    }

    private void MoneyReadWrite(Opertion opertion, String fileName) {
        switch (opertion) {
            case Write:
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
                    bw.write(String.valueOf(money));
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Read:
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
                    money = Integer.valueOf(br.readLine());
                    tvMoney.setText("Монет: " + money);
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void GetLastPetIndex(String fileName){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            petIndex = Integer.valueOf(br.readLine());
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void WriteLastPetIndex(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.valueOf(petIndex));
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void UpdateDataBase() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("PetType", kind);
        cv.put("RoomColor", roomColor);
        cv.put("money", money);
        //db.update("myDataTable", cv, "id = ?", new String[]{petIndex+""}); НЕКОРРЕКТНО РАБОТАЕТ
        dbHelper.close();
    }

    private void CheckStatus(){
        if (satiety >= 11)
            IsPetSatiety = true;
        else {
            IsPetSatiety = false;
            SendNotification("Мой любимый питомец", "Я хочу кушать!", R.drawable.home);
        }
        if (caress >= 100) {
            caress = 100;//Пользователь может гладить питомца без ограничеий, поэтому делаю так
            IsPerCaress = true;
        }
        if (caress < 12){
            IsPerCaress = false;
            SendNotification("Мой любимый питомец", "Поиграй со мной!", R.drawable.home);
        }
    }

    private String GetPetStatus(){
        return null;
    }

    private void StartTimer(){
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.schedule(timerTask, 0, 1000*60);
    }

    private void PetDead(){
        SendNotification("Ты плохой хозяин", "Я умер", R.drawable.skull);
    }

    private void SendNotification(String title, String text, int icon){
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, Room.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text);

        Notification notification = builder.getNotification(); // до API 16
        //Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    private class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            if (countDeadMessage > 5) {
                PetDead();
            }else {
                if (satiety-1 > 10 && caress-2 > 10) {
                    satiety -= 1;
                    caress -= 2;
                    CheckStatus();
                }else
                    countDeadMessage++;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
