package com.example.student3.myfavouritepet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MathActivityExamples extends AppCompatActivity {
    byte znakMass;
    int[] numsMass = new int[2];
    int rightAnswer;
    byte i = 0, userLevel = 1;
    int countRight = 0;
    int count = 0;
    char[] numbers = {'0', '1' ,'2', '3', '4' ,'5', '6', '7' ,'8', '9', '-'};

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

        ReadActivity("MathActivityExamples");
        CheckRightAnswers();
        TextResult.setText("Правильных ответов " + countRight + " из " + count);
        ChangeSymbolOnLabel();

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckResults()) {
                    CheckRightAnswers();
                    ChangeSymbolOnLabel();
                    ans1.setText("0");
                }
            }
        });

        ans1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean correct = false;
                for(int i = 0; i < numbers.length; i++)
                    if (s.length() > 0 && s.charAt(s.length()-1) == numbers[i]) correct = true;
                if (s.length() > 0 && !correct){
                    String s1 = s.toString();
                    try {
                        ans1.setText(s1.substring(0, s.length() - 1));
                    }catch (Exception e){}
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPause(){
        super.onPause();
        SaveActivity("MathActivityExamples");
    }

    @Override
    public void onStop(){
        super.onStop();
        SaveActivity("MathActivityExamples");
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
        if (userAnswer == 250801)
            Room.money = 9999999;
        if (userAnswer == rightAnswer)
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
        rightAnswer = result;
    }

    void CheckRightAnswers(){
        if (countRight == 5) {
            userLevel++;
            Room.money += 5;
        }
        else if (countRight == 15) {
            userLevel++;
            Room.money += 15;
        }
        else if (countRight == 25) {
            userLevel++;
            Room.money += 25;
        }
    }

    void SaveActivity(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(String.format(countRight + " " + count + " "));
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ReadActivity(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String ActivityInFile = br.readLine();
            int j = 0;
            String chislo = "";
            ActivityInFile.charAt(j);
            while (ActivityInFile.charAt(j) != ' '){
                chislo += ActivityInFile.charAt(j);
                j++;
            }
            j++;
            countRight = Integer.valueOf(chislo);
            chislo = "";
            while (ActivityInFile.charAt(j) != ' '){
                chislo += ActivityInFile.charAt(j);
                j++;
            }
            count = Integer.valueOf(chislo);
            br.close();
        }catch (FileNotFoundException e) {
            SaveActivity("MathActivityExamples");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){}
    }
}