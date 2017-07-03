package at.fhooe.mc.android.aerojump.db;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Bliznac on 03.07.2017.
 */

public class Parser {

    private String phpReturn;

    public Parser(String _s){
        phpReturn = _s;
    }

    public int[] getScores(){
        ArrayList<Integer> scores = new ArrayList<Integer>();

        int start = phpReturn.indexOf(":") + 2;
        int stop = phpReturn.indexOf(";", start);

        while (start > 1){
            scores.add(Integer.valueOf(phpReturn.substring(start, stop)));
            start = phpReturn.indexOf(":", stop) + 2;
            stop = phpReturn.indexOf(";", start);
        }

        int[] s = new int[scores.size()];
        for (int i=0; i < s.length; i++) s[i] = scores.get(i);
        return s;
    }

    public String[] getNames(){
        ArrayList<String> names = new ArrayList<String>();

        int start = 0;
        int stop = phpReturn.indexOf(":", start);

        while (stop != -1){
            names.add(phpReturn.substring(start, stop));
            start = phpReturn.indexOf(";", stop) + 1;
            stop = phpReturn.indexOf(":", start);
        }

        String[] s = new String[names.size()];
        for (int i=0; i < s.length; i++) s[i] = names.get(i);
        return s;
    }

}
