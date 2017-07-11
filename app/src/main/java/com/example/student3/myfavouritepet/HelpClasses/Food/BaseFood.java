package com.example.student3.myfavouritepet.HelpClasses.Food;

import com.example.student3.myfavouritepet.HelpClasses.Exceptions.ZeroCountFoodException;

public abstract class BaseFood {

    protected int foodBitmapId;
    protected String foodName;

    protected int cost = 0;

    private byte currentCount = 0;

    public int getFoodBitmapId() {
        return foodBitmapId;
    }

    public String getFoodName() {
        return foodName;
    }

    public byte getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(byte currentCount) throws ZeroCountFoodException {
        if (currentCount < 0)
            throw new ZeroCountFoodException();
        else
            this.currentCount = currentCount;
    }

    public int getCost() {
        return cost;
    }
}
