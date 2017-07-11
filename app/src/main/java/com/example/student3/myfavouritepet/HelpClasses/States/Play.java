package com.example.student3.myfavouritepet.HelpClasses.States;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.Display;

import com.example.student3.myfavouritepet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Play extends BaseState{

    private Random random = new Random();

    private int[] balls = {R.drawable.ball, R.drawable.ball2, R.drawable.ball3};

    public Play(Context context){
        name = "Развлечение: ";
        wordsOfGratitude = new String[]{":-)", ";-)"};
        ChooseBitmap();
        iMaxAnimationStep = 100;
        cost = 10;
    }

    private void ChooseBitmap(){
        bitmapId = balls[random.nextInt(balls.length)];
    }

    @Override
    public Bitmap GetBitmap() {
        bmSprite = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bmSprite = Bitmap.createScaledBitmap(bmSprite, 150, 150, false);
        return bmSprite;
    }

    @Override
    public List<PointF> GetAnimationPath(Display display){
        List<PointF> bmSpritePoints = new ArrayList<>();
        bmSpritePoints.add(new PointF(100f, 200f));
        bmSpritePoints.add(new PointF(150f, 250f));
        bmSpritePoints.add(new PointF(400f, 500f));
        bmSpritePoints.add(new PointF(500f, 600f));
        bmSpritePoints.add(new PointF(600f, 700f));
        bmSpritePoints.add(new PointF(700f, 800f));
        bmSpritePoints.add(new PointF(800f, 900f));
        return bmSpritePoints;
    }

    @Override
    public String getTiredMsg() {
        return "Я наигрался!";
    }

    @Override
    public String getNotifyMsg() {
        return "Поиграй со мной";
    }
}
