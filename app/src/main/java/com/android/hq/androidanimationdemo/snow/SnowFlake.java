package com.android.hq.androidanimationdemo.snow;

/**
 * Created by heqiang on 16-12-1.
 */
public class SnowFlake {
    public int startX;
    public int startY;
    public int x;
    public int y;
    public long startTimeVertical;
    public long startTimeHorizontal;
    public boolean isLive;
    public int alpha;
    public int speedVertical;
    public float scale;
    public int index;

    public SnowFlake(){
        this.startX = 0;
        this.startY = 0;
        this.x = 0;
        this.y = 0;
        this.startTimeVertical = 0;
        this.alpha = 255;
        this.speedVertical = 0;
        this.isLive = false;
        this.scale = 1;
    }
}
