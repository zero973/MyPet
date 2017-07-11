package com.example.student3.myfavouritepet.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.student3.myfavouritepet.R;

public class PhisActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phis_activity);
        Button QuestionsPhis = (Button)findViewById(R.id.buttonQuestionsPhis);
        QuestionsPhis.setOnClickListener(this);
        Button ExamplesPhis = (Button)findViewById(R.id.buttonExamplesPhis);
        ExamplesPhis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.buttonQuestionsPhis: intent = new Intent(PhisActivity.this, PhisActivityFirstLevel.class); break;
            case R.id.buttonExamplesPhis: intent = new Intent(PhisActivity.this, PhisActivityTasks.class); break;
        }
        startActivity(intent);
    }
}
