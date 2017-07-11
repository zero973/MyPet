package com.example.student3.myfavouritepet.HelpClasses.States;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.Display;
import android.widget.Toast;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.TiredException;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;
import com.example.student3.myfavouritepet.HelpClasses.Food.BaseFood;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseState {

    protected Context context;

    protected List<PointF> bmSpritePoints = new ArrayList<>();
    protected Bitmap bmSprite;

    protected String[] wordsOfGratitude;

    protected String name;
    protected int cost = 0;
    protected int bitmapId = 0;
    protected int iMaxAnimationStep = 75;
    private int fullness = 50;

    protected BaseFood food;

    public abstract Bitmap GetBitmap();

    public abstract List<PointF> GetAnimationPath(Display display);

    public abstract String getTiredMsg();

    public abstract String getNotifyMsg();

    public enum Operation{
        Plus, Minus
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(Operation operation) throws TiredException {
        switch (operation){
            case Plus: if (fullness + cost < 85) fullness += cost; else throw new TiredException(); break;
            case Minus: if (fullness - cost > 0) fullness -= 1; break;
        }
    }

    private int i = 0, b = 0;
    public void ShowGratitude(Context context) {
        if (i % 2 != 0) {
            if (wordsOfGratitude.length == b)
                b = 0;
            Toast.makeText(context, wordsOfGratitude[b], Toast.LENGTH_SHORT).show();
            b++;
        }
        i++;
    }

    public void MinusFood(BaseFood food) throws ZeroCountFoodException {}

    public BaseFood getFood() {
        return food;
    }

    public String getName() {
        return name;
    }

    public void setFood(BaseFood food){
        this.food = food;
    }
}
