package com.example.student3.myfavouritepet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class EnglishActivity extends AppCompatActivity {

    byte i = 0;

    Button checkButEng;
    TextView tw;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;
    CheckBox cb8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_activity);
        checkButEng = (Button)findViewById(R.id.butCheckEnglishResults);
        tw = (TextView)findViewById(R.id.getEnglishResults);
        cb1 = (CheckBox)findViewById(R.id.checkBox36);
        cb2 = (CheckBox)findViewById(R.id.checkBox29);
        cb3 = (CheckBox)findViewById(R.id.checkBox30);
        cb4 = (CheckBox)findViewById(R.id.checkBox31);
        cb5 = (CheckBox)findViewById(R.id.checkBox32);
        cb6 = (CheckBox)findViewById(R.id.checkBox33);
        cb7 = (CheckBox)findViewById(R.id.checkBox34);
        cb8 = (CheckBox)findViewById(R.id.checkBox35);

        checkButEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                if (cb1.isChecked() == true && cb2.isChecked() == false)
                    i++;
                if (cb4.isChecked() == true && cb3.isChecked() == false)
                    i++;
                if (cb6.isChecked() == true && cb5.isChecked() == false)
                    i++;
                if (cb7.isChecked() == true && cb8.isChecked() == false)
                    i++;
                tw.setText("Правильных ответов: " + i + " из 4");
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                cb5.setChecked(false);
                cb6.setChecked(false);
                cb7.setChecked(false);
                cb8.setChecked(false);
            }
        });
    }
}
