package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class HealthActivity extends Activity implements View.OnClickListener {

    ImageButton IBPlusBall; TextView tvBallCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_activity);
        IBPlusBall = (ImageButton)findViewById(R.id.plusBall);
        IBPlusBall.setOnClickListener(this);
        tvBallCost = (TextView)findViewById(R.id.TextViewBallCost);
        if (ReadPurch("HealthPurch") == true){
            IBPlusBall.setBackgroundResource(Integer.parseInt(null));
            tvBallCost.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusBall: if (Room.money -500 > -1){
                Room.money -=500;
                SavePurchases("HealthPurch", "1");
                IBPlusBall.setBackgroundResource(Integer.parseInt(null));
                tvBallCost.setText("0");
            } else {Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();}
                break;
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
