package at.fhooe.mc.android.aerojump.db;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import at.fhooe.mc.android.aerojump.R;
import at.fhooe.mc.android.aerojump.db.HighscoreActivity;

/**
 * Created by david on 01.07.17.
 */

public class GetHighscoresThread extends Thread{
    private static final String TAG = "GetHighscoresThread";
    public static String HIGHSCORES;

    @Override
    public void run() {
        try {
            URL url = new URL("http://dav-raspberrypi.ddns.net/http/highscores.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line+";";
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            HIGHSCORES = result;
            Log.i(TAG, "Data -> "+result);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
