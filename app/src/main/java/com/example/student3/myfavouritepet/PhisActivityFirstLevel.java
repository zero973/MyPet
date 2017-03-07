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
import android.widget.Toast;

import java.util.Random;

public class PhisActivityFirstLevel extends AppCompatActivity {

    byte countRightAnswers = 0, index = 0, numTrueCB, numUserCB;
    Random random = new Random();
    String[] questions = new String[8], answers = new String[8];
    String[] mass = {"m*a", "m*g", "a*t", "a÷t", "F÷a", "F÷m", "m÷V", "p÷F", "F÷p", "V*p", "k*x", "k÷x", "F*S"};
    String trueAnswer;

    TextView tvQuestion, tvCountRight;
    Button checkBut;
    CheckBox cb1;
    CheckBox cb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phis_activity_first_level);
        tvQuestion = (TextView) findViewById(R.id.textViewQuestionPhis);
        tvCountRight = (TextView)findViewById(R.id.textViewCountRightAnswersPhisFirstLevel);
        checkBut = (Button) findViewById(R.id.buttonCheckFizika);
        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);

        questions = getResources().getStringArray(R.array.phisQuestions);
        answers = getResources().getStringArray(R.array.phisQuestions_Answers);

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
                    numUserCB = 1;//Какой чекбокс выбрали
                }
                if (numTrueCB == numUserCB) {
                    countRightAnswers++;
                    tvCountRight.setText("Правильных ответов: " + countRightAnswers + " из 8");
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
        if (index == 8) {
            index = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(PhisActivityFirstLevel.this);
            builder.setTitle("Игра окончена!")
                    .setMessage("Правильных ответов: " + countRightAnswers + " из 8")
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
        }else {
            trueAnswer = answers[index];
            byte v = (byte) random.nextInt(2);//0 - первый чекбокс верный, 1 - неверный
            if (v == 0) {
                cb1.setText(trueAnswer);
                cb2.setText(Generate());
            } else {
                cb1.setText(Generate());
                cb2.setText(trueAnswer);
            }
            tvQuestion.setText(questions[index]);
            index++;
            numTrueCB = v;
        }
    }

    String Generate(){
        int variant = random.nextInt(13);
        if (mass[variant] != answers[index])
            return mass[variant];
        else
            return Generate();
    }
}
