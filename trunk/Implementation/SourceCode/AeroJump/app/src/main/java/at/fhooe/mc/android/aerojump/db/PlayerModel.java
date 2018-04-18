package at.fhooe.mc.android.aerojump.db;

/**
 * Created by Marko on 18.04.2018.
 */

public class PlayerModel {

    private String mPlayerName;
    private int mHighscore;

    public PlayerModel(String _name, int _score){
        mPlayerName = _name;
        mHighscore = _score;
    }

    public String getPlayerName(){
        return mPlayerName;
    }

    public int getHighscore(){
        return mHighscore;
    }

}
