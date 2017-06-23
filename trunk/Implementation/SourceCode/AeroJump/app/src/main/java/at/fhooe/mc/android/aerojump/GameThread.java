package at.fhooe.mc.android.aerojump;

import android.util.Log;

/**
 * Created by david on 21.06.17.
 */

public class GameThread extends Thread {

    private static final String TAG = "GameThread";
    private GameView gameView;
    private GameActivity gameActivity;
    protected static Object mPauseLock;

    public GameThread(GameView gv) {
        gameView = gv;
        mPauseLock = new Object();
    }

    @Override
    public void run() {
        while(true) {
            gameView.postInvalidate();
            synchronized (mPauseLock) {
                while (gameActivity.mPause) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException _e) {
                        Log.e(TAG, "Interrupted Exception: ", _e);
                    }
                }
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException _e) {
                Log.e(GameActivity.TAG, "Interrupted while sleeping", _e);
            }
        }
    }
}
