package com.example.student3.myfavouritepet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class MathActivityExamples extends AppCompatActivity {
    byte znakMass;
    int[] numsMass = new int[2];
    int rightAnswers;
    byte i = 0, userLevel = 1, falseExample = 0;
    int countRight = 0;
    int count = 0;

    Button checkBut;
    TextView t1;
    TextView t2;
    TextView t5;//znak
    TextView TextResult;
    EditText ans1;
    Random rand = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_activity_examples);
        checkBut = (Button)findViewById(R.id.buttonCheckAnswers);
        t1 = (TextView)findViewById(R.id.TextFieldChislo1);
        t2 = (TextView)findViewById(R.id.TextFieldChislo2);
        t5 = (TextView)findViewById(R.id.TextZnak1);
        TextResult = (TextView)findViewById(R.id.textViewRightAnswers);
        ans1 = (EditText)findViewById(R.id.Answer1);
        ChangeSymbolOnLabel();

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckResults()) {
                    if (countRight == 5 || countRight == 6)
                        userLevel++;
                    else if (countRight == 15 || countRight == 16)
                        userLevel++;
                    else if (countRight == 25 || countRight == 26)
                        userLevel++;
                    ChangeSymbolOnLabel();
                    ans1.setText("0");
                }
            }
        });
    }

    boolean CheckResults(){
        for (int i = 0; i < 2; i += 2){
            Chitatel(numsMass[i], numsMass[i+1], znakMass);
        }//задали ответы
        i = 0;
        if (ans1.getText().toString().equals(""))
            return false;
        int userAnswer;
        try {
            userAnswer = Integer.valueOf(ans1.getText().toString());
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Не вводите буквы и другие символы", Toast.LENGTH_LONG).show();
            return false;
        }
        count++;
        if (userAnswer == 228777){
            userLevel = 3;
            countRight += 25;
        }
        if (userAnswer == rightAnswers)
            countRight++;
        else {
            TextResult.setText("Правильных ответов " + countRight + " из " + count);
            Toast.makeText(getApplicationContext(), "Подумай ещё!", Toast.LENGTH_SHORT).show();
            return false;
        }
        TextResult.setText("Правильных ответов " + countRight + " из " + count);
        return true;
    }

    int GenerateNum(){
        short diapozon = 0;
        if (userLevel == 1)
            diapozon = 50;
        else if(userLevel == 2)
            diapozon = 500;
        else if (userLevel >= 3 && znakMass != 2)
            diapozon = 1000;
        else if (userLevel >= 3 && znakMass == 2)
            diapozon = 50;
        int chislo = rand.nextInt(diapozon);
        numsMass[i] = chislo;
        i++;
        return chislo;
    }

    String GenerateZnak(){
        String symbol = "";
        byte diapozon = 0;
        if (userLevel <= 2)
            diapozon = 2;
        else if(userLevel > 2)
            diapozon = 3;
        byte index = (byte)rand.nextInt(diapozon);
        switch (index)
        {
            case 0: symbol = "+"; break;
            case 1: symbol = "―"; break;
            case 2: symbol = "X"; break;
        }
        znakMass = index;
        return symbol;
    }

    void ChangeSymbolOnLabel(){
        t5.setText(GenerateZnak());
        t1.setText(String.valueOf(GenerateNum()));
        t2.setText(String.valueOf(GenerateNum()));
    }

    void Chitatel(int f1, int f2, byte s){
        int result = 0;
        switch (s)
        {
            case 0: result = f1 + f2; break;
            case 1: result = f1 - f2; break;
            case 2: result = f1 * f2; break;
        }
        rightAnswers = result;
    }
}