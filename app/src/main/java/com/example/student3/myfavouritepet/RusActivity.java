package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RusActivity extends AppCompatActivity {

    Button btnGram, btnUdarenie, btnAnagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rus_activity);

        btnGram = (Button)findViewById(R.id.buttonGramm);
        btnUdarenie = (Button)findViewById(R.id.buttonUdarenie);
        btnAnagram = (Button)findViewById(R.id.buttonAnagrams);

        btnGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RusActivity.this, RusActivityGrammatica.class);
                startActivity(intent);
            }
        });

        btnAnagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RusActivity.this, RusActivity_anagrams.class);
                startActivity(intent);
            }
        });

        btnUdarenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RusActivity.this, RusActivity_Udarenie.class);
                startActivity(intent);
            }
        });
    }
}
