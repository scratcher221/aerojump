package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener{

    public final static String TAG = "GameActivity";
    public static boolean playMusic;
    protected static TextView mHighscore;
    protected static boolean mPause;
    private Button mButtonPause, mButtonPlay;
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
        mButtonPlay = (Button)findViewById(R.id.activity_game_button_play);
        mButtonPlay.setOnClickListener(this);

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
                mPause = true;
                mButtonPause.setVisibility(View.GONE);
                mButtonPlay.setVisibility(View.VISIBLE);
                Log.i(TAG, "Pause");
            } break;
            case R.id.activity_game_button_play: {
                synchronized (GameThread.mPauseLock) {
                    mPause = false;
                    GameThread.mPauseLock.notifyAll();
                    mButtonPause.setVisibility(View.VISIBLE);
                    mButtonPlay.setVisibility(View.GONE);
                }
                Log.i(TAG, "Play");
            } break;
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
