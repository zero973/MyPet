package com.example.student3.myfavouritepet.Activityes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.student3.myfavouritepet.HelpClasses.Service.Pet;
import com.example.student3.myfavouritepet.R;

import java.util.Random;

public class PhisActivityFirstLevel extends AppCompatActivity implements View.OnClickListener{

    byte countRightAnswers = 0, index = 0, numTrueCB, numUserCB;
    Random random = new Random();
    String[] questions = new String[8], trueAnswers = new String[8];
    String[] answers = {"m*a", "m*g", "a*t", "a÷t", "F÷a", "F÷m", "m÷V", "p÷F", "F÷p", "V*p", "k*x", "k÷x", "F*S"};
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
        checkBut.setOnClickListener(this);
        cb1.setOnClickListener(this);
        cb2.setOnClickListener(this);

        questions = getResources().getStringArray(R.array.phisQuestions);
        trueAnswers = getResources().getStringArray(R.array.phisQuestions_Answers);

        questions = EnglishActivity.randomMass(questions, answers, trueAnswers);
        answers = EnglishActivity.Answers;
        trueAnswers = EnglishActivity.True_answers;

        Play();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
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
            trueAnswer = trueAnswers[index];
            byte v = (byte) random.nextInt(2);//0 - первый чекбокс верный, 1 - неверный
            if (v == 0) {
                cb1.setText(trueAnswer);
                cb2.setText(answers[index]);
            } else {
                cb1.setText(answers[index]);
                cb2.setText(trueAnswer);
            }
            tvQuestion.setText(questions[index]);
            index++;
            numTrueCB = v;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkBox1: if (cb2.isChecked() == true) cb2.setChecked(false); break;
            case R.id.checkBox2: if (cb1.isChecked() == true) cb1.setChecked(false); break;
            case R.id.buttonCheckFizika: if (cb1.isChecked() == false && cb2.isChecked() == false) return; else {
                if (cb1.isChecked() == true) numUserCB = 0;
                else numUserCB = 1;//Какой чекбокс выбрали
                if (numTrueCB == numUserCB) {
                    countRightAnswers++;
                    Pet.IncreaseMoney();
                    tvCountRight.setText("Правильных ответов: " + countRightAnswers + " из 8");
                }
                Play();
            }break;
        }
    }
}
