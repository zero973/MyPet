package com.example.student3.myfavouritepet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class DrawThread extends Thread{

    SurfaceHolder surfaceHolder;
    private volatile boolean running = true;//флаг для остановки потока
    Bitmap backgroundPaint, ArmOrBall, paintKind;
    Display display;
    Paint paint = new Paint();
    byte indexArmOrBall = 0;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        this.surfaceHolder = surfaceHolder;
        indexArmOrBall = HeartActivity.ArmOrBall;
        switch (indexArmOrBall){
            case 1: ArmOrBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.arm); ArmOrBall = Bitmap.createScaledBitmap(ArmOrBall, 100, 150, false); break;
            case 2: ArmOrBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball); ArmOrBall = Bitmap.createScaledBitmap(ArmOrBall, 150, 150, false);break;
        }
        switch (Room.roomColor){
            case "Синяя": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueroom);break;
            case "Коричневая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.brownroom);break;
            case "Голубая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_whiteroom);break;
            case "Жёлтая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowroom);break;
            case "Алая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.alayaroom);break;
        }
        switch (Room.kind) {
            case "Собака":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petdog); break;
            case "Кошка":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petcat);break;
            case "Заяц":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petrabbit);break;
            case "Черепаха":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petturtle);break;
            case "Попугай":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petparrot);break;
        }
        backgroundPaint = Bitmap.createScaledBitmap(backgroundPaint, display.getWidth(), display.getHeight(), false);
        paintKind = Bitmap.createScaledBitmap(paintKind, 300, 300, false);
    }

    void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawBitmap(backgroundPaint, 0, 0, paint);
                    canvas.drawBitmap(paintKind, display.getHeight() / 2 / 2, display.getWidth() - 25, paint);
                    canvas.drawBitmap(ArmOrBall, 100, 100, paint);
                }
                finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
