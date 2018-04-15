package at.fhooe.mc.android.aerojump.db;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.FileOutputStream;

import at.fhooe.mc.android.aerojump.GameActivity;
import at.fhooe.mc.android.aerojump.GameView;
import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class EnterPlayerNameActivity extends Activity implements View.OnClickListener {

    public static final String MY_SHARED_PREF_KEY = "SharedPref";

    private static final String TAG = "EnterHighscore: ";
    private static final String FILENAME = "PLAYERNAME";
    public static boolean showOnlyEditText;
    private RadioButton rb1, rb2;
    private CheckBox cb;
    String mName;
    int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playername);

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
                mName = ((EditText)findViewById(R.id.activity_enter_playername_textbox)).getText().toString();
                if (!mName.equals("")){
                    MainActivity.PLAYERNAME = mName;
                    Intent mIntent = getIntent();
                    mScore = mIntent.getIntExtra("highscore", 0);
                    FileOutputStream outputStream;
                    try {
                        outputStream = openFileOutput(FILENAME, MODE_PRIVATE);
                        Log.i(TAG, "mName --> "+mName);
                        Log.i(TAG, "Filedir -->"+getFilesDir());
                        outputStream.write(mName.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (cb.isChecked()) GameActivity.playMusic = true;
                    else GameActivity.playMusic = false;

                    if (rb1.isChecked()) GameView.control = false;
                    if (rb2.isChecked()) GameView.control = true;

                    SharedPreferences sp = getSharedPreferences(MY_SHARED_PREF_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = sp.edit();
                    edt.putString("playerName", mName);
                    edt.putBoolean("playMusic", GameActivity.playMusic);
                    edt.putBoolean("radio_control", GameView.control);
                    edt.apply();

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Log.i(TAG, "This app needs ACESS FINE LOCATION permission");
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    }
                    //String uniqueID = UUID.randomUUID().toString();
                } else Toast.makeText(this, "Enter your name first!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == 0){
            finish();
        }
    }
}
