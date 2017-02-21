package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class PhisActivity extends AppCompatActivity {

    byte i = 0;

    Button checkButFiz, goNextLevelFiz;
    TextView tw;
    CheckBox ck17;
    CheckBox ck28;
    CheckBox ck18;
    CheckBox ck19;
    CheckBox ck20;
    CheckBox ck21;
    CheckBox ck22;
    CheckBox ck23;
    CheckBox ck24;
    CheckBox ck25;
    CheckBox ck26;
    CheckBox ck27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phis_activity);
        checkButFiz = (Button)findViewById(R.id.buttonCheckFizika);
        goNextLevelFiz = (Button)findViewById(R.id.buttonGoNextLevelFizika);
        tw = (TextView)findViewById(R.id.textView11);
        ck17 = (CheckBox)findViewById(R.id.checkBox17);
        ck28 = (CheckBox)findViewById(R.id.checkBox28);
        ck18 = (CheckBox)findViewById(R.id.checkBox18);
        ck19 = (CheckBox)findViewById(R.id.checkBox19);
        ck20 = (CheckBox)findViewById(R.id.checkBox20);
        ck21 = (CheckBox)findViewById(R.id.checkBox21);
        ck22 = (CheckBox)findViewById(R.id.checkBox22);
        ck23 = (CheckBox)findViewById(R.id.checkBox23);
        ck24 = (CheckBox)findViewById(R.id.checkBox24);
        ck25 = (CheckBox)findViewById(R.id.checkBox25);
        ck26 = (CheckBox)findViewById(R.id.checkBox26);
        ck27 = (CheckBox)findViewById(R.id.checkBox27);
        goNextLevelFiz.setVisibility(View.INVISIBLE);

        goNextLevelFiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhisActivity.this, PhisActivityTasks.class);
                startActivity(intent);
            }
        });

        checkButFiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                if (ck28.isChecked() == true && ck17.isChecked() == false)
                    i++;
                if (ck19.isChecked() == true && ck18.isChecked() == false)
                    i++;
                if (ck20.isChecked() == true && ck21.isChecked() == false)
                    i++;
                if (ck23.isChecked() == true && ck22.isChecked() == true)
                    i++;
                if (ck24.isChecked() == true && ck25.isChecked() == false)
                    i++;
                if (ck26.isChecked() == true && ck27.isChecked() == false)
                    i++;
                if (i == 6) {
                    goNextLevelFiz.setVisibility(View.VISIBLE);
                }
                tw.setText("Правильных ответов: " + i + " из 6");
                clear();
            }
        });
    }

    void clear(){
        ck17.setChecked(false);
        ck18.setChecked(false);
        ck19.setChecked(false);
        ck20.setChecked(false);
        ck21.setChecked(false);
        ck22.setChecked(false);
        ck23.setChecked(false);
        ck24.setChecked(false);
        ck25.setChecked(false);
        ck26.setChecked(false);
        ck27.setChecked(false);
        ck28.setChecked(false);
    }
}
