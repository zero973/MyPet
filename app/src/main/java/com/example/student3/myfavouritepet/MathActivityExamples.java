package com.example.student3.myfavouritepet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    private byte znak;
    private int[] numsMass = new int[2];
    private int rightAnswer, countRight = 0, count = 0;
    private byte userLevel = 1;
    private char[] numbers = {'0', '1' ,'2', '3', '4' ,'5', '6', '7' ,'8', '9', '-'};

    private TextView tvChislo1, tvChislo2, tvZnak, TextResult;
    private EditText edUserAnswer;
    private Button checkBut;
    private Random rand = new Random();

    private void main(){
        setContentView(R.layout.math_activity_examples);
        checkBut = (Button) findViewById(R.id.buttonCheckAnswers);
        tvChislo1 = (TextView)findViewById(R.id.TextFieldChislo1);
        tvChislo2 = (TextView)findViewById(R.id.TextFieldChislo2);
        tvZnak = (TextView)findViewById(R.id.TextZnak1);
        TextResult = (TextView)findViewById(R.id.textViewRightAnswers);
        edUserAnswer = (EditText)findViewById(R.id.Answer1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();

        ReadActivity("MathActivityExamples");
        LevelUp();
        TextResult.setText("Правильных ответов " + countRight + " из " + count);
        ChangeSymbolOnLabel();

        /*CountUpRightResult();
        edUserAnswer.setText(rightAnswer+"");*/

        checkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckResults()) {
                    LevelUp();
                    ChangeSymbolOnLabel();
                    /*CountUpRightResult();
                    edUserAnswer.setText(rightAnswer+"");*/
                }else Toast.makeText(getApplicationContext(), "Подумай ещё!", Toast.LENGTH_SHORT).show();
            }
        });

        edUserAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                boolean correct = false;
                for(int i = 0; i < numbers.length; i++)
                    if (s.length() > 0 && s.charAt(s.length()-1) == numbers[i]) correct = true;
                if (s.length() > 0 && !correct){
                    String s1 = s.toString();
                    try {
                        edUserAnswer.setText(s1.substring(0, s.length() - 1));
                    }catch (Exception e){}
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        SaveActivity("MathActivityExamples");
    }

    private boolean CheckResults(){
        CountUpRightResult();
        if (edUserAnswer.getText().toString().equals(""))
            return false;
        int userAnswer = GetUserAnswer();
        count++;
        CheckForCheats(userAnswer);
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

    private int GetUserAnswer(){
        int result = -250801250;
        try {
            result = Integer.valueOf(edUserAnswer.getText().toString());
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Не вводите буквы и другие символы", Toast.LENGTH_LONG).show();
        }
        return result;
    }

    private void CheckForCheats(int userAnswer){
        if (userAnswer == 228777){
            userLevel = 3;
            countRight += 25;
        }
        if (userAnswer == 250801)
            Pet.setMoney(9999999);
    }

    private void GenerateNum(){
        short numberRange = SetNumberRange();
        numsMass[0] = rand.nextInt(numberRange);
        numsMass[1] = rand.nextInt(numberRange);
    }

    private short SetNumberRange(){
        short result = 100;
        if (userLevel == 1)
            result = 50;
        else if(userLevel == 2)
            result = 500;
        else if (userLevel >= 3 && znak != 2)
            result = 1000;
        else if (userLevel >= 3 && znak == 2)
            result = 50;
        return result;
    }

    private String GenerateZnak(){
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
        znak = index;
        return symbol;
    }

    private void ChangeSymbolOnLabel(){
        edUserAnswer.setText("");
        tvZnak.setText(GenerateZnak());
        GenerateNum();
        tvChislo1.setText(numsMass[0]+"");
        tvChislo2.setText(numsMass[1]+"");
    }

    private void CountUpRightResult(){
        int result = 0;
        switch (znak)
        {
            case 0: result = numsMass[0] + numsMass[1]; break;
            case 1: result = numsMass[0] - numsMass[1]; break;
            case 2: result = numsMass[0] * numsMass[1]; break;
        }
        rightAnswer = result;
    }

    private void LevelUp(){
        if (countRight >= userLevel * 10) {
            userLevel++;
            Pet.IncreaseMoney(userLevel * 10);
        }
    }

    private void SaveActivity(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(countRight + " " + count + " " +  userLevel);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ReadActivity(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String[] ActivityInFile = br.readLine().split(" ");
            countRight = Integer.valueOf(ActivityInFile[0]);
            count = Integer.valueOf(ActivityInFile[1]);
            userLevel = Byte.valueOf(ActivityInFile[2]);
            br.close();
        }catch (FileNotFoundException e) {
            SaveActivity("MathActivityExamples");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ignored){}
    }
}