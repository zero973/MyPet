package com.example.student3.myfavouritepet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Random;

public class EnglishActivity extends AppCompatActivity implements View.OnClickListener{

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
        cb1.setOnClickListener(this);
        cb2.setOnClickListener(this);
        checkBut.setOnClickListener(this);

        byte typeActivity = 0;
        Intent intent = getIntent();
        typeActivity = Byte.valueOf(intent.getStringExtra("activity"));

        switch (typeActivity){
            case 0: EngActivity(); break;
            case 1: HimActivity(); break;
            case 2: BioActivity(); break;
        }

        tvCountRight.setText("Правильных ответов: 0 из " + questions.length);

        Play();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    void Play(){
        cb1.setChecked(false);
        cb2.setChecked(false);
        if (index == 16) {
            index = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(EnglishActivity.this);
            builder.setTitle("Игра окончена!").setMessage("Правильных ответов: " + countRightAnswers + " из " + questions.length)
                    .setCancelable(false).setNegativeButton("Закончить игру", new DialogInterface.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkBoxEnglish1: if (cb2.isChecked() == true) cb2.setChecked(false); break;
            case R.id.checkBoxEnglish2: if (cb1.isChecked() == true) cb1.setChecked(false); break;
            case R.id.buttonCheckEnglish: if (cb1.isChecked() == false && cb2.isChecked() == false)
                return;
            else {
                if (cb1.isChecked() == true)
                    numUserCB = 0;
                else {
                    numUserCB = 1;
                }
                if (numTrueCB == numUserCB) {
                    countRightAnswers++;
                    Room.money++;
                    tvCountRight.setText("Правильных ответов: " + countRightAnswers + " из " + questions.length);
                    Play();
                } else {
                    Play();
                }
            } break;
        }
    }

    void EngActivity(){
        questions = getResources().getStringArray(R.array.englishQuestions);
        answers = getResources().getStringArray(R.array.englishQuestions_Answers);
        trueAnswers = getResources().getStringArray(R.array.englishQuestions_True_Answers);
    }

    void HimActivity(){
        questions = getResources().getStringArray(R.array.himiyaQuestions);
        answers = getResources().getStringArray(R.array.himiyaQuestions_Answers);
        trueAnswers = getResources().getStringArray(R.array.himiyaQuestions_True_Answers);
        setTitle("Химия");
    }

    void BioActivity(){
        questions = getResources().getStringArray(R.array.biologiyaQuestions);
        answers = getResources().getStringArray(R.array.biologiyaQuestions_Answers);
        trueAnswers = getResources().getStringArray(R.array.biologiyaQuestions_True_Answers);
        setTitle("Биология");
    }
}
