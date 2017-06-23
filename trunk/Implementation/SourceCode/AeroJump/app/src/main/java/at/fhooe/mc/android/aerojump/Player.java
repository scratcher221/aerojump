package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by peter on 21.06.2017.
 */

public class Player {

    private RectF mRectPlayer;
    private float mScreenHeight;
    private float moveOnGameOver;
    private int highscore;

    public Player(float width, float height) {
        mScreenHeight = height;

        float posX1, posX2, posY1, posY2;
        posX1 = width / 16;
        posX2 = posX1 + width / 12;
        posY1 = height / 2 + height / 32;
        posY2 = height / 2 - height / 32;

        mRectPlayer = new RectF(posX1, posY2, posX2, posY1);
        moveOnGameOver = 1.0f;
    }

    public void drawPlayer(Canvas _c, Paint _p) {
        _c.drawRect(mRectPlayer, _p);
    }

    public boolean movePlayer (boolean isTouched, boolean leftTouch) {
        if (mRectPlayer.bottom < 0 || mRectPlayer.top > mScreenHeight) return true;
        if (isTouched) {
            if (leftTouch){
                mRectPlayer.top = mRectPlayer.top - 12.0f;
                mRectPlayer.bottom = mRectPlayer.bottom - 12.0f;
            } else {
                mRectPlayer.top = mRectPlayer.top + 12.0f;
                mRectPlayer.bottom = mRectPlayer.bottom + 12.0f;
            }
        } else {
            mRectPlayer.top = mRectPlayer.top + 2.0f;
            mRectPlayer.bottom = mRectPlayer.bottom + 2.0f;
        }
        return false;
    }

    public void moveOnGameOver(Context _c){
        if (mRectPlayer.top > mScreenHeight) ((Activity)_c).finish();
        mRectPlayer.top = mRectPlayer.top + moveOnGameOver;
        mRectPlayer.bottom = mRectPlayer.bottom + moveOnGameOver;
        mRectPlayer.left = mRectPlayer.left + 4.0f;
        mRectPlayer.right = mRectPlayer.right + 4.0f;
        moveOnGameOver = moveOnGameOver*1.1f;
    }

    public boolean detectCollision(RectF[] obstacle1, RectF[] obstacle2) {
        if (mRectPlayer.right >= obstacle1[0].left && mRectPlayer.left <= obstacle1[0].right) {
            if (mRectPlayer.top <= obstacle1[1].bottom && mRectPlayer.bottom >= obstacle1[1].top) return true;
            else if (mRectPlayer.bottom >= obstacle1[0].top) return true;
        }
        else if (mRectPlayer.right >= obstacle2[0].left && mRectPlayer.left <= obstacle2[0].right) {
            if (mRectPlayer.top <= obstacle2[1].bottom && mRectPlayer.bottom >= obstacle2[1].top) return true;
            else if (mRectPlayer.bottom >= obstacle2[0].top) return true;
        }
        if (Math.abs(mRectPlayer.left - obstacle1[1].right) < 5
                || Math.abs(mRectPlayer.left - obstacle2[1].right) < 5) highscore++;
        return false;
    }

    public int getHighscore(){
        return highscore;
    }

}

