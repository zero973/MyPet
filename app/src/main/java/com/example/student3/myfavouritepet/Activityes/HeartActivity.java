package com.example.student3.myfavouritepet.Activityes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.student3.myfavouritepet.HelpClasses.Animation.PrepareForAnimation;
import com.example.student3.myfavouritepet.HelpClasses.Service.Pet;
import com.example.student3.myfavouritepet.HelpClasses.States.Solicitude;
import com.example.student3.myfavouritepet.HelpClasses.States.Play;
import com.example.student3.myfavouritepet.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class HeartActivity extends Activity implements View.OnClickListener {

    private ImageButton IBPlusBall, IBPlusMusic, IBArm, IBBall, IBMusic;
    private TextView tvBallCost, tvMusicCost;
    private boolean IsBallBought = false, IsMusicBought = false;

    public static MediaPlayer mp;
    private ThreadMusic tm = new ThreadMusic();

    private byte[] purchMass = new byte[2];
    private boolean IsMusicPlay = false;

    private void main(){
        setContentView(R.layout.heart_activity);
        IBPlusBall = (ImageButton) findViewById(R.id.plusBall);
        IBPlusBall.setOnClickListener(this);
        IBArm = (ImageButton) findViewById(R.id.arm);
        IBArm.setOnClickListener(this);
        IBBall = (ImageButton) findViewById(R.id.ball);
        IBBall.setOnClickListener(this);
        IBPlusMusic = (ImageButton) findViewById(R.id.plusMusic);
        IBPlusMusic.setOnClickListener(this);
        IBMusic = (ImageButton) findViewById(R.id.Music);
        IBMusic.setOnClickListener(this);
        tvBallCost = (TextView) findViewById(R.id.TextViewBallCost);
        tvMusicCost = (TextView) findViewById(R.id.TextViewMusicCost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
        ReadPurch("HealthPurch");
        if (purchMass[0] == 1) {
            IsBallBought = true;
            IBPlusBall.setVisibility(View.INVISIBLE);
            tvBallCost.setText("0");
        }if (purchMass[1] == 1) {
            IsMusicBought = true;
            IBPlusMusic.setVisibility(View.INVISIBLE);
            tvMusicCost.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.plusBall:
                if (Pet.getMoney() - 100 > -1) {
                    IsBallBought = true;
                    Pet.setMoney(Pet.getMoney() - 100);
                    purchMass[0] = 1;
                    SavePurchases("HealthPurch");
                    IBPlusBall.setVisibility(View.INVISIBLE);
                    tvBallCost.setText("0");
                } else
                    StorageActivity.ShowToast("Не хватает монет!", this);
                break;
            case R.id.plusMusic:
                if (Pet.getMoney() - 20 > -1) {
                    IsMusicBought = true;
                    Pet.setMoney(Pet.getMoney() - 20);
                    purchMass[1] = 1;
                    SavePurchases("HealthPurch");
                    IBPlusMusic.setVisibility(View.INVISIBLE);
                    tvMusicCost.setText("0");
                } else
                    StorageActivity.ShowToast("Не хватает монет!", this);
                break;
            case R.id.arm:
                intent = new Intent(this, PrepareForAnimation.class);
                PrepareForAnimation.setState(Room.states[1]);
                startActivity(intent);
                break;
            case R.id.ball:
                if (IsBallBought) {
                    intent = new Intent(this, PrepareForAnimation.class);
                    PrepareForAnimation.setState(Room.states[2]);
                    startActivity(intent);
                } else
                    StorageActivity.ShowToast("Купите мяч!", this);
                break;
            case R.id.Music:
                if (IsMusicBought)
                    PlayMusic();
                else
                    StorageActivity.ShowToast("Купите музыку!", this);
                break;
        }
    }

    private void PlayMusic(){
        if (!IsMusicPlay) {
            tm.run();
            IsMusicPlay = true;
        }
        else {
            mp.stop();
            IsMusicPlay = false;
        }
    }

    private void SavePurchases(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
            for (int i = 0; i < purchMass.length; i++)
                bw.write(purchMass[i] + " ");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ReadPurch(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String countsInFile = br.readLine();
            byte[] mass = new byte[purchMass.length];
            int i = 0, j = 0;
            while (i < countsInFile.length()) {
                String stroka = "";
                while (countsInFile.charAt(i) != ' ') {
                    stroka += countsInFile.charAt(i);
                    i++;
                }
                mass[j] = Byte.valueOf(stroka);
                j++;
                i++;
            }
            purchMass = mass;
        } catch (FileNotFoundException e) {
            SavePurchases("HealthPurch");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ThreadMusic extends Thread {
        public void run() {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.mysound);
            mp.start();
        }
    }
}
