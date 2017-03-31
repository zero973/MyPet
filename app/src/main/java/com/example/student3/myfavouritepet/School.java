package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class School extends AppCompatActivity implements View.OnClickListener{

    Button Matem, Fizika, Russkiy, English, Himiya, Biologiya;
    RelativeLayout room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_activity);
        Matem = (Button)findViewById(R.id.butMath);
        Fizika = (Button)findViewById(R.id.butPhis);
        Russkiy = (Button)findViewById(R.id.butRus);
        English = (Button)findViewById(R.id.butEnglish);
        Himiya = (Button)findViewById(R.id.buttonHimiya);
        Biologiya = (Button)findViewById(R.id.buttonBiologiya);
        Matem.setOnClickListener(this);
        Fizika.setOnClickListener(this);
        Russkiy.setOnClickListener(this);
        English.setOnClickListener(this);
        Himiya.setOnClickListener(this);
        Biologiya.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.butMath: intent = new Intent(School.this, MathActivity.class); break;
            case R.id.butPhis: intent = new Intent(School.this, PhisActivity.class); break;
            case R.id.butRus: intent = new Intent(School.this, RusActivity.class); break;
            case R.id.butEnglish: intent = new Intent(School.this, EnglishActivity.class); intent.putExtra("activity", "0"); break;
            case R.id.buttonHimiya: intent = new Intent(School.this, EnglishActivity.class); intent.putExtra("activity", "1"); break;
            case R.id.buttonBiologiya: intent = new Intent(School.this, EnglishActivity.class); intent.putExtra("activity", "2"); break;
        }
        startActivity(intent);
    }
}
