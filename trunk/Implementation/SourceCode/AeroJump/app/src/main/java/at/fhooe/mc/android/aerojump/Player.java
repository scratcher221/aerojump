package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import at.fhooe.mc.android.aerojump.db.HighscoreActivity;
import at.fhooe.mc.android.aerojump.db.InsertHighscoresThread;

/**
 * Created by peter on 21.06.2017.
 */

public class Player {

    private RectF mRectPlayer;
    private Bitmap plane;
    private float mScreenHeight;
    private float moveOnGameOver;
    private int mHighscore;
    private float mSpeed;
    private boolean mgameOver;

    public Player(float _width, float _height, Resources _res) {
        mScreenHeight = _height;
        plane = BitmapFactory.decodeResource(_res, R.drawable.plane);

        float posX1, posX2, posY1, posY2;
        posX1 = _width / 16;
        posX2 = posX1 + _width / 12;
        posY1 = _height / 2 + _height / 32;
        posY2 = _height / 2 - _height / 32;

        mRectPlayer = new RectF(posX1, posY2, posX2, posY1);
        moveOnGameOver = 1.0f;
        mgameOver = false;
    }

    public void drawPlayer(Canvas _c, Paint _p) {
        //_c.drawRect(mRectPlayer, _p);
        _c.drawBitmap(plane, null, mRectPlayer, _p);
    }

    public boolean movePlayer (boolean _isTouched, boolean _leftTouch) {
        if (mRectPlayer.bottom < 0 || mRectPlayer.top > mScreenHeight) return true;
        if (_isTouched) {
            if (_leftTouch){
                mRectPlayer.top = mRectPlayer.top - 24.0f;
                mRectPlayer.bottom = mRectPlayer.bottom - 24.0f;
            } else {
                mRectPlayer.top = mRectPlayer.top + 24.0f;
                mRectPlayer.bottom = mRectPlayer.bottom + 24.0f;
            }
        } else {
            mRectPlayer.top = mRectPlayer.top + 1.0f;
            mRectPlayer.bottom = mRectPlayer.bottom + 1.0f;
        }
        return false;
    }

    public void moveOnGameOver(Context _c){
        if (mRectPlayer.top > mScreenHeight && !mgameOver) {
            final InsertHighscoresThread insertHighscoresThread = new InsertHighscoresThread(MainActivity.PLAYERNAME, String.valueOf(this.getHighscore()));
            insertHighscoresThread.insertIntoDatabase();
            Intent i = new Intent(_c, HighscoreActivity.class);
            ((Activity)_c).finish();
            HighscoreActivity.setInsertHighscoreThread(insertHighscoresThread);
            HighscoreActivity.setLastHighscore(mHighscore);
            HighscoreActivity.setLastPlayer(MainActivity.PLAYERNAME);
            _c.startActivity(i);
            mgameOver = true;
        }
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
        if (Math.abs(mRectPlayer.left - obstacle1[1].right) < mSpeed/2
                || Math.abs(mRectPlayer.left - obstacle2[1].right) < mSpeed/2) mHighscore++;
        return false;
    }

    public int getHighscore(){
        return mHighscore;
    }

    public void setSpeed(float _s) {
        mSpeed = _s;
    }

}

