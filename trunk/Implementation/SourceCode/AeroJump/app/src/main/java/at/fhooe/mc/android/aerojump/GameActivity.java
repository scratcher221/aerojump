package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity {

    public final static String TAG = "AeroJump Game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gv = new GameView(this);
        setContentView(gv);
        GameThread gt = new GameThread(gv);
        gt.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
