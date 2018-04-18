package at.fhooe.mc.android.aerojump.db;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by david on 01.07.17.
 */

public class InsertHighscoresThread {

    private static final String TAG = "GetHighscoresThread";
    private String mName;
    int mScore;

    private static DatabaseReference mUserNodeScoresReference;

    public InsertHighscoresThread(String _name, int _score) {
        mName = _name;
        mScore = _score;

        mUserNodeScoresReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void insertIntoDatabase(){
        mUserNodeScoresReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long curScore = (long)dataSnapshot.child(mName).child("highscore").getValue();
                if (!dataSnapshot.child(mName).hasChild("highscore") || mScore > curScore) {
                    mUserNodeScoresReference.child(mName).child("highscore").setValue(mScore);
                }
                mUserNodeScoresReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value!");
            }
        });
    }
}
