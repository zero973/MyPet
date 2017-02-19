package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

    byte level = 1, countRightAnswers = 0;
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
        Toast.makeText(this, "Для перехода на следующий уровень наберите 15 правильных ответов", Toast.LENGTH_SHORT);

        GoPlayAnagram();

        CheckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countRightAnswers == 20) {
                    indexWord = 0;
                    countTry = 0;
                    countRightAnswers = 0;
                    level++;
                    GoPlayAnagram();
                }
                else {
                    if (countTry > 3) {
                        if (trueWord == UserWord.getText().toString()) {
                            countRightAnswers++;
                            CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из " + countTry);
                            GoPlayAnagram();
                        } else {
                            Toast.makeText(getApplicationContext(), "Неправильный ответ, подумай ещё!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Правильный ответ: " + trueWord, Toast.LENGTH_LONG).show();
                    }
                    countTry++;
                    CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из " + countTry);
                }
            }
        });
    }

    void GoPlayAnagram(){
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
                                    Intent intent = new Intent(RusActivity_anagrams.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        anagramma.setText(GenerateWord(trueWord));
        CountRightAnswers.setText("Правильных ответов: " + countRightAnswers + " из " + countTry);
        UserLevel.setText("Уровень: " + level);
        indexWord++;
    }

    String GenerateWord(String word){
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
        return result;
    }
}
