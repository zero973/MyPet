package com.example.student3.myfavouritepet;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SceneView extends View {
    private static Bitmap bmSprite, paintKind, backgroundPaint;
    private static Rect rSrc, rDest;

    //animation step
    private static int iMaxAnimationStep = 75;
    private int iCurStep = 0;

    //points defining our curve
    private List<PointF> aPoints = new ArrayList<PointF>();
    private Paint paint;
    private Path ptCurve = new Path(); //curve
    private PathMeasure pm;            //curve measure
    private float fSegmentLen;         //curve segment
    Display display;
    public static byte WhoCalled = 1;
    byte elementCount = 0, elementId = 0;

    public SceneView(Context context) {
        super(context);
        //destination rectangle
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        rDest = new Rect(0, 0, display.getWidth(), display.getHeight());

        //load background
        switch (Room.roomColor) {
            case "Синяя":
                backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueroom);
                break;
            case "Коричневая":
                backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.brownroom);
                break;
            case "Голубая":
                backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_whiteroom);
                break;
            case "Жёлтая":
                backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellowroom);
                break;
            case "Алая":
                backgroundPaint = BitmapFactory.decodeResource(context.getResources(), R.drawable.alayaroom);
                break;
        }
        rSrc = new Rect(0, 0, backgroundPaint.getWidth(), backgroundPaint.getHeight());


        //load pet
        switch (Room.kind) {
            case "Собака":
                paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petdog);
                break;
            case "Кошка":
                paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petcat);
                break;
            case "Заяц":
                paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petrabbit);
                break;
            case "Черепаха":
                paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petturtle);
                break;
            case "Попугай":
                paintKind = BitmapFactory.decodeResource(context.getResources(), R.drawable.petparrot);
                break;
        }
        paintKind = Bitmap.createScaledBitmap(paintKind, 300, 300, false);

        if (WhoCalled == 1) {
            byte indexArmOrBall = 0;
            indexArmOrBall = HeartActivity.ArmOrBall;
            switch (indexArmOrBall) {
                case 1:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.arm);
                    bmSprite = Bitmap.createScaledBitmap(bmSprite, 100, 150, false);
                    bmSprite = RotateBitmap(bmSprite, -90);
                    iMaxAnimationStep = 75;
                    break;
                case 2:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
                    bmSprite = Bitmap.createScaledBitmap(bmSprite, 150, 150, false);
                    break;
            }

            if (indexArmOrBall == 1) {
                aPoints.add(new PointF(350f, 350f));
                aPoints.add(new PointF(300f, 350f));
                aPoints.add(new PointF(200f, 350f));
                aPoints.add(new PointF(100f, 350f));
            } else {
                iMaxAnimationStep = 100;
                aPoints.add(new PointF(100f, 200f));
                aPoints.add(new PointF(150f, 250f));
                //aPoints.add(new PointF(200f, 250f));
                aPoints.add(new PointF(300f, 410f));
                aPoints.add(new PointF(400f, 410f));
                aPoints.add(new PointF(600f, 700f));
                aPoints.add(new PointF(700f, 700f));
            }
        } else {
            switch (StorageActivity.FoodIndex) {
                case 1:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.watermelon);
                    elementCount = StorageActivity.counts[0];
                    elementId = 0;
                    break;
                case 2:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.pear);
                    elementCount = StorageActivity.counts[1];
                    elementId = 1;
                    break;
                case 3:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.strawberry);
                    elementCount = StorageActivity.counts[2];
                    elementId = 2;
                    break;
                case 4:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
                    elementCount = StorageActivity.counts[3];
                    elementId = 3;
                    break;
                case 5:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.lemon);
                    elementCount = StorageActivity.counts[4];
                    elementId = 4;
                    break;
                case 6:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.morkovka);
                    elementCount = StorageActivity.counts[5];
                    elementId = 5;
                    break;
                case 7:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.potato);
                    elementCount = StorageActivity.counts[6];
                    elementId = 6;
                    break;
                case 8:
                    bmSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.icecream);
                    elementCount = StorageActivity.counts[7];
                    elementId = 7;
                    break;
            }
            iMaxAnimationStep = 75;
            bmSprite = Bitmap.createScaledBitmap(bmSprite, 100, 100, false);
            bmSprite = RotateBitmap(bmSprite, 90);
            aPoints.add(new PointF(350f, 700f));
            aPoints.add(new PointF(350f, 600f));
            aPoints.add(new PointF(350f, 500f));
            aPoints.add(new PointF(350f, 400f));

        }
        //init smooth curve
        PointF point = aPoints.get(0);
        ptCurve.moveTo(point.x, point.y);
        for (int i = 0; i < aPoints.size() - 1; i++) {
            point = aPoints.get(i);
            PointF next = aPoints.get(i + 1);
            ptCurve.quadTo(point.x, point.y, (next.x + point.x) / 2, (point.y + next.y) / 2);
        }
        pm = new PathMeasure(ptCurve, false);
        fSegmentLen = pm.getLength() / iMaxAnimationStep;//20 animation steps//init paint object
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(0, 148, 255));
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundPaint, rSrc, rDest, null);
        canvas.drawBitmap(paintKind, display.getHeight() / 2 / 2 / 2, display.getWidth() - 100, paint);
        //canvas.drawPath(ptCurve, paint); //animate the sprite
        Matrix mxTransform = new Matrix();
        if (iCurStep <= iMaxAnimationStep) {
            pm.getMatrix(fSegmentLen * iCurStep, mxTransform, PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG);
            mxTransform.preTranslate(-bmSprite.getWidth(), -bmSprite.getHeight());
            canvas.drawBitmap(bmSprite, mxTransform, null);
            iCurStep++; //advance to the next step
            invalidate();
        } else {
            iCurStep = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("elementCount", ""+elementCount);
        if (elementCount > 0) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) { //run animation
                elementCount--;
                StorageActivity.counts[elementId]--;
                invalidate();
                return true;
            }
            return false;
        } else {
            Toast.makeText(getContext(), "Еда кончилась!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
