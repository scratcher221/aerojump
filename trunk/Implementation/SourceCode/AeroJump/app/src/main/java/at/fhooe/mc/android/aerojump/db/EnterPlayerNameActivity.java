package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.fhooe.mc.android.aerojump.GameActivity;
import at.fhooe.mc.android.aerojump.GameView;
import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class EnterPlayerNameActivity extends Activity implements View.OnClickListener {

    public static final String MY_SHARED_PREF_KEY = "SharedPref";
    public static boolean showOnlyEditText;
    private static final String TAG = "EnterHighscore: ";
    private RadioButton rb1, rb2;
    private CheckBox cb;

    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("firstStart", false)){
            finishAffinity();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playername);

        mAuth = FirebaseAuth.getInstance();

        Button b = null;
        b = (Button)findViewById(R.id.activity_enter_playername_button_submit);
        b.setOnClickListener(this);

        rb1 = (RadioButton)findViewById(R.id.activity_enter_playername_radio_control_a);
        rb1.setOnClickListener(this);
        rb2 = (RadioButton)findViewById(R.id.activity_enter_playername_radio_control_b);
        rb2.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences(MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
        String name = sp.getString("playerName", null);
        ((EditText)findViewById(R.id.activity_enter_playername_textbox)).setText(name);

        cb = (CheckBox)findViewById(R.id.activity_enter_playername_enable_sound);
        boolean music = sp.getBoolean("playMusic", false);
        cb.setChecked(music);

        boolean control = sp.getBoolean("radio_control", false);
        rb1.setChecked(!control);
        rb2.setChecked(control);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()) {
            case R.id.activity_enter_playername_button_submit: {
                String playerName = ((EditText)findViewById(R.id.activity_enter_playername_textbox)).getText().toString();
                if (playerName.equals("")) Toast.makeText(this, "Enter your name first!", Toast.LENGTH_SHORT).show();
                else if (playerName.contains(" ")) Toast.makeText(this, "Your name must not contain space!", Toast.LENGTH_SHORT).show();
                else {
                    if (getIntent().getBooleanExtra("firstStart", false)){
                        signIn(playerName);
                    } else {
                        updateDatabase(playerName);
                    }
                }
            } break;
            case R.id.activity_enter_playername_radio_control_a: {
                if (rb1.isChecked()) rb2.setChecked(false);
            } break;
            case R.id.activity_enter_playername_radio_control_b: {
                if (rb2.isChecked()) rb1.setChecked(false);
            } break;
            default:
                Log.e(TAG, "unexpected button id encountered");
        }
    }

    private void signIn(final String _playerName) {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "Anonymous login successful");
                            updateDatabase(_playerName);
                        } else {
                            Log.e(TAG, "Anonymous login unsuccessful");
                        }
                    }
                });
    }

    private void updateDatabase(final String _playerName){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        myRef.child("users").child(_playerName).addValueEventListener(new ValueEventListener() {
            boolean userExists = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences sp = getSharedPreferences(MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
                if (_playerName.equals(sp.getString("playerName", null))){
                    GameActivity.playMusic = cb.isChecked();

                    if (rb1.isChecked()) GameView.control = false;
                    if (rb2.isChecked()) GameView.control = true;

                    SharedPreferences.Editor edt = sp.edit();
                    edt.putBoolean("playMusic", GameActivity.playMusic);
                    edt.putBoolean("radio_control", GameView.control);
                    edt.apply();

                    userExists = true;
                    MainActivity.PLAYERNAME = _playerName;
                    myRef.child("users").child(_playerName).removeEventListener(this);
                    finish();
                }
                if (!dataSnapshot.exists()){
                    String curUser = sp.getString("playerName", null);
                    if (!getIntent().getBooleanExtra("firstStart", false)){
                        if (curUser != null) myRef.child("users").child(curUser).setValue(null);
                        myRef.child("users").child(_playerName).child("uuid").setValue(mAuth.getCurrentUser().getUid());
                    } else {
                        dataSnapshot.getRef().child("uuid").setValue(mAuth.getCurrentUser().getUid());
                    }

                    GameActivity.playMusic = cb.isChecked();

                    if (rb1.isChecked()) GameView.control = false;
                    if (rb2.isChecked()) GameView.control = true;

                    SharedPreferences.Editor edt = sp.edit();
                    edt.putString("playerName", _playerName);
                    edt.putBoolean("playMusic", GameActivity.playMusic);
                    edt.putBoolean("radio_control", GameView.control);
                    edt.apply();

                    getIntent().putExtra("firstStart", false);
                    userExists = true;
                    MainActivity.PLAYERNAME = _playerName;
                    myRef.child("users").child(_playerName).removeEventListener(this);
                    finish();
                }
                if (!userExists){
                    Toast.makeText(getApplicationContext(), "This username already exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read value!");
            }
        });
    }
}
