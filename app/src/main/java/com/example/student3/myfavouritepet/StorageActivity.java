package com.example.student3.myfavouritepet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    ImageButton IBWaterMelon, IBPear, IBStrawberry, IBApple, IBLemon, IBMorkov, IBPotato, IBIcecream;

    public static byte[] counts = new byte[8];
    byte[] foodCosts = {15, 5, 8, 3, 10, 10, 7, 25};
    public static byte FoodIndex = 0;

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
        IBWaterMelon = (ImageButton)findViewById(R.id.Watermelon);
        IBPear = (ImageButton)findViewById(R.id.Pear);
        IBStrawberry = (ImageButton)findViewById(R.id.Strawberry);
        IBApple = (ImageButton)findViewById(R.id.Apple);
        IBLemon = (ImageButton)findViewById(R.id.Lemon);
        IBMorkov = (ImageButton)findViewById(R.id.Morkov);
        IBPotato = (ImageButton)findViewById(R.id.Potato);
        IBIcecream = (ImageButton)findViewById(R.id.Icecream);
        IBWaterMelon.setOnClickListener(this);
        IBPear.setOnClickListener(this);
        IBStrawberry.setOnClickListener(this);
        IBApple.setOnClickListener(this);
        IBLemon.setOnClickListener(this);
        IBMorkov.setOnClickListener(this);
        IBPotato.setOnClickListener(this);
        IBIcecream.setOnClickListener(this);
        ReadCounts("CountFood");
        ChangeTVCounts();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        SceneView.WhoCalled = 2;
        switch (v.getId()) {
            case R.id.plusWaterMelon: BuyFood(0); break;
            case R.id.plusPear: BuyFood(1);break;
            case R.id.plusStrawberry: BuyFood(2);break;
            case R.id.plusApple: BuyFood(3);break;
            case R.id.plusLemon: BuyFood(4);break;
            case R.id.plusMorkov: BuyFood(5);break;
            case R.id.plusPotato: BuyFood(6);break;
            case R.id.plusIcecream: BuyFood(7);break;
            case R.id.Watermelon: if (counts[0] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 1;}else ShowToast("Сначала купите еду!", getApplicationContext());break;
            case R.id.Pear: if (counts[1] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 2;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Strawberry: if (counts[2] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 3;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Apple: if (counts[3] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 4;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Lemon: if (counts[4] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 5;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Morkov: if (counts[5] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 6;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Potato: if (counts[6] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 7;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
            case R.id.Icecream: if (counts[7] > 0) {intent = new Intent(this, CaressActivity.class); FoodIndex = 8;}else ShowToast("Сначала купите еду!", getApplicationContext()); break;
        }
        SaveCounts("CountFood");
        if (intent != null) startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ChangeTVCounts();
    }

    void SaveCounts(String fileName){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            for (int i = 0; i < 8; i++)
                bw.write(String.format(counts[i] + " "));
            bw.close();
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
        }catch (FileNotFoundException e) {
            SaveCounts("CountFood");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    void BuyFood(int FoodIndex){
        if (Room.money - foodCosts[FoodIndex] > -1)
            if (CheckFoodOnOverflow(FoodIndex))
                Room.money -= foodCosts[FoodIndex];
            else
                ShowToast("На складе больше не помещается!", this);
        else
            ShowToast("Не хватает монет!", this);

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

    boolean CheckFoodOnOverflow(int foodIndex){
        if(counts[foodIndex]+1 < 101) {
            counts[foodIndex]++;
            return true;
        }
        else
            return false;
    }

    public static void ShowToast(String text, Context context){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
