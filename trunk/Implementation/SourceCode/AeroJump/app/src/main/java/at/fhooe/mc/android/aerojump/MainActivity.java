package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import at.fhooe.mc.android.aerojump.db.EnterPlayerNameActivity;
import at.fhooe.mc.android.aerojump.db.HighscoreActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    public static String TAG = "AeroJump: ";
    public static String PLAYERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation slideInPlay = AnimationUtils.loadAnimation(this, R.anim.slide_in_play_button);
        Animation slideInScore = AnimationUtils.loadAnimation(this, R.anim.slide_in_score_button);
        Animation slideInInfo = AnimationUtils.loadAnimation(this, R.anim.slide_in_info_button);

        Button b = null;
        b = (Button)findViewById(R.id.activity_main_playButton);
        b.setOnClickListener(this);
        b.startAnimation(slideInPlay);
        b = (Button)findViewById(R.id.activity_main_scoreButton);
        b.setOnClickListener(this);
        b.startAnimation(slideInScore);
        b = (Button)findViewById(R.id.activity_main_infoButton);
        b.setOnClickListener(this);
        b.startAnimation(slideInInfo);
        b = (Button)findViewById(R.id.activity_main_settingsButton);
        b.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences(EnterPlayerNameActivity.MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        GameActivity.playMusic = sp.getBoolean("playMusic", false);
        GameView.control = sp.getBoolean("radio_control", false);

        PLAYERNAME = sp.getString("playerName", null);
        if (PLAYERNAME == null){
            Intent i = new Intent(this, EnterPlayerNameActivity.class);
            i.putExtra("firstStart", true);
            EnterPlayerNameActivity.showOnlyEditText = true;
            startActivity(i);
        }
    }

    @Override
    public void onClick(View _view) {
        switch(_view.getId()){
            case R.id.activity_main_playButton : {
                Intent i = new Intent(this, GameActivity.class);
                startActivity(i);
                Log.i(TAG, "button play clicked");
            } break;
            case R.id.activity_main_scoreButton : {
                Intent i = new Intent(this, HighscoreActivity.class);
                SharedPreferences sp = getSharedPreferences(HighscoreActivity.MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
                HighscoreActivity.setLastHighscore(sp.getInt("lastHighscore", 0));
                HighscoreActivity.setLastPlayer(sp.getString("lastPlayer", ""));
                startActivity(i);
                Log.i(TAG, "button highscores clicked");
            } break;
            case R.id.activity_main_infoButton : {
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                Log.i(TAG, "button instructions clicked");
            } break;
            case R.id.activity_main_settingsButton : {
                Intent i = new Intent(this, EnterPlayerNameActivity.class);
                EnterPlayerNameActivity.showOnlyEditText = false;
                startActivity(i);
                Log.i(TAG, "button settings clicked");
            } break;
            default : Log.e(TAG, "unexpected id encountered"); break;
        }
    }
}
