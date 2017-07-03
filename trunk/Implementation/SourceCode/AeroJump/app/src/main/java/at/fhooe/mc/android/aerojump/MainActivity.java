package at.fhooe.mc.android.aerojump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import at.fhooe.mc.android.aerojump.db.EnterPlayerNameActivity;
import at.fhooe.mc.android.aerojump.db.HighscoreActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    public static String TAG = "AeroJump: ";
    public static String PLAYERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = null;
        b = (Button)findViewById(R.id.activity_main_playButton);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.activity_main_scoreButton);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.activity_main_infoButton);
        b.setOnClickListener(this);

        String ret = "";
        try {
            InputStream inputStream = this.openFileInput("PLAYERNAME");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File PLAYERNAME not found ... will be created");
            Intent i = new Intent(this, EnterPlayerNameActivity.class);
            startActivity(i);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        PLAYERNAME = ret;
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
                startActivity(i);
                Log.i(TAG, "button highscores clicked");
            } break;
            case R.id.activity_main_infoButton : {
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                Log.i(TAG, "button instructions clicked");
            } break;
            default : Log.e(TAG, "unexpected id encountered"); break;
        }
    }
}
