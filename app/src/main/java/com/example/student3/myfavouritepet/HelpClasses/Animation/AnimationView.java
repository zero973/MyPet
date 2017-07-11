package com.example.student3.myfavouritepet.HelpClasses.Animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.TiredException;
import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;
import com.example.student3.myfavouritepet.HelpClasses.States.BaseState;

import java.util.List;

public class AnimationView extends View {

    private int iCurStep = 0;
    private Paint paint;
    private Path ptCurve = new Path();
    private PathMeasure pm;
    private float fSegmentLen;

    private BaseState state;
    private PrepareForAnimation PFA;
    private Context context;

    public AnimationView(Context context, BaseState state, PrepareForAnimation PFA) {
        super(context);
        this.context = context;
        this.PFA = PFA;
        this.state = state;
        state.ShowGratitude(context);
        CreatePathAnimation(PFA.getBmSpritePoints(), PFA.getiMaxAnimationStep());

        //fullness++;

    }

    private void CreatePathAnimation(List<PointF> aPoints, int iMaxAnimationStep) {
        PointF point = aPoints.get(0);
        ptCurve.moveTo(point.x, point.y);
        for (int i = 0; i < aPoints.size() - 1; i++) {
            point = aPoints.get(i);
            PointF next = aPoints.get(i + 1);
            ptCurve.quadTo(point.x, point.y, (next.x + point.x) / 2, (point.y + next.y) / 2);
        }
        pm = new PathMeasure(ptCurve, false);
        fSegmentLen = pm.getLength() / iMaxAnimationStep;//20 animation steps
        //DrawAnimationPath();
    }

    private void DrawAnimationPath(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.rgb(0, 148, 255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(PFA.getBackgroundPaint(), null, PFA.getBackground(), null);
        canvas.drawBitmap(PFA.getPaintKind(), PFA.getPaintKindX(), PFA.getPaintKindY(), null);
        //canvas.drawPath(ptCurve, paint); //рисет путь анимации
        if (!IsAnimationbmSpriteEnd)
            DrawAnimation(canvas, PFA.getBmSprite());
        if (IsAnimationbmSpriteEnd)
            DrawAnimation(canvas, PFA.getBmHeart());
    }

    private boolean IsAnimationbmSpriteEnd = false;

    private void DrawAnimation(Canvas canvas, Bitmap sprite) {
        Matrix mxTransform = new Matrix();
        if (iCurStep <= PFA.getiMaxAnimationStep()) {
            pm.getMatrix(fSegmentLen * iCurStep, mxTransform, PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG);
            mxTransform.preTranslate(-sprite.getWidth(), -sprite.getHeight());
            canvas.drawBitmap(sprite, mxTransform, null);
            iCurStep++; //advance to the next step
            invalidate();
        } else {
            iCurStep = 0;
            if (sprite.equals(PFA.getBmSprite()))
                IsAnimationbmSpriteEnd = true;
            else
                IsAnimationbmSpriteEnd = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//run animation
            if(TryMinusFood() == true){
                try {
                    state.setFullness(BaseState.Operation.Plus);
                }
                catch (TiredException e) {
                    Toast.makeText(context, state.getTiredMsg(), Toast.LENGTH_LONG).show();
                    return false;
                }
                state.ShowGratitude(context);
                invalidate();
                return true;
            }
        }
        return false;
    }

    private boolean TryMinusFood(){
        try {
            state.MinusFood(state.getFood());
        } catch (ZeroCountFoodException e) {
            Toast.makeText(context, "Купи еду!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
