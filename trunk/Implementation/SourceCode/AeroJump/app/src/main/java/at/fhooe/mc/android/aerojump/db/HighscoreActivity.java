package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Timer;
import java.util.TimerTask;

import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        final GetHighscoresThread dT = new GetHighscoresThread(this);
        dT.start();

        final TextView tv = (TextView) findViewById(R.id.activity_highscores_content);
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!dT.isAlive()) tv.setText(GetHighscoresThread.HIGHSCORES);
                        else tv.setText("Loading Highscores...");
                    }
                });
            }
        }, 0, 100);
    }
}
