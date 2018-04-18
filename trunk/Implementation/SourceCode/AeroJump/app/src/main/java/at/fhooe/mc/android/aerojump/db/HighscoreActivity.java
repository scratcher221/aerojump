package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import at.fhooe.mc.android.aerojump.R;

public class HighscoreActivity extends Activity {

    public static final String TAG = "HighscoreActivity";
    public static final String MY_SHARED_PREF_KEY = "SharedPref2";

    private List<PlayerModel> mHighscoreList;
    private static int lastHighscore;
    private static String lastPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        DatabaseReference highscoresReference = FirebaseDatabase.getInstance().getReference().child("users");
        mHighscoreList = new ArrayList<>();

        highscoresReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snap = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snap.iterator();
                while (iterator.hasNext()){
                    DataSnapshot entry = iterator.next();
                    String curEntryName = entry.getKey();
                    long curEntryScore;
                    if (entry.hasChild("highscore")){
                        curEntryScore = (long)entry.child("highscore").getValue();
                    } else {
                        curEntryScore = 0;
                    }
                    mHighscoreList.add(new PlayerModel(curEntryName, (int)curEntryScore));
                    Log.i(TAG, curEntryName + ": " + String.valueOf(curEntryScore));
                }
                PlayerModel[] array = (PlayerModel[])mHighscoreList.toArray();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value!");
            }
        });

        SharedPreferences sp = getSharedPreferences(MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = sp.edit();
        edt.putInt("lastHighscore", lastHighscore);
        edt.putString("lastPlayer", lastPlayer);
        edt.apply();

        TextView lastScore = (TextView)findViewById(R.id.activity_highscores_lastplayed);
        lastScore.setText(sp.getString("lastPlayer", "") + "\n" + String.valueOf(lastHighscore));
    }

    public static void setLastHighscore(int _h){
        lastHighscore = _h;
    }

    public static void setLastPlayer(String _name){
        lastPlayer = _name;
    }
}
