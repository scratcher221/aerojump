package at.fhooe.mc.android.aerojump;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Bliznac on 17.06.2017.
 */

public class Obstacle {

    private RectF mRect1, mRect2;
    private float mScreenWidth, mScreenHeight;

    public Obstacle(float _widthScreen, float _heightScreen, boolean _isFirst){
        mScreenWidth = _widthScreen;
        mScreenHeight = _heightScreen;

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
    }

    public RectF[] getRectangles() {
        RectF[] rectangles = new RectF[2];
        rectangles[0] = mRect1;
        rectangles[1] = mRect2;
        return rectangles;
    }

    public void drawObstacle(Canvas _c, Paint _p){
        _c.drawRect(mRect1, _p);
        _c.drawRect(mRect2, _p);
    }

    public void generateObstacles(){
        int rand = (int)(Math.random()*mScreenHeight*0.7);

        if (mRect1.right > 0){
            mRect1.left = mRect1.left - 10.0f;
            mRect1.right = mRect1.right - 10.0f;
        } else {
            mRect1.left = mScreenWidth;
            mRect1.right = mScreenWidth + mScreenWidth/7;
            mRect1.top = rand + (float)(mScreenHeight*0.3);
            mRect1.bottom = mRect1.bottom + (float)(mScreenHeight*0.7);
        }

        if (mRect2.right > 0){
            mRect2.left = mRect2.left - 10.0f;
            mRect2.right = mRect2.right - 10.0f;
        } else {
            mRect2.left = mScreenWidth;
            mRect2.right = mScreenWidth + mScreenWidth/7;
            mRect2.bottom = rand;
            mRect2.top = mRect2.bottom - mScreenHeight*0.4f;
        }
    }
}
