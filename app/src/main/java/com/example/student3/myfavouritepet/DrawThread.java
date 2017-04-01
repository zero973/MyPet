package com.example.student3.myfavouritepet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.ImageView;

public class DrawThread extends Thread{

    SurfaceHolder surfaceHolder;
    private volatile boolean running = true;//флаг для остановки потока
    Bitmap backgroundPaint;
    Bitmap Arm;
    ImageView paintKind;
    Display display;
    Paint paint = new Paint();
    private int towardPointX;
    private int towardPointY;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        this.surfaceHolder = surfaceHolder;
        Arm = BitmapFactory.decodeResource(context.getResources(), R.drawable.arm);
        switch (Room.roomColor){
            case "Синяя": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueroom);break;
            case "Коричневая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.brownroom);break;
            case "Голубая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_whiteroom);break;
            case "Жёлтая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowroom);break;
            case "Алая": backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.alayaroom);break;
        }
        paintKind = new ImageView(context);
        switch (Room.kind) {
            case "Собака":paintKind.setBackgroundResource(R.drawable.petdog); break;
            case "Кошка":paintKind.setBackgroundResource(R.drawable.petcat);break;
            case "Заяц":paintKind.setBackgroundResource(R.drawable.petrabbit);break;
            case "Черепаха":paintKind.setBackgroundResource(R.drawable.petturtle);break;
            case "Попугай":paintKind.setBackgroundResource(R.drawable.petparrot);break;
        }
        //backgroundPaint.setHeight(display.getHeight()); не работает
        //backgroundPaint.setWidth(display.getWidth());
    }

    void requestStop() {
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
            paintKind.setMaxWidth(50); paintKind.setMaxHeight(50);
            if (canvas != null) {
                try {
                    canvas.drawBitmap(backgroundPaint, 0, 0, paint);
                    //canvas.drawPicture(paintKind, display.getHeight()/2/2/2, display.getWidth()-25, paint);
                    canvas.drawBitmap(Arm, 100, 100, paint);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
