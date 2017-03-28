package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class HeartActivity extends Activity implements View.OnClickListener {

    ImageButton IBPlusBall, IBArm, IBBall; TextView tvBallCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_activity);
        IBPlusBall = (ImageButton)findViewById(R.id.plusBall);
        IBPlusBall.setOnClickListener(this);
        IBArm = (ImageButton)findViewById(R.id.arm);
        IBArm.setOnClickListener(this);
        IBBall = (ImageButton)findViewById(R.id.ball);
        IBBall.setOnClickListener(this);
        tvBallCost = (TextView)findViewById(R.id.TextViewBallCost);
        if (ReadPurch("HealthPurch") == true){
            IBPlusBall.setBackgroundResource(Integer.parseInt(null));
            tvBallCost.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.plusBall: if (Room.money -500 > -1){
                Room.money -=500;
                SavePurchases("HealthPurch", "1");
                IBPlusBall.setBackgroundResource(Integer.parseInt(null));
                tvBallCost.setText("0");
            } else {Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.arm: intent = new Intent(this, CaressActivity.class); startActivity(intent); break;//дописать putExtra
            case R.id.ball: intent = new Intent(this, CaressActivity.class); startActivity(intent); break;//дописать putExtra
        }
    }

    void SavePurchases(String fileName, String Zero_Or_One){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            bw.write(Zero_Or_One);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean ReadPurch(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String countsInFile = br.readLine();
            if (countsInFile.equals("1"))
                return true;
            else
                return false;
        }catch (FileNotFoundException e) {
            SavePurchases("HealthPurch", "0");
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
