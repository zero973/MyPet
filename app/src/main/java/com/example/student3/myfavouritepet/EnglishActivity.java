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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnglishActivity extends AppCompatActivity implements View.OnClickListener{

    byte countRightAnswers = 0, index = 0, numTrueCB, numUserCB, typeActivity;
    Random random = new Random();
    String[] answers, questions, trueAnswers;

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

        Intent intent = getIntent();
        typeActivity = Byte.valueOf(intent.getStringExtra("activity"));

        switch (typeActivity){
            case 0: EngActivity(); break;
            case 1: HimActivity(); break;
            case 2: BioActivity(); break;
            case 3: GramActivity(); break;
            case 4: UdarActivity(); break;
        }

        if (typeActivity == 3 || typeActivity == 4) {
            answers = randomMass(answers, trueAnswers);
            trueAnswers = True_answers;
        }
        else {
            questions = randomMass(questions, answers, trueAnswers);
            answers = Answers;
            trueAnswers = True_answers;
        }
        tvCountRight.setText("Правильных ответов: 0 из " + answers.length);

        Play();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    void Play(){
        cb1.setChecked(false);
        cb2.setChecked(false);
        if (index == answers.length) {
            index = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(EnglishActivity.this);
            builder.setTitle("Игра окончена!").setMessage("Правильных ответов: " + countRightAnswers + " из " + answers.length)
                    .setCancelable(false).setNegativeButton("Закончить игру", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if (typeActivity < 3)
            tvQuestion.setText(questions[index]);
        else if (typeActivity == 3)
            tvQuestion.setText("Как правильно?");
        else if (typeActivity == 4)
            tvQuestion.setText("Куда падает ударение?");
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
            case R.id.checkBoxEnglish1: if (cb2.isChecked()) cb2.setChecked(false); break;
            case R.id.checkBoxEnglish2: if (cb1.isChecked()) cb1.setChecked(false); break;
            case R.id.buttonCheckEnglish: if (!cb1.isChecked() && !cb2.isChecked()) return; else {
                if (cb1.isChecked())
                    numUserCB = 0;
                else {
                    numUserCB = 1;
                }
                if (numUserCB == numTrueCB) {
                    countRightAnswers++;
                    Room.money++;
                    tvCountRight.setText("Правильных ответов: " + countRightAnswers + " из " + answers.length);
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

    void GramActivity(){
        answers = getResources().getStringArray(R.array.rusQuestions_How_True);
        trueAnswers = getResources().getStringArray(R.array.rusQuestions_Answers_How_True);
        setTitle("Грамматика");
    }

    void UdarActivity(){
        answers = getResources().getStringArray(R.array.rusQuestions_Udarenie);
        trueAnswers = getResources().getStringArray(R.array.rusQuestions_Answers_Udarenie);
        setTitle("Ударение");
    }

    public static String[] Answers, True_answers;

    public static String[] randomMass(String[] questions, String[] answers, String[] true_answers){
        Answers = new String[answers.length]; True_answers = new String[true_answers.length];
        String[] result = new String[questions.length];
        boolean[] isUsed = new boolean[questions.length];
        for(int i = 0; i < questions.length; i++)
            isUsed[i] = false;
        Random r = new Random();
        byte index;
        for(int i = 0; i < questions.length; i++)
        {
            index = (byte) r.nextInt(questions.length);
            if(isUsed[index] == false){
                result[i] = questions[index];
                Answers[i] = answers[index];
                True_answers[i] = true_answers[index];
                isUsed[index] = true;
            }else i--;
        }
        return result;
    }

    String[] randomMass(String[] answers, String[] true_answers){
        Answers = new String[answers.length]; True_answers = new String[true_answers.length];
        String[] result = new String[answers.length];
        boolean[] isUsed = new boolean[answers.length];
        for(int i = 0; i < answers.length; i++)
            isUsed[i] = false;
        Random r = new Random();
        byte index;
        for(int i = 0; i < answers.length; i++)
        {
            index = (byte) r.nextInt(answers.length);
            if(isUsed[index] == false){
                result[i] = answers[index];
                True_answers[i] = true_answers[index];
                isUsed[index] = true;
            }else i--;
        }
        return result;
    }
}
