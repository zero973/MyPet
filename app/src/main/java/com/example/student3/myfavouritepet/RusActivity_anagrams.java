package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RusActivity_anagrams extends AppCompatActivity {

    TextView anagramma, UserLevel, CountRightAnswers;
    EditText UserWord;
    Button CheckWord;

    String trueWord;
    String[] words1, words2;

    byte level = 1, countRightAnswers = 0, countTryFalse = 0;
    int indexWord = 0, countTry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rus_activity_anagrams);
        anagramma = (TextView)findViewById(R.id.textViewWord);
        UserLevel = (TextView)findViewById(R.id.textViewLevelAnagrams);
        CountRightAnswers = (TextView)findViewById(R.id.textViewCountRightAnswers);
        UserWord = (EditText)findViewById(R.id.editTextUserWord);
        CheckWord = (Button)findViewById(R.id.buttonCheckUserWord);

        words1 = getResources().getStringArray(R.array.words_1_lvl);
        words2 = getResources().getStringArray(R.array.words_2_lvl);

        words1 = randomMass(words1);
        words2 = randomMass(words2);

        GoPlayAnagram();

        CheckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countRightAnswers >= 18) {
                    indexWord = 0;
                    countTry = 0;
                    countRightAnswers = 0;
                    level++;
                    GoPlayAnagram();
                }
                else {
                    if (UserWord.getText().toString().equals("debug"))
                        SendNotification("Мой любимый питомец", "debug", R.drawable.home);
                    if (trueWord.toLowerCase().equals(UserWord.getText().toString().toLowerCase())) {
                        countRightAnswers++;
                        Room.money += 2;
                        CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из 20");
                    GoPlayAnagram();
                    } else if (countTryFalse == 3){
                        UserWord.setText(trueWord);
                        Toast.makeText(getApplicationContext(), "Правильный ответ: " + trueWord, Toast.LENGTH_SHORT).show();
                        countTryFalse = 0;
                    }
                    else {
                        countTryFalse++;
                        Toast.makeText(getApplicationContext(), "Неправильный ответ, подумай ещё!", Toast.LENGTH_SHORT).show();
                    }
                }
                countTry++;
                UserWord.setText("");
                CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из 20");
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void GoPlayAnagram(){
        if (level == 1){
            if (indexWord < 20)
                trueWord = words1[indexWord];
            else
                indexWord = 0;
        }
        else{
            if (indexWord < 20)
                trueWord = words2[indexWord];
            else
                indexWord = 0;
        }
        if (level == 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(RusActivity_anagrams.this);
            builder.setTitle("Вы всё прошли!")
                    .setMessage("Игра окончена!")
                    .setCancelable(false)
                    .setNegativeButton("Закончить игру",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        String slovo = GenerateWord(trueWord);
        while (slovo.equals(trueWord))
            slovo = GenerateWord(trueWord);
        anagramma.setText(ToUpperText(slovo));
        CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из 20");
        UserLevel.setText("Уровень: " + level);
        indexWord++;
    }

    private String GenerateWord(String word){
        boolean[] stroka = new boolean[word.length()];
        byte index;
        Random rand = new Random();
        String result = "";
        for (int i = 0; i < word.length(); i++)
            stroka[i] = false;
        for (int i = 0; i < word.length(); i++){
            index = (byte) rand.nextInt(word.length());
            if (stroka[index] == false){
                result += word.charAt(index);
                stroka[index] = true;
            }else
                i--;
        }
        if (result.equals(word))
            GenerateWord(word);
        return result;
    }

    private String ToUpperText(String word){
        String result = "";
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            result += c + " ";
        }
        return result;
    }

    public static String[] randomMass(String[] words){
        String[] result = new String[words.length];
        boolean[] isUsed = new boolean[words.length];
        for(int i = 0; i < words.length; i++)
            isUsed[i] = false;
        Random r = new Random();
        byte index;
        for(int i = 0; i < words.length; i++)
        {
            index = (byte) r.nextInt(words.length);
            if(isUsed[index] == false){
                result[i] = words[index];
                isUsed[index] = true;
            }else i--;
        }
        return result;
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
        notificationManager.notify(101, notification);
    }
}
