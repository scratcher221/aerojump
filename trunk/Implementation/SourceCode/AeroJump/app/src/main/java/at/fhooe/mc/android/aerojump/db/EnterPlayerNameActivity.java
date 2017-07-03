package at.fhooe.mc.android.aerojump.db;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;

import at.fhooe.mc.android.aerojump.MainActivity;
import at.fhooe.mc.android.aerojump.R;

public class EnterPlayerNameActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "EnterHighscore: ";
    private static final String FILENAME = "PLAYERNAME";
    String mName;
    int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playername);
        Button b = null;
        b = (Button)findViewById(R.id.activity_enter_playername_button_submit);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId()) {
            case R.id.activity_enter_playername_button_submit: {
                mName = ((EditText)findViewById(R.id.activity_enter_playername_textbox)).getText().toString();
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
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } break;
            default:
                Log.e(TAG, "unexpected button id encountered");
        }

    }
}
