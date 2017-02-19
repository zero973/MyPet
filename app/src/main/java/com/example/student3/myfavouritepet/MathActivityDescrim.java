package com.example.student3.myfavouritepet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MathActivityDescrim extends AppCompatActivity {

    TextView tvExample, tvCountRightAnswersDescrim;
    EditText edUserAnswer;
    Button check;

    String[] examples;
    int[] trueAnswers;
    int userAnswer, trueAnswer, countTry = 0;
    byte index = 0, countRightAnswers = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_activity_descrim);
        tvExample = (TextView)findViewById(R.id.textViewExampleDescrim);
        tvCountRightAnswersDescrim = (TextView)findViewById(R.id.textViewCountRightAnswersDescrim);
        edUserAnswer = (EditText)findViewById(R.id.editTextUserAnswerDescrim);
        check = (Button)findViewById(R.id.buttonCheckDescrim);

        examples = getResources().getStringArray(R.array.mathExamplesDiscriminant);
        trueAnswers = getResources().getIntArray(R.array.mathExamplesDiscriminantAnswers);

        Start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer = Integer.valueOf(edUserAnswer.getText().toString());
                if (userAnswer == trueAnswer){
                    countRightAnswers++;
                    countTry++;
                    Start();
                }else {
                    countTry++;
                    Toast.makeText(getApplicationContext(), "Неправильный  ответ!", Toast.LENGTH_SHORT).show();
                }
                tvCountRightAnswersDescrim.setText("Правильных ответов: " + countRightAnswers + " из " + countTry);
            }
        });
    }

    void Start(){
        if (index < 20) {
            tvExample.setText(examples[index]);
            trueAnswer = trueAnswers[index];
            index++;
        }else {
            index = 0;
        }
    }
}
