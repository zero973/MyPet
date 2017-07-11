package com.example.student3.myfavouritepet.HelpClasses.Animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.TiredException;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;
import com.example.student3.myfavouritepet.HelpClasses.Service.Pet;
import com.example.student3.myfavouritepet.HelpClasses.States.BaseState;
import com.example.student3.myfavouritepet.HelpClasses.States.Feed;
import com.example.student3.myfavouritepet.R;

import java.util.ArrayList;
import java.util.List;

public class PrepareForAnimation extends AppCompatActivity {

    private Context context;
    private static BaseState state;
    private Display display;

    private int paintKindX, paintKindY;

    private Rect background;
    private Bitmap bmSprite, paintKind, backgroundPaint, bmHeart;
    private List<PointF> bmSpritePoints = new ArrayList();
    private int iMaxAnimationStep = 75;

    public PrepareForAnimation(){
        //context = activity.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        LoadBackgroundPaint();
        SetBackgroundSize();
        SetPetCoordinates();
        LoadPet();
        LoadHeart();
        LoadbmSprite(state);
        LoadbmSpritePoints(state);

        if (state.getClass() == Feed.class)
            TryMinusFood();
        IncreaseFullness(state);

        setContentView(new AnimationView(this, state, this));
    }

    private void SetBackgroundSize() {
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        background = new Rect(0, 0, backgroundPaint.getWidth(), backgroundPaint.getHeight());
    }

    private void SetPetCoordinates(){
        paintKindX = display.getWidth() / 2 - 150;
        paintKindY = display.getHeight() / 2 - 100;
    }

    private void LoadBackgroundPaint() {
        backgroundPaint = BitmapFactory.decodeResource(context.getResources(), Pet.getRoom());
    }

    private void LoadPet() {
        paintKind = BitmapFactory.decodeResource(context.getResources(), Pet.getKind());
        paintKind = Bitmap.createScaledBitmap(paintKind, 300, 340, false);
    }

    private void LoadHeart(){
        bmHeart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        bmHeart = Bitmap.createScaledBitmap(bmHeart, 100, 100, false);
        bmHeart = RotateBitmap(bmHeart, -180);
    }

    private void LoadbmSprite(BaseState state){
        bmSprite = state.GetBitmap();
    }

    private void LoadbmSpritePoints(BaseState state){
        bmSpritePoints = state.GetAnimationPath(display);
    }

    private void TryMinusFood(){
        try {
            state.MinusFood(state.getFood());
        } catch (ZeroCountFoodException e) {
            Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void IncreaseFullness(BaseState state) {
        try {
            state.setFullness(BaseState.Operation.Plus);
        } catch (TiredException e) {
            Toast.makeText(context, "Я наелся !", Toast.LENGTH_LONG).show();
        }
    }

    public Bitmap getBackgroundPaint() {
        return backgroundPaint;
    }

    public Bitmap getBmHeart() {
        return bmHeart;
    }

    public List<PointF> getBmSpritePoints() {
        return bmSpritePoints;
    }

    public int getiMaxAnimationStep() {
        return iMaxAnimationStep;
    }

    public int getPaintKindX() {
        return paintKindX;
    }

    public int getPaintKindY() {
        return paintKindY;
    }

    public Rect getBackground() {
        return background;
    }

    public Bitmap getBmSprite() {
        return bmSprite;
    }

    public Bitmap getPaintKind() {
        return paintKind;
    }

    public static void setState(BaseState st) {
        state = st;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
