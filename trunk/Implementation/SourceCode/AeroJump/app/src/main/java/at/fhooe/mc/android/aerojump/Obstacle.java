package at.fhooe.mc.android.aerojump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Bliznac on 17.06.2017.
 */

public class Obstacle {

    private static float mSpeed;
    private RectF mRect1, mRect2;
    private Bitmap skyscraper, balloon;
    private float mScreenWidth, mScreenHeight;

    public Obstacle(float _widthScreen, float _heightScreen, boolean _isFirst, Resources _res){
        mScreenWidth = _widthScreen;
        mScreenHeight = _heightScreen;
        skyscraper = BitmapFactory.decodeResource(_res, R.drawable.skyscraper);
        balloon = BitmapFactory.decodeResource(_res, R.drawable.balloon);

        float posX1, posX2, posY1, posY2;
        if (_isFirst) posX1 = _widthScreen/2 - _widthScreen/14;
        else posX1 = _widthScreen;
        posX1 = posX1 + _widthScreen/2;
        posX2 = posX1 + _widthScreen/7;

        int rand = (int)(Math.random()*_heightScreen*0.7);
        posY2 = rand + (float)(_heightScreen*0.3);
        posY1 = posY2 + (float)(_heightScreen*0.7);
        mRect1 = new RectF(posX1, posY2, posX2, posY1);

        posY1 = rand;
        posY2 = posY1 - _heightScreen*0.4f;
        mRect2 = new RectF(posX1, posY2, posX2, posY1);
        mSpeed = 15.0f;
    }

    public static float getSpeed(){
        return mSpeed;
    }

    public RectF[] getRectangles() {
        RectF[] rectangles = new RectF[2];
        rectangles[0] = mRect1;
        rectangles[1] = mRect2;
        return rectangles;
    }

    public void drawObstacle(Canvas _c, Paint _p){
        //_c.drawRect(mRect1, _p);
        _c.drawBitmap(skyscraper, null, mRect1, _p);
        //_c.drawRect(mRect2, _p);
        _c.drawBitmap(balloon, null, mRect2, _p);
    }

    public void generateObstacles(){
        int rand = (int)(Math.random()*mScreenHeight*0.7);

        if (mRect1.right > 0){
            mRect1.left = mRect1.left - mSpeed;
            mRect1.right = mRect1.right - mSpeed;
        } else {
            mRect1.left = mScreenWidth;
            mRect1.right = mScreenWidth + mScreenWidth/7;
            mRect1.top = rand + (float)(mScreenHeight*0.3);
            mRect1.bottom = mRect1.top + (float)(mScreenHeight*0.7);
        }

        if (mRect2.right > 0){
            mRect2.left = mRect2.left - mSpeed;
            mRect2.right = mRect2.right - mSpeed;
        } else {
            mRect2.left = mScreenWidth;
            mRect2.right = mScreenWidth + mScreenWidth/7;
            mRect2.bottom = rand;
            mRect2.top = mRect2.bottom - mScreenHeight*0.4f;
        }

        if (mSpeed < 30.0f) mSpeed = mSpeed + 0.004f;
    }
}
