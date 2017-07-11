package com.example.student3.myfavouritepet.HelpClasses.States;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.Display;
import com.example.student3.myfavouritepet.R;
import java.util.List;
import static com.example.student3.myfavouritepet.HelpClasses.Animation.PrepareForAnimation.RotateBitmap;

public class Solicitude extends BaseState {

    private Context context;

    public Solicitude(Context context){
        this.context = context;
        name = "Забота: ";
        wordsOfGratitude = new String[]{":-)", ";-)"};
        bitmapId = R.drawable.arm;
        cost = 3;
    }

    @Override
    public Bitmap GetBitmap() {
        bmSprite = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bmSprite = Bitmap.createScaledBitmap(bmSprite, 100, 150, false);
        bmSprite = RotateBitmap(bmSprite, -90);
        return bmSprite;
    }

    @Override
    public List<PointF> GetAnimationPath(Display display) {
        int paintKindX = display.getWidth() / 2 - 150, paintKindY = display.getHeight() / 2 - 100;
        bmSpritePoints.add(new PointF(paintKindX + 200, paintKindY - 50));
        bmSpritePoints.add(new PointF(paintKindX + 100, paintKindY - 50));
        bmSpritePoints.add(new PointF(paintKindX, paintKindY - 50));
        return bmSpritePoints;
    }

    @Override
    public String getTiredMsg() {
        return "Я наигрался";
    }

    @Override
    public String getNotifyMsg() {
        return "Погладь меня.";
    }
}
