package at.fhooe.mc.android.aerojump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Bliznac on 17.06.2017.
 */

public class GameView extends View implements View.OnTouchListener{

    public boolean mGameOver;
    private Paint mPaint;
    private Obstacle mObstacle1, mObstacle2;
    private Player mPlayer;
    private float mScreenWidth, mScreenHeight;
    private boolean onTouchHold;

    public GameView(Context _context){
        super(_context);

        mPaint = new Paint();
        mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels + getNavBarWidth(_context);
        mScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        mObstacle1 = new Obstacle(mScreenWidth, mScreenHeight, true);
        mObstacle2 = new Obstacle(mScreenWidth, mScreenHeight, false);
        mPlayer = new Player(mScreenWidth,mScreenHeight);

        onTouchHold = false;
        mGameOver = false;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.GREEN);
        mObstacle1.drawObstacle(canvas, mPaint);
        mObstacle2.drawObstacle(canvas, mPaint);
        mPaint.setColor(Color.BLACK);
        mPlayer.drawPlayer(canvas, mPaint);
        drawHighscore(mPlayer.getHighscore(), canvas);

        if(!mGameOver){
            mObstacle1.generateObstacles();
            mObstacle2.generateObstacles();
            mGameOver = mPlayer.detectCollision(mObstacle1.getRectangles(), mObstacle2.getRectangles()) || mPlayer.movePlayer(onTouchHold);
        } else mPlayer.moveOnGameOver();
    }

    public void drawHighscore(int _score, Canvas _c){
        int dig3, dig2, dig1;
        Bitmap digit3, digit2, digit1;
        RectF rect3 = new RectF(), rect2 = new RectF(), rect1 = new RectF();

        if (_score < 10) rect1 = new RectF(mScreenWidth/2-48, 40, mScreenWidth/2+48, 168);
        else if (_score < 100) {
            rect1 = new RectF(mScreenWidth/2, 40, mScreenWidth/2+96, 168);
            rect2 = new RectF(mScreenWidth/2-96, 40, mScreenWidth/2, 168);
        } else if (_score < 1000){
            rect1 = new RectF(mScreenWidth/2+48, 40, mScreenWidth/2+144, 168);
            rect2 = new RectF(mScreenWidth/2-48, 40, mScreenWidth/2+48, 168);
            rect3 = new RectF(mScreenWidth/2-144, 40, mScreenWidth/2-48, 168);
        }

        dig1 = _score % 10;
        _score = _score / 10;
        dig2 = _score % 10;
        _score = _score / 10;
        dig3 = _score % 10;

        digit1 = BitmapFactory.decodeResource(getResources(), getBitmapID(dig1));
        digit2 = BitmapFactory.decodeResource(getResources(), getBitmapID(dig2));
        digit3 = BitmapFactory.decodeResource(getResources(), getBitmapID(dig3));

        _c.drawBitmap(digit1, null, rect1, mPaint);
        if (dig2 != 0) _c.drawBitmap(digit2, null, rect2, mPaint);
        if (dig3 != 0) _c.drawBitmap(digit3, null, rect3, mPaint);
    }

    public int getBitmapID(int _digit){
        switch (_digit){
            case 0 : return R.drawable.digit0;
            case 1 : return R.drawable.digit1;
            case 2 : return R.drawable.digit2;
            case 3 : return R.drawable.digit3;
            case 4 : return R.drawable.digit4;
            case 5 : return R.drawable.digit5;
            case 6 : return R.drawable.digit6;
            case 7 : return R.drawable.digit7;
            case 8 : return R.drawable.digit8;
            case 9 : return R.drawable.digit9;
            default: return -1;
        }
    }

    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        if (_event.getAction() == MotionEvent.ACTION_DOWN) onTouchHold = true;
        else if (_event.getAction() == MotionEvent.ACTION_UP) onTouchHold = false;
        return true;
    }

    private static int getNavBarWidth(Context _context) {
        int result = 0;
        int resourceId = _context.getResources().getIdentifier("navigation_bar_width", "dimen", "android");
        if (resourceId > 0) {
            result = _context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
