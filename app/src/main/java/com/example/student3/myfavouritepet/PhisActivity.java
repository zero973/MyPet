package com.example.student3.myfavouritepet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PhisActivity extends AppCompatActivity {

    Button btnQuestions, btnExamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phis_activity);

        btnQuestions = (Button)findViewById(R.id.buttonQuestionsPhis);
        btnExamples = (Button)findViewById(R.id.buttonExamplesPhis);

        btnQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhisActivity.this, PhisActivityFirstLevel.class);
                startActivity(intent);
            }
        });

        btnExamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhisActivity.this, PhisActivityTasks.class);
                startActivity(intent);
            }
        });
    }
}
