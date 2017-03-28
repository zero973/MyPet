package com.example.student3.myfavouritepet;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import static com.example.student3.myfavouritepet.Room.kind;
import static com.example.student3.myfavouritepet.Room.roomColor;

public class DrawThread extends Thread{

    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;//флаг для остановки потока
    private Bitmap backgroundPaint;
    private Bitmap Arm;
    Bitmap paintKind;
    Paint paint = new Paint();
    private int towardPointX;
    private int towardPointY;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        this.surfaceHolder = surfaceHolder;
        Arm = BitmapFactory.decodeResource(context.getResources(), R.drawable.arm);
        backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.arm);
        switch (roomColor){
            case "Синяя": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueroom);break;
            case "Коричневая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.brownroom);break;
            case "Розовая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinkroom);break;
            case "Голубая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_whiteroom);break;
            case "Жёлтая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowroom);break;
            case "Алая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.alayaroom);break;
        }

        switch (kind) {
            case "Собака":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petdog);break;
            case "Кошка":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petcat);break;
            case "Заяц":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petrabbit);break;
            case "Черепаха":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petturtle);break;
            case "Попугай":paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petparrot);break;
        }
        //backgroundPaint.setHeight(display.getHeight());
        //backgroundPaint.setWidth(display.getWidth());
    }

    public void requestStop() {
        running = false;
    }

    public void setTowardPoint(int x, int y) {
        towardPointX = x;
        towardPointY = y;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawBitmap(backgroundPaint, 0, 0, paint);
                    canvas.drawBitmap(paintKind, 0, 0, paint);
                    canvas.drawBitmap(Arm, 100, 100, paint);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
