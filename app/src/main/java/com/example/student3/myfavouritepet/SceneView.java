package com.example.student3.myfavouritepet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SceneView extends View {
    private static Bitmap bmSprite, paintKind, backgroundPaint, bitmapHeart;
    private static Rect rSrc, rDest;

    //animation step
    private static int iMaxAnimationStep = 75;
    private int iCurStep = 0;

    //points defining our curve
    private List<PointF> bmSpritePoints = new ArrayList(), HeartPoints = new ArrayList();
    private Paint paint;
    private Path ptCurve = new Path(); //curve
    private PathMeasure pm;            //curve measure
    private float fSegmentLen;//curve segment
    private Display display;
    public static byte WhoCalled = 1;
    private byte elementCount = 120, elementId = 0;
    private Context context;

    private int paintKindX, paintKindY;

    public SceneView(Context context) {
        super(context);
        this.context = context;

        SetBackgroundSizeAndPetPoints();
        LoadBackground();
        LoadPet();
        if (WhoCalled == 1)
            LoadHeartAndSetAnimation();

        SwitchWhoCalled();
        CreatePathAnimation(bmSpritePoints);

        MinusFood();
        if (WhoCalled == 2)
            Toast.makeText(context, "Очень вкусно!", Toast.LENGTH_SHORT).show();
    }

    private void SetBackgroundSizeAndPetPoints(){
        //destination rectangle
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        rDest = new Rect(0, 0, display.getWidth(), display.getHeight());
        paintKindX = display.getWidth() /2 - 150;
        paintKindY = display.getHeight() / 2 - 100;
    }

    private void LoadPet(){
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
        paintKind = Bitmap.createScaledBitmap(paintKind, 300, 340, false);
    }

    private void LoadBackground(){
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
    }

    private void LoadHeartAndSetAnimation(){
        bitmapHeart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        bitmapHeart = Bitmap.createScaledBitmap(bitmapHeart, 100, 100, false);
        bitmapHeart = RotateBitmap(bitmapHeart, -180);
        HeartPoints.add(new PointF(paintKindX, paintKindY));
        HeartPoints.add(new PointF(paintKindX, paintKindY + 50));
        HeartPoints.add(new PointF(paintKindX, paintKindY + 100));
        HeartPoints.add(new PointF(paintKindX, paintKindY + 150));
        HeartPoints.add(new PointF(paintKindX, paintKindY + 200));
    }

    private void SwitchWhoCalled(){
        if (WhoCalled == 1) {
            SwitchIndexArmOrBallAndSetAnimation();
        } else {
            SwitchFoodIndexAndSetAnimationForFood();
        }
    }

    private void SwitchIndexArmOrBallAndSetAnimation(){
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
            bmSpritePoints.add(new PointF(paintKindX+200, paintKindY-50));
            bmSpritePoints.add(new PointF(paintKindX+100, paintKindY-50));
            bmSpritePoints.add(new PointF(paintKindX, paintKindY-50));
        } else {
            iMaxAnimationStep = 100;
            bmSpritePoints.add(new PointF(100f, 200f));
            bmSpritePoints.add(new PointF(150f, 250f));
            bmSpritePoints.add(new PointF(300f, 410f));
            bmSpritePoints.add(new PointF(400f, 410f));
            bmSpritePoints.add(new PointF(600f, 700f));
            bmSpritePoints.add(new PointF(700f, 700f));
        }
    }

    private void SwitchFoodIndexAndSetAnimationForFood(){
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
        final int tochka = display.getWidth()/2+100;
        final int displayHeight = display.getHeight();
        bmSpritePoints.add(new PointF(tochka, displayHeight-100));
        bmSpritePoints.add(new PointF(tochka, displayHeight-200));
        bmSpritePoints.add(new PointF(tochka, displayHeight-300));
        bmSpritePoints.add(new PointF(tochka, displayHeight-400));
    }

    private void CreatePathAnimation(List<PointF> aPoints){
        PointF point = aPoints.get(0);
        ptCurve.moveTo(point.x, point.y);
        for (int i = 0; i < aPoints.size() - 1; i++) {
            point = aPoints.get(i);
            PointF next = aPoints.get(i + 1);
            ptCurve.quadTo(point.x, point.y, (next.x + point.x) / 2, (point.y + next.y) / 2);
        }
        pm = new PathMeasure(ptCurve, false);
        fSegmentLen = pm.getLength() / iMaxAnimationStep;//20 animation steps//init paint object
        /*paint = new Paint(Paint.ANTI_ALIAS_FLAG);//Задаёт путь анимации
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(0, 148, 255));*/
    }

    private void MinusFood(){
        elementCount--;
        StorageActivity.counts[elementId]--;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundPaint, rSrc, rDest, null);
        canvas.drawBitmap(paintKind, paintKindX, paintKindY, null);
        //canvas.drawPath(ptCurve, paint); //рисет путь анимации
        if (!IsAnimationbmSpriteEnd) {
            DrawAnimation(canvas, bmSprite);
        }
        if (WhoCalled == 1 && IsAnimationbmSpriteEnd) {
            DrawAnimation(canvas, bitmapHeart);
        }
    }

    boolean IsAnimationbmSpriteEnd = false;

    private void DrawAnimation(Canvas canvas, Bitmap sprite){
        Matrix mxTransform = new Matrix();
        if (iCurStep <= iMaxAnimationStep) {
            pm.getMatrix(fSegmentLen * iCurStep, mxTransform, PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG);
            mxTransform.preTranslate(-sprite.getWidth(), -sprite.getHeight());
            canvas.drawBitmap(sprite, mxTransform, null);
            iCurStep++; //advance to the next step
            invalidate();
        } else {
            iCurStep = 0;
            if (WhoCalled == 1 && sprite.equals(bmSprite)) {
                IsAnimationbmSpriteEnd = true;
                CreatePathAnimation(HeartPoints);
            }else {
                IsAnimationbmSpriteEnd = false;
                CreatePathAnimation(bmSpritePoints);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (WhoCalled == 2 && elementCount > 0)
            ShowThankForEat();
        if (elementCount > 0) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {//run animation
                MinusFood();
                invalidate();
                return true;
            }
            return false;
        } else {
            Toast.makeText(getContext(), "Еда кончилась!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    int i = 0, b = 0;
    String[] wordsMassEat = {"Спасибо!", "Очень вкусно!", "Благодарю!"};

    private void ShowThankForEat(){
        if (i % 2 != 0) {
            if (wordsMassEat.length == b)
                b = 0;
            Toast.makeText(getContext(), wordsMassEat[b], Toast.LENGTH_SHORT).show();
            b++;
        }
        i++;
    }
}
