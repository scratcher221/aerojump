package at.fhooe.mc.android.aerojump;

import android.util.Log;

/**
 * Created by david on 21.06.17.
 */

public class GameThread extends Thread {

    private GameView gv;

    public GameThread(GameView gv) {
        this.gv = gv;
    }

    @Override
    public void run() {
        while(!gv.mGameOver) {
            gv.postInvalidate();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Log.e(GameActivity.TAG, "interrupted while sleeping", e);
            }
        }
    }
}
