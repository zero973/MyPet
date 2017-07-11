package com.example.student3.myfavouritepet.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.student3.myfavouritepet.R;

public class MathActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_activity);
        Button ExamplesMath = (Button)findViewById(R.id.buttonExamplesMath);
        ExamplesMath.setOnClickListener(this);
        Button Dicscriminant = (Button)findViewById(R.id.buttonDiscriminant);
        Dicscriminant.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.buttonExamplesMath: intent = new Intent(MathActivity.this, MathActivityExamples.class); break;
            case R.id.buttonDiscriminant: intent = new Intent(MathActivity.this, MathActivityDescrim.class); break;
        }
        startActivity(intent);
    }
}
