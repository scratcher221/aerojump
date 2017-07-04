package at.fhooe.mc.android.aerojump;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import at.fhooe.mc.android.aerojump.db.HighscoreActivity;

public class GameActivity extends Activity implements View.OnClickListener{

    public final static String TAG = "GameActivity";
    public static boolean playMusic;
    protected static TextView mHighscore;
    protected static boolean mPause;
    private Button mButtonPause;
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gv = new GameView(this);
        setContentView(gv);

        LayoutInflater infl = this.getLayoutInflater();
        ViewGroup overlay = (ViewGroup)infl.inflate(R.layout.activity_game_overlay, null);
        addContentView(overlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        GameThread gt = new GameThread(gv);

        mHighscore = (TextView)findViewById(R.id.activity_game_text_highscore);
        mButtonPause = (Button)findViewById(R.id.activity_game_button_pause);
        mButtonPause.setOnClickListener(this);

        gt.start();

        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusic.setLooping(true);

        synchronized (GameThread.mPauseLock) {
            mPause = false;
            GameThread.mPauseLock.notifyAll();
            mButtonPause.setBackgroundResource(R.drawable.button_pause);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPause = true;
        mButtonPause.setBackgroundResource(R.drawable.button_play);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playMusic) backgroundMusic.start();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()) {
            case R.id.activity_game_button_pause: {
                if (!mPause) {
                    mPause = true;
                    _v.setBackgroundResource(R.drawable.button_play);
                } else {
                    synchronized (GameThread.mPauseLock) {
                        mPause = false;
                        GameThread.mPauseLock.notifyAll();
                        _v.setBackgroundResource(R.drawable.button_pause);
                    }
                    Log.i(TAG, "Play");
                }
                Log.i(TAG, "Pause");
            }break;
            default: Log.e(TAG, "unexpected button id encountered");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (playMusic) backgroundMusic.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playMusic) backgroundMusic.stop();
    }
}
