package at.fhooe.mc.android.aerojump;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by peter on 21.06.2017.
 */

public class Player {

    private RectF mRectPlayer;
    private float mScreenWidth, mScreenHeight;

    public Player(float width, float height) {
        mScreenHeight = height;
        mScreenWidth = width;

        float posX1, posX2, posY1, posY2;
        posX1 = width / 16;
        posX2 = posX1 + width / 12;
        posY1 = height / 32 + height / 2;
        posY2 = height / 2 - height / 32;

        mRectPlayer = new RectF(posX1, posY2, posX2, posY1);
    }

    public void drawPlayer(Canvas c, Paint p) {
        c.drawRect(mRectPlayer, p);
    }

    public void movePlayer(boolean moved) {
        if (moved) {
            mRectPlayer.top=mRectPlayer.top-8.0f;
            mRectPlayer.bottom=mRectPlayer.bottom-8.0f;
        }
        else if (!moved){
            mRectPlayer.top=mRectPlayer.top+8.0f;
            mRectPlayer.bottom=mRectPlayer.bottom+8.0f;
        }
    }

}

