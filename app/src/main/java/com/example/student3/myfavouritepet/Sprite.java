package com.example.student3.myfavouritepet;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

    private Bitmap bitmap;
    private int frameWidth, frameHeight, padding;
    private double frameTime, x, y, velocityX, velocityY;

    public Sprite(double x, double y, double velocityX, double velocityY, Rect initialFrame, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.bitmap = bitmap;
        this.frameTime = 0.1;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
    }

    public void update (int ms) {
        timeForCurrentFrame += ms;
        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }
        x = x + velocityX * ms/1000.0;
        y = y + velocityY * ms/1000.0;
    }
}
