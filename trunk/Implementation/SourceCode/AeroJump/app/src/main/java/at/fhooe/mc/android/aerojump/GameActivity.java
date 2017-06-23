package at.fhooe.mc.android.aerojump;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener{

    public final static String TAG = "GameActivity";
    protected static TextView mHighscore;
    protected static boolean mPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater infl = this.getLayoutInflater();
        ViewGroup overlay = (ViewGroup)infl.inflate(R.layout.activity_game_overlay, null);
        GameView gv = new GameView(this);
        setContentView(gv);
        addContentView(overlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        GameThread gt = new GameThread(gv);

        mHighscore = (TextView)findViewById(R.id.activity_game_text_highscore);
        Button mButtonPause = (Button)findViewById(R.id.activity_game_button_pause);
        mButtonPause.setOnClickListener(this);
//        Button mButtonPlay = (Button)findViewById(R.id.activity_game_button_play);
//        mButtonPlay.setOnClickListener(this);
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

    @Override
    public void onClick(View _v) {
        switch(_v.getId()) {
            case R.id.activity_game_button_pause: {
                Button pause = (Button)_v;
                if (!mPause) {
                    mPause = true;
                    pause.setText(R.string.Play);
                }
                else {
                    synchronized (GameThread.mPauseLock) {
                        mPause = false;
                        GameThread.mPauseLock.notifyAll();
                        pause.setText(R.string.Pause);
                    }
                    Log.i(TAG, "Play");
                }
                Log.i(TAG, "Pause");
            }break;
            default: Log.e(TAG, "unexpected button id encountered");
        }
    }
}
