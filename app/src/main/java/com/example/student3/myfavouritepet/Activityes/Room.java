package com.example.student3.myfavouritepet.Activityes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.TiredException;
import com.example.student3.myfavouritepet.HelpClasses.Food.Apple;
import com.example.student3.myfavouritepet.HelpClasses.Food.Icecream;
import com.example.student3.myfavouritepet.HelpClasses.Service.DBHelper;
import com.example.student3.myfavouritepet.HelpClasses.Service.Pet;
import com.example.student3.myfavouritepet.HelpClasses.States.BaseState;
import com.example.student3.myfavouritepet.HelpClasses.States.Solicitude;
import com.example.student3.myfavouritepet.HelpClasses.States.Feed;
import com.example.student3.myfavouritepet.HelpClasses.States.Play;
import com.example.student3.myfavouritepet.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

public class Room extends Activity implements View.OnClickListener {

    public static BaseState[] states;

    private int countDeadMessage = 0;
    private static final int NOTIFY_ID = 101;

    private RelativeLayout layout;
    private ImageButton IBPet;
    private TextView tvPetName, tvMoney;

    private void main(){
        setContentView(R.layout.activity_room);
        Context c = getApplicationContext();
        states = new BaseState[]{new Feed(c, new Icecream()), new Solicitude(c), new Play(c)};
        IBPet = (ImageButton) findViewById(R.id.imageButtonPet);
        IBPet.setOnClickListener(this);
        ImageButton IBFood = (ImageButton) findViewById(R.id.imageButtonFood);
        IBFood.setOnClickListener(this);
        ImageButton IBHealth = (ImageButton) findViewById(R.id.imageButtonHealth);
        IBHealth.setOnClickListener(this);
        ImageButton IBAchievement = (ImageButton) findViewById(R.id.imageButtonAchievement);
        IBAchievement.setOnClickListener(this);
        Button btnChangePet = (Button) findViewById(R.id.buttonChangePet);
        btnChangePet.setOnClickListener(this);
        tvPetName = (TextView) findViewById(R.id.textViewNamePet);
        tvMoney = (TextView) findViewById(R.id.textViewMoney);
        layout = (RelativeLayout)findViewById(R.id.room);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
        readFile("PetInfo");
        if (Pet.getName().equals("250801250801")) {
            Intent intent = new Intent(Room.this, MainActivity.class);
            startActivity(intent);
        } else {
            GetLastPetIndex("PetIndex");
            MoneyReadWrite(Operation.Read, "PetMoney");
            CheckStatus();
            StartTimer();
            ShowPetStatus();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        UpdateDataBase();
        WriteLastPetIndex("PetIndex");
        MoneyReadWrite(Operation.Write, "PetMoney");
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMoney.setText("Монет: "+Pet.getMoney());
        readFile("PetInfo");
        CheckStatus();
    }

    @Override
    public void onClick(View v) {
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
                ShowPetStatus();
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
        layout.setBackgroundResource(Pet.getRoom());
        IBPet.setBackgroundResource(Pet.getKind());
    }

    private void readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String s = br.readLine();
            if (s != null) {
                Pet.setName(s);
                Pet.setKind(Integer.valueOf(br.readLine()));
                Pet.setRoom(Integer.valueOf(br.readLine()));
                RecolorRoom();
                tvPetName.setText(Pet.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum Operation {
        Write, Read
    }

    private void MoneyReadWrite(Operation operation, String fileName) {
        switch (operation) {
            case Write:
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
                    bw.write(String.valueOf(Pet.getMoney()));
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Read:
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
                    Pet.setMoney(Integer.valueOf(br.readLine()));
                    tvMoney.setText("Монет: " + Pet.getMoney());
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
            Pet.setPetIndex(Integer.valueOf(br.readLine()));
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void WriteLastPetIndex(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.valueOf(Pet.getPetIndex()));
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void UpdateDataBase() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", Pet.getName());
        cv.put("PetType", Pet.getKind());
        cv.put("RoomColor", Pet.getRoom());
        cv.put("money", Pet.getMoney());
        db.update("PetTable", cv, "id = ?", new String[]{Pet.getPetIndex()+""});
        dbHelper.close();
    }

    private void CheckStatus(){
        for (int i = 0 ; i < states.length; i++){
            if (states[i].getFullness() < 10)
                SendNotification("Мой любимый питомец", states[i].getNotifyMsg(), R.drawable.home);
        }
    }

    private void ShowPetStatus(){
        String msg = "";
        for(int i = 0; i < states.length; i++)
            msg += states[i].getName() + states[i].getFullness() + "\n";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void StartTimer(){
        Timer timer = new Timer();
        MyTimerTask timerTask = new MyTimerTask();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (countDeadMessage > 5) {
                        PetDead();
                    }else {
                        for (int i = 0; i < states.length; i++){
                            if (states[i].getFullness() - 1 > 10)
                                SetFullness(i);
                            else {
                                countDeadMessage++;
                                break;
                            }
                        }
                    }
                }
            });
        }

        private void SetFullness(int i){
            try {
                states[i].setFullness(BaseState.Operation.Minus);
            }catch (TiredException e) {}
            CheckStatus();
        }
    }
}
