package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MathActivity extends AppCompatActivity {

    Button btnExamples, btnDiscrim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_activity);
        btnExamples = (Button)findViewById(R.id.buttonExamplesMath);
        btnDiscrim = (Button)findViewById(R.id.buttonDiscriminant);

        btnExamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathActivity.this, MathActivityExamples.class);
                startActivity(intent);
            }
        });

        btnDiscrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathActivity.this, MathActivityDescrim.class);
                startActivity(intent);
            }
        });
    }
}
