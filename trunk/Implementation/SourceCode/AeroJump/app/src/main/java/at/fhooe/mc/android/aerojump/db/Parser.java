package at.fhooe.mc.android.aerojump.db;

import java.util.ArrayList;

/**
 * Created by Bliznac on 03.07.2017.
 */

public class Parser {

    private String phpReturn;

    public Parser(String _s){
        phpReturn = _s;
    }

    public String getScores(){
        int[] str = getScoresArray();
        StringBuffer scores = new StringBuffer();
        int length;
        if (str.length >= 8) length = 8;
        else length = str.length;
        for (int i=0; i<length; i++){
            scores.append(str[i] + "\n");
        }
        return scores.toString();
    }

    private int[] getScoresArray(){
        if (phpReturn == null) return new int[]{};

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

    public String getNames(){
        String[] str = getNamesArray();
        StringBuffer names = new StringBuffer();
        int length;
        if (str.length >= 8) length = 8;
        else length = str.length;
        for (int i=0; i<length; i++){
            names.append(str[i] + "\n");
        }
        return names.toString();
    }

    private String[] getNamesArray(){
        if (phpReturn == null) return new String[]{"Error: No Database Connection"};

        ArrayList<String> names = new ArrayList<String>();
        int start = 0;
        int stop = phpReturn.indexOf(":", start);

        while (stop != -1){
            names.add(phpReturn.substring(start, stop));
            start = phpReturn.indexOf(";", stop) + 1;
            stop = phpReturn.indexOf(":", start);
        }

        String[] s = new String[names.size()];
        for (int i=0; i < s.length; i++) s[i] = i+1 + ". " + names.get(i);
        return s;
    }

}
