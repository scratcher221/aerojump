package at.fhooe.mc.android.aerojump;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Bliznac on 17.06.2017.
 */

public class GameView extends View implements View.OnTouchListener{

    private Paint mPaint;
    private Obstacle mObstacle1, mObstacle2;
    private float mScreenWidth, mScreenHeight;
    private boolean onTouchHold;
    private Player player;

    public GameView(Context _context){
        super(_context);

        mPaint = new Paint();
        mScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        mScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        mObstacle1 = new Obstacle(mScreenWidth, mScreenHeight, true);
        mObstacle2 = new Obstacle(mScreenWidth, mScreenHeight, false);
        player=new Player(mScreenWidth,mScreenHeight);
        onTouchHold = false;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.GREEN);
        mObstacle1.drawObstacle(canvas, mPaint);
        mObstacle1.generateObstacles();
        mObstacle2.drawObstacle(canvas, mPaint);
        mObstacle2.generateObstacles();
        mPaint.setColor(Color.BLACK);
        player.drawPlayer(canvas,mPaint);
        player.movePlayer(onTouchHold);
        invalidate();   //repaints the view
    }

    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        if (_event.getAction() == MotionEvent.ACTION_DOWN) onTouchHold = true;
        else if (_event.getAction() == MotionEvent.ACTION_UP) onTouchHold = false;
        return true;
    }
}
