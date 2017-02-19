package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MathActivity extends AppCompatActivity {
    byte[] znakMass = new byte[2];
    int[] numsMass = new int[4];
    int[] rightAnswers = new int[2];
    int[] userAnswers = new int[2];
    byte k = 0, i = 0, j = 0, userLevel = 1;
    int countRight = 0;
    int count = 0;
    boolean CanGoNextLevel = false;

    Button nextbutton;
    Button checkBut;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;//znak
    TextView t6;//znak
    TextView TextResult;
    EditText ans1;
    EditText ans2;
    Random rand = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_activity);
        nextbutton = (Button)findViewById(R.id.StartButton);
        checkBut = (Button)findViewById(R.id.buttonCheckAnswers);
        t1 = (TextView)findViewById(R.id.TextFieldChislo1);
        t2 = (TextView)findViewById(R.id.TextFieldChislo2);
        t3 = (TextView)findViewById(R.id.TextFieldChislo3);
        t4 = (TextView)findViewById(R.id.TextFieldChislo4);
        t5 = (TextView)findViewById(R.id.TextZnak1);
        t6 = (TextView)findViewById(R.id.TextZnak2);
        TextResult = (TextView)findViewById(R.id.textViewRightAnswers);
        ans1 = (EditText)findViewById(R.id.Answer1);
        ans2 = (EditText)findViewById(R.id.Answer2);
        ChangeSymbolOnLabel();
        ChangeChisloOnLabel();
        nextbutton.setVisibility(View.INVISIBLE);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countRight = count = 0;
                userLevel = 1;
                Intent intent = new Intent(MathActivity.this, MathActivityDescrim.class);
                startActivity(intent);
            }
        });

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countRight >= 5)
                    userLevel++;
                else if (countRight >= 15)
                    userLevel++;
                else if (countRight >= 25)
                    userLevel++;
                if (userLevel == 4){
                    nextbutton.setVisibility(View.VISIBLE);
                }
                CheckResults();
                ChangeSymbolOnLabel();
                ChangeChisloOnLabel();
                ans1.setText("0");
                ans2.setText("0");
            }
        });
    }

    void CheckResults(){
        k = 0;
        for (int i = 0; i < 4; i += 2){
            Chitatel(numsMass[i], numsMass[i+1], znakMass[k]);
            k++;
        }//задали ответы
        try {
            userAnswers[0] = Integer.valueOf(ans1.getText().toString());
            userAnswers[1] = Integer.valueOf(ans2.getText().toString());//ответы юзера
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Не вводите буквы и другие символы", Toast.LENGTH_LONG).show();
            userAnswers[0] = userAnswers[1] = -2287777;
        }
        count += 2;
        for (int i = 0; i < 2; i++){
            if (userAnswers[i] == rightAnswers[i])
                countRight++;
        }
        TextResult.setText("Правильных ответов " + countRight + " из " + count);
        k = 0; i = 0; j = 0;
    }

    int GenerateNum(){
        short diapozon = 0;
        if (userLevel == 1)
            diapozon = 50;
        else if(userLevel == 2)
            diapozon = 500;
        else if (userLevel == 3 && znakMass[k-1] != 2)
            diapozon = 1000;
        else if (userLevel == 3 && znakMass[k-1] == 2)
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
        znakMass[k] = index;
        k++;
        return symbol;
    }

    void ChangeSymbolOnLabel(){
        t5.setText(GenerateZnak());
        t6.setText(GenerateZnak());
    }

    void ChangeChisloOnLabel(){
        t1.setText(String.valueOf(GenerateNum()));
        t2.setText(String.valueOf(GenerateNum()));
        t3.setText(String.valueOf(GenerateNum()));
        t4.setText(String.valueOf(GenerateNum()));
    }

    void Chitatel(int f1, int f2, byte s){
        int result = 0;
        switch (s)
        {
            case 0: result = f1 + f2; break;
            case 1: result = f1 - f2; break;
        }
        rightAnswers[j] = result;
        j++;
    }
}
