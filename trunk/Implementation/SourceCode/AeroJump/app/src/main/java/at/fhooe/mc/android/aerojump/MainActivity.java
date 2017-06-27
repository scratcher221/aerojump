package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    public static String TAG = "AeroJump: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = null;
        b = (Button)findViewById(R.id.activity_main_playButton);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.activity_main_scoreButton);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.activity_main_instructionsButton);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.activity_main_creditsButton);
        b.setOnClickListener(this);
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

                Log.i(TAG, "button highscores clicked");
            } break;
            case R.id.activity_main_instructionsButton : {

                Log.i(TAG, "button instructions clicked");
            } break;
            case R.id.activity_main_creditsButton : {

                Log.i(TAG, "button credits clicked");
            } break;
            default : Log.e(TAG, "unexpected id encountered"); break;
        }
    }
}
