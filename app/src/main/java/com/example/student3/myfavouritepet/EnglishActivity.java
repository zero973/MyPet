package com.example.student3.myfavouritepet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Random;

public class EnglishActivity extends AppCompatActivity {

    byte countRightAnswers = 0, index = 0, numTrueCB, numUserCB;
    Random random = new Random();
    String[] answers = new String[4];
    String[] questions = new String[4];
    String[] trueAnswers = new String[4];

    TextView tvCountRight, tvQuestion;
    Button checkBut;
    CheckBox cb1;
    CheckBox cb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_activity);
        tvCountRight = (TextView)findViewById(R.id.textViewCountRightAnswersEnglish);
        tvQuestion = (TextView)findViewById(R.id.textViewQuestionEnglish);
        checkBut = (Button) findViewById(R.id.buttonCheckEnglish);
        cb1 = (CheckBox) findViewById(R.id.checkBoxEnglish1);
        cb2 = (CheckBox) findViewById(R.id.checkBoxEnglish2);

        questions = getResources().getStringArray(R.array.englishQuestions);
        answers = getResources().getStringArray(R.array.englishQuestions_Answers);
        trueAnswers = getResources().getStringArray(R.array.englishQuestions_True_Answers);

        Play();

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb2.isChecked() == true)
                    cb2.setChecked(false);
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb1.isChecked() == true)
                    cb1.setChecked(false);
            }
        });

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked() == true)
                    numUserCB = 0;
                else {
                    numUserCB = 1;
                }
                if (numTrueCB == numUserCB) {
                    countRightAnswers++;
                    Room.money++;
                    tvCountRight.setText("Правильных ответов: " + countRightAnswers + " из 4");
                    Play();
                }
                else {
                    Play();
                }
            }
        });
    }

    void Play(){
        cb1.setChecked(false);
        cb2.setChecked(false);
        if (index == 4) {
            index = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(EnglishActivity.this);
            builder.setTitle("Игра окончена!")
                    .setMessage("Правильных ответов: " + countRightAnswers + " из 4")
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
        tvQuestion.setText(questions[index]);
        byte v = (byte) random.nextInt(2);//0 - первый чекбокс верный, 1 - первый чекбокс неверный
        if (v == 0) {
            cb2.setText(answers[index]);
            cb1.setText(trueAnswers[index]);
        }
        else{
            cb2.setText(trueAnswers[index]);
            cb1.setText(answers[index]);
        }
        index++;
        numTrueCB = v;
    }
}
