package com.example.student3.myfavouritepet;

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

public class PhisActivityTasks extends AppCompatActivity {

    TextView tvExample, tvCountRightAnswersExample;
    EditText edUserAnswer;
    Button check;

    String[] examples, trueAnswers;
    String userAnswer, trueAnswer;
    int countTry = 0;
    byte index = 0, countRightAnswers = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phis_activity_tasks);

        tvExample = (TextView)findViewById(R.id.textViewExampleExamplePhis);
        tvCountRightAnswersExample = (TextView)findViewById(R.id.textViewCountRightAnswersExamplePhis);
        edUserAnswer = (EditText)findViewById(R.id.editTextUserAnswerExamplePhis);
        check = (Button)findViewById(R.id.buttonCheckExamplePhis);

        examples = getResources().getStringArray(R.array.phisTasks);
        trueAnswers = getResources().getStringArray(R.array.phisTasks_Answers);

        Start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = edUserAnswer.getText().toString();
                if (userAnswer.equals(trueAnswer)){
                    countRightAnswers++;
                    countTry++;
                    Room.money += 2;
                    edUserAnswer.setText("");
                    Start();
                }else {
                    countTry++;
                    Toast.makeText(getApplicationContext(), "Неправильный  ответ!", Toast.LENGTH_SHORT).show();
                }
                tvCountRightAnswersExample.setText("Правильных ответов: " + countRightAnswers + " из " + countTry);
            }
        });
    }

    void Start(){
        if (index < 6) {
            tvExample.setText(examples[index]);
            trueAnswer = trueAnswers[index];
            index++;
        }else {
            index = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(PhisActivityTasks.this);
            builder.setTitle("Игра окончена!")
                    .setMessage("Правильных ответов: " + countRightAnswers + " из " + countTry)
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
    }
}
