package at.fhooe.mc.android.aerojump.db;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by david on 01.07.17.
 */

public class InsertHighscoresThread {

    private static final String TAG = "GetHighscoresThread";
    private String mName, mScore;
    private List scoresList;

    private DatabaseReference mScoresReference;

    public InsertHighscoresThread(String _name, String _score) {
        mName = _name;
        mScore = _score;

        mScoresReference = FirebaseDatabase.getInstance().getReference().child("users").child(mName);
    }

    public void insertIntoDatabase(){
        mScoresReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*@Override
    public void run() {
        try {
            URL url = new URL("http://dav-raspberrypi.ddns.net/http/insertscore.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("mName", "UTF-8")+"="+URLEncoder.encode(mName, "UTF-8")+"&"+URLEncoder.encode("mScore", "UTF-8")+"="+URLEncoder.encode(mScore, "UTF-8");
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
    }*/
}
