package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class RusActivity extends AppCompatActivity {

    byte i = 0;

    TextView tw;
    Button resultBut;
    Button nextButRus;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;
    CheckBox cb8;
    CheckBox cb9;
    CheckBox cb10;
    CheckBox cb11;
    CheckBox cb12;
    CheckBox cb13;
    CheckBox cb14;
    CheckBox cb15;
    CheckBox cb16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rus_activity);
        tw = (TextView)findViewById(R.id.textView4);
        nextButRus = (Button)findViewById(R.id.nextExamplesRus);
        resultBut = (Button)findViewById(R.id.getResult);
        cb1 = (CheckBox)findViewById(R.id.checkBox1);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);
        cb3 = (CheckBox)findViewById(R.id.checkBox3);
        cb4 = (CheckBox)findViewById(R.id.checkBox4);
        cb5 = (CheckBox)findViewById(R.id.checkBox5);
        cb6 = (CheckBox)findViewById(R.id.checkBox6);
        cb7 = (CheckBox)findViewById(R.id.checkBox7);
        cb8 = (CheckBox)findViewById(R.id.checkBox8);
        cb9 = (CheckBox)findViewById(R.id.checkBox9);
        cb10 = (CheckBox)findViewById(R.id.checkBox10);
        cb11 = (CheckBox)findViewById(R.id.checkBox11);
        cb12 = (CheckBox)findViewById(R.id.checkBox12);
        cb13 = (CheckBox)findViewById(R.id.checkBox13);
        cb14 = (CheckBox)findViewById(R.id.checkBox14);
        cb15 = (CheckBox)findViewById(R.id.checkBox15);
        cb16 = (CheckBox)findViewById(R.id.checkBox16);
        Toast.makeText(this, "Для перехода на следующий уровень нужно ответить на все вопросы правильно", Toast.LENGTH_LONG).show();

        resultBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                if (cb2.isChecked() == true && cb1.isChecked() == false)
                    i++;
                if (cb3.isChecked() == true && cb4.isChecked() == false)
                    i++;
                if (cb5.isChecked() == true && cb6.isChecked() == false)
                    i++;
                if (cb10.isChecked() == true && cb9.isChecked() == false)
                    i++;
                if (cb7.isChecked() == true && cb8.isChecked() == false)
                    i++;
                if (cb11.isChecked() == true && cb12.isChecked() == false)
                    i++;
                if (cb13.isChecked() == true && cb14.isChecked() == false)
                    i++;
                if (cb15.isChecked() == true && cb16.isChecked() == false)
                    i++;
                tw.setText("Правильных ответов: " + i + " из 8");
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                cb5.setChecked(false);
                cb6.setChecked(false);
                cb7.setChecked(false);
                cb8.setChecked(false);
                cb9.setChecked(false);
                cb10.setChecked(false);
                cb11.setChecked(false);
                cb12.setChecked(false);
                cb13.setChecked(false);
                cb14.setChecked(false);
                cb15.setChecked(false);
                cb16.setChecked(false);
                if (i == 8){
                    nextButRus.setVisibility(View.VISIBLE);
                }
            }
        });

        nextButRus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RusActivity.this, RusActivity_anagrams.class);
                startActivity(intent);
            }
        });
    }
}
