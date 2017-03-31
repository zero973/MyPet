package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RusActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rus_activity);
        Button Gramm = (Button)findViewById(R.id.buttonGramm);
        Gramm.setOnClickListener(this);
        Button Udarenie = (Button)findViewById(R.id.buttonUdarenie);
        Udarenie.setOnClickListener(this);
        Button Anagramma = (Button)findViewById(R.id.buttonAnagrams);
        Anagramma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.buttonGramm: intent = new Intent(RusActivity.this, EnglishActivity.class); intent.putExtra("activity", "3"); break;
            case R.id.buttonUdarenie: intent = new Intent(RusActivity.this, EnglishActivity.class); intent.putExtra("activity", "4"); break;
            case R.id.buttonAnagrams: intent = new Intent(RusActivity.this, RusActivity_anagrams.class); break;
        }
        startActivity(intent);
    }
}
