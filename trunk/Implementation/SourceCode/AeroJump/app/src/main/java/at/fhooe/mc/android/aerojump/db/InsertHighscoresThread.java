package at.fhooe.mc.android.aerojump.db;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by david on 01.07.17.
 */

public class InsertHighscoresThread extends Thread{
    private static final String TAG = "GetHighscoresThread";
    private String name, score;

    public InsertHighscoresThread(String _name, String _score) {
        name = _name;
        score = _score;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("http://dav-raspberrypi.ddns.net/http/insertscore.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+"&"+URLEncoder.encode("score", "UTF-8")+"="+URLEncoder.encode(score, "UTF-8");
            Log.i(TAG, "POST Data --> "+post_data);
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            Log.i(TAG, "HTTP response --> "+result);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
