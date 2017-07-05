package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class HighscoreActivity extends Activity {

    public static final String MY_SHARED_PREF_KEY = "SharedPref2";

    private static InsertHighscoresThread mInsertHighscoreThread;
    private boolean isLoading = false;
    private static int lastHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        final GetHighscoresThread dT = new GetHighscoresThread();
        if (mInsertHighscoreThread != null){
            isLoading = true;
            final Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (!mInsertHighscoreThread.isAlive()){
                        t.cancel();
                        dT.start();
                        isLoading = false;
                    }
                }
            }, 0, 100);
        } else dT.start();

        final TextView tvNames = (TextView) findViewById(R.id.activity_highscores_names);
        final TextView tvScores = (TextView) findViewById(R.id.activity_highscores_scores);

        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!dT.isAlive() && !isLoading) {
                            t.cancel();
                            Parser p = new Parser(GetHighscoresThread.HIGHSCORES);
                            tvNames.setText(p.getNames());
                            tvScores.setText(p.getScores());
                        }
                        else {
                            tvNames.setText("Loading Scores...");
                            tvScores.setText("");
                        }
                    }
                });
            }
        }, 0, 100);

        SharedPreferences sp = getSharedPreferences(MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sp.edit();
        edt.putInt("lastHighscore", lastHighscore);
        edt.commit();

        TextView lastScore = (TextView)findViewById(R.id.activity_highscores_lastplayed);
        lastScore.setText(MainActivity.PLAYERNAME + "\n" + String.valueOf(lastHighscore));
    }

    public static void setInsertHighscoreThread(InsertHighscoresThread _it){
        mInsertHighscoreThread = _it;
    }

    public static void setLastHighscore(int _h){
        lastHighscore = _h;
    }
}
