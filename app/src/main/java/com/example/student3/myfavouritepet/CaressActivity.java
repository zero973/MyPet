package com.example.student3.myfavouritepet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CaressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SceneView(this));
    }
}
