package com.example.student3.myfavouritepet.HelpClasses.States;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.Log;
import android.view.Display;

import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;
import com.example.student3.myfavouritepet.HelpClasses.Food.BaseFood;

import java.util.List;
import static com.example.student3.myfavouritepet.HelpClasses.Animation.PrepareForAnimation.RotateBitmap;

public class Feed extends BaseState {

    public Feed(Context context, BaseFood food){
        this.context = context;
        this.food = food;
        name = "Сытость: ";
        wordsOfGratitude = new String[]{"Спасибо!", "Очень вкусно!", "Благодарю!", "Мням, мням!"};
        bitmapId = food.getFoodBitmapId();
        cost = food.getCost();
    }

    @Override
    public Bitmap GetBitmap() {
        bmSprite = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bmSprite = Bitmap.createScaledBitmap(bmSprite, 100, 100, false);
        bmSprite = RotateBitmap(bmSprite, 90);
        return bmSprite;
    }

    @Override
    public List<PointF> GetAnimationPath(Display display) {
        final int tochka = display.getWidth() / 2 + 100;
        final int displayHeight = display.getHeight();
        bmSpritePoints.add(new PointF(tochka, displayHeight - 100));
        bmSpritePoints.add(new PointF(tochka, displayHeight - 200));
        bmSpritePoints.add(new PointF(tochka, displayHeight - 300));
        bmSpritePoints.add(new PointF(tochka, displayHeight - 400));
        return bmSpritePoints;
    }

    @Override
    public String getTiredMsg() {
        return "Я наелся!";
    }

    @Override
    public String getNotifyMsg() {
        return "Покорми меня";
    }

    @Override
    public void MinusFood(BaseFood food) throws ZeroCountFoodException {
        food.setCurrentCount((byte) (food.getCurrentCount() - 1));
    }
}
