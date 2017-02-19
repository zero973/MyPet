package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameSchool extends AppCompatActivity {

    TextView tv;
    Button Matem;
    Button Fizika;
    Button Russkiy;
    Button English;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        tv = (TextView)findViewById(R.id.textView);
        Matem = (Button)findViewById(R.id.butMath);
        Fizika = (Button)findViewById(R.id.butPhis);
        Russkiy = (Button)findViewById(R.id.butRus);
        English = (Button)findViewById(R.id.butEnglish);

        Matem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSchool.this, MathActivity.class);
                startActivity(intent);
            }
        });

        Fizika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSchool.this, PhisActivity.class);
                startActivity(intent);
            }
        });

        Russkiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSchool.this, RusActivity.class);
                startActivity(intent);
            }
        });

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSchool.this, EnglishActivity.class);
                startActivity(intent);
            }
        });
    }
}
