package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        GetHighscoresThread dT = new GetHighscoresThread(this);
        dT.start();



        TextView tv = (TextView)findViewById(R.id.activity_highscores_content);
        tv.setText(GetHighscoresThread.HIGHSCORES);
    }
}
