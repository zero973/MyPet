package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Intent;
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

public class StorageActivity extends Activity implements View.OnClickListener{

    TextView tvCountWatermelon, tvCountPear, tvCountStrawberry, tvCountApple, tvCountLemon, tvCountMorkov, tvCountPotato, tvCountIcecream;
    ImageButton IBplusWaterMelon, IBplusPear, IBplusStrawberry, IBplusApple, IBplusLemon, IBplusMorkov, IBplusPotato, IBplusIcecream;

    byte[] counts = new byte[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_activity);
        tvCountWatermelon = (TextView)findViewById(R.id.tvCountWatermelon);
        tvCountPear = (TextView)findViewById(R.id.tvCountPear);
        tvCountStrawberry = (TextView)findViewById(R.id.tvCountStrawberry);
        tvCountApple = (TextView)findViewById(R.id.tvCountApple);
        tvCountLemon = (TextView)findViewById(R.id.tvCountLemon);
        tvCountMorkov = (TextView)findViewById(R.id.tvCountMorkov);
        tvCountPotato = (TextView)findViewById(R.id.tvCountPotato);
        tvCountIcecream = (TextView)findViewById(R.id.tvCountIcecream);
        IBplusWaterMelon = (ImageButton)findViewById(R.id.plusWaterMelon);
        IBplusPear = (ImageButton)findViewById(R.id.plusPear);
        IBplusStrawberry = (ImageButton)findViewById(R.id.plusStrawberry);
        IBplusApple = (ImageButton)findViewById(R.id.plusApple);
        IBplusLemon = (ImageButton)findViewById(R.id.plusLemon);
        IBplusMorkov = (ImageButton)findViewById(R.id.plusMorkov);
        IBplusPotato = (ImageButton)findViewById(R.id.plusPotato);
        IBplusIcecream = (ImageButton)findViewById(R.id.plusIcecream);
        IBplusWaterMelon.setOnClickListener(this);
        IBplusPear.setOnClickListener(this);
        IBplusStrawberry.setOnClickListener(this);
        IBplusApple.setOnClickListener(this);
        IBplusLemon.setOnClickListener(this);
        IBplusMorkov.setOnClickListener(this);
        IBplusPotato.setOnClickListener(this);
        IBplusIcecream.setOnClickListener(this);
        ReadCounts("CountFood");
        ChangeTVCounts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusWaterMelon: BuyFood(0); break;
            case R.id.plusPear: BuyFood(1);break;
            case R.id.plusStrawberry: BuyFood(2);break;
            case R.id.plusApple: BuyFood(3);break;
            case R.id.plusLemon: BuyFood(4);break;
            case R.id.plusMorkov: BuyFood(5);break;
            case R.id.plusPotato: BuyFood(6);break;
            case R.id.plusIcecream: BuyFood(7);break;
        }
        SaveCounts("CountFood");
    }

    void SaveCounts(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            for (int i = 0; i < 8; i++)
                bw.write(String.format(counts[i] + " "));
            bw.close();
            Log.d("Успех", "Количества сохранены");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ReadCounts(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String countsInFile = br.readLine();
            int j = 0;
            for (int i = 0; i < 8; i++){
                String chislo = "";
                while (countsInFile.charAt(j) != ' '){
                    chislo += countsInFile.charAt(j);
                    j++;
                }
                counts[i] = Byte.valueOf(chislo);
                j++;
            }
            Log.d("Успех", "Количества загружены");
        }catch (FileNotFoundException e) {
            SaveCounts("CountFood");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    void BuyFood(int FoodIndex){
        switch (FoodIndex){
            case 0: if(Room.money - 15 > -1) {Room.money -= 15; CheckFood(0);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show(); break;
            case 1: if(Room.money - 5 > -1) {Room.money -= 5; CheckFood(1);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 2: if(Room.money - 8 > -1) {Room.money -= 8; CheckFood(2);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 3: if(Room.money - 3 > -1) {Room.money -= 3; CheckFood(3);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 4: if(Room.money - 10 > -1) {Room.money -= 10; CheckFood(4);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 5: if(Room.money - 10 > -1) {Room.money -= 10; CheckFood(5);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 6: if(Room.money - 7 > -1) {Room.money -= 7; CheckFood(6);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
            case 7: if(Room.money - 25 > -1) {Room.money -= 25; CheckFood(7);} else Toast.makeText(getApplicationContext(), "Не хватает монет!", Toast.LENGTH_SHORT).show();break;
        }
        ChangeTVCounts();
    }

    void ChangeTVCounts(){
        tvCountWatermelon.setText(""+counts[0]);
        tvCountPear.setText(""+counts[1]);
        tvCountStrawberry.setText(""+counts[2]);
        tvCountApple.setText(""+counts[3]);
        tvCountLemon.setText(""+counts[4]);
        tvCountMorkov.setText(""+counts[5]);
        tvCountPotato.setText(""+counts[6]);
        tvCountIcecream.setText(""+counts[7]);
    }

    void CheckFood(int foodIndex){
        if(counts[foodIndex]+1 < 101)
            counts[foodIndex]++;
        else
            Toast.makeText(getApplicationContext(), "На складе больше не помещается!", Toast.LENGTH_SHORT).show();
    }
}
