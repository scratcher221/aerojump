package at.fhooe.mc.android.aerojump;

import android.util.Log;

/**
 * Created by david on 21.06.17.
 */

public class GameThread extends Thread {

    private GameView gameView;

    public GameThread(GameView gv) {
        gameView = gv;
    }

    @Override
    public void run() {
        while(!gameView.mGameOver) {
            gameView.postInvalidate();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Log.e(GameActivity.TAG, "interrupted while sleeping", e);
            }
        }
    }
}
