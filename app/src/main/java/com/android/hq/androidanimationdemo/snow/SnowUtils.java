package com.android.hq.androidanimationdemo.snow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import com.android.hq.androidanimationdemo.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by heqiang on 17-5-27.
 */

public class SnowUtils {

    private Context mContext;

    public static final int SNOW_LEVEL_SMALL = 1;
    public static final int SNOW_LEVEL_MIDDLE = 2;
    public static final int SNOW_LEVEL_HEAVY = 3;

    private static final int SNOW_NUM_PER_TIME_SMALL = 3;
    private static final int SNOW_NUM_PER_TIME_MIDDLE = 4;
    private static final int SNOW_NUM_PER_TIME_HEAVY = 5;

    private static final int MAX_SPEED_SMALL = 20;
    private static final int MAX_SPEED_MIDDLE = 30;
    private static final int MAX_SPEED_HEAVY = 40;

    private static final int MIN_SPEED_SMALL = 6;
    private static final int MIN_SPEED_MIDDLE = 7;
    private static final int MIN_SPEED_HEAVY = 10;

    private static final int PRODUCE_SNOW_INTERVAL_SMALL = 500;
    private static final int PRODUCE_SNOW_INTERVAL_MIDDLE = 350;
    private static final int PRODUCE_SNOW_INTERVAL_HEAVY = 200;

    private int SNOW_FLAKE_MAX_COUNT = 300;
    private float SCALE_MIN = 0.5f;

    private float[] mAlphas = new float[]{0.3f,0.5f,0.6f,0.8f,1f};
    private float[] mSpeedFactors = new float[]{0.5f,0.7f,0.8f,0.9f,1f};
    private float[] mScaleFactors = new float[]{0.3f,0.4f,0.6f,0.8f,1f};

    private Random mRandom = new Random();
    private Paint mPaint = new Paint();
    private int mSnowLevel = SNOW_LEVEL_MIDDLE;
    private int mProduceNumPerTime = SNOW_NUM_PER_TIME_MIDDLE;
    private int mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_MIDDLE;

    private Bitmap mSnowBitmap;
    private ArrayList<SnowFlake> mSnowFlakeList;
    private Bitmap[] mBitmaps;
    private int mMaxSpeed,mMinSpeed;
    private int mHeight;
    private int mWidth;


    public SnowUtils(Context context){
        mContext = context;
    }

    public void init(int width, int height){
        mHeight = height;
        mWidth = width;
        initSnowFlakes();
    }

    private void initSnowFlakes(){
        mSnowBitmap = ((BitmapDrawable)(mContext.getResources().getDrawable(R.drawable.snow, mContext.getTheme()))).getBitmap();
        mBitmaps = new Bitmap[]{resizeBitmap(mScaleFactors[0]),resizeBitmap(mScaleFactors[1]),
                resizeBitmap(mScaleFactors[2]),resizeBitmap(mScaleFactors[3]),mSnowBitmap};
        mSnowFlakeList = new ArrayList<>(SNOW_FLAKE_MAX_COUNT);
        for(int i = 0; i < SNOW_FLAKE_MAX_COUNT; i++){
            SnowFlake snow = new SnowFlake();
            mSnowFlakeList.add(snow);
        }
    }

    private Bitmap resizeBitmap(float scale){
        Matrix m = new Matrix();
        m.setScale(scale, scale);
        Bitmap resizeBitmap = Bitmap.createBitmap(mSnowBitmap,0,0,mSnowBitmap.getWidth(),mSnowBitmap.getHeight(),m,true);
        return resizeBitmap;
    }

    private void updateSnowFlake(){
        for(int i = 0; i < mSnowFlakeList.size(); i++){
            SnowFlake snow = mSnowFlakeList.get(i);
            if(snow.isLive){
                long currentTime = SystemClock.uptimeMillis();
                int offsetY = (int)(((float)(currentTime - snow.startTimeVertical))/100 * snow.speedVertical);
                snow.y = snow.startY + offsetY;

                if(snow.y > mHeight){
                    snow.isLive = false;
                }
            }
        }
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < mSnowFlakeList.size(); i++) {
            SnowFlake snow = mSnowFlakeList.get(i);
            if(snow.isLive){
                int save = canvas.save();

                //mMatrix.reset();
                //mMatrix.setScale(snow.scale, snow.scale);
                //mMatrix.setScale(1.0f, 1.0f);
                //canvas.setMatrix(mMatrix);
                mPaint.setAlpha(snow.alpha);
                canvas.drawBitmap(mBitmaps[snow.index], snow.x, snow.y, mPaint);
                canvas.restoreToCount(save);
            }
        }
        updateSnowFlake();
    }


    public void setSnowLevel(int level){
        mSnowLevel = level;

        switch (mSnowLevel){
            case SNOW_LEVEL_SMALL:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_SMALL;
                mMaxSpeed = MAX_SPEED_SMALL;
                mMinSpeed = MIN_SPEED_SMALL;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_SMALL;
                break;
            case SNOW_LEVEL_MIDDLE:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_MIDDLE;
                mMaxSpeed = MAX_SPEED_MIDDLE;
                mMinSpeed = MIN_SPEED_MIDDLE;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_MIDDLE;
                break;
            case SNOW_LEVEL_HEAVY:
                mProduceNumPerTime = SNOW_NUM_PER_TIME_HEAVY;
                mMaxSpeed = MAX_SPEED_HEAVY;
                mMinSpeed = MIN_SPEED_HEAVY;
                mProduceSnowInterval = PRODUCE_SNOW_INTERVAL_HEAVY;
                break;
        }
    }

    public int getProduceSnowInterval(){
        return mProduceSnowInterval;
    }


    public void removeAllSnowFlake(){
        for(int i = 0; i < mSnowFlakeList.size(); i++){
            SnowFlake snow = mSnowFlakeList.get(i);
            if(snow.isLive){
                snow.isLive = false;
            }
        }
    }

    public void produceSnowFlake(){
        int produceCount = 0;
        for(int i = 0; i < mSnowFlakeList.size(); i++){
            SnowFlake snow = mSnowFlakeList.get(i);
            if(!snow.isLive){
                int index = mRandom.nextInt(4);
                snow.isLive = true;
                int edge = mContext.getResources().getDimensionPixelOffset(R.dimen.snow_edge);
                snow.x = mRandom.nextInt(mWidth - edge * 2) + edge;
                snow.y = -edge;
                snow.startX = snow.x;
                snow.startY = snow.y;
                snow.alpha = (int)(mAlphas[index]*255);//mRandom.nextInt(155)+100;
                snow.speedVertical = (int)((mRandom.nextInt(mMaxSpeed - mMinSpeed)+mMinSpeed) * mSpeedFactors[index]);//mRandom.nextInt(20)+5;
//                float scale = mRandom.nextFloat();
//                scale = scale > SCALE_MIN ? scale : SCALE_MIN;
//                snow.scale = scale;

                snow.index = index;

                long currentTime = SystemClock.uptimeMillis();
                snow.startTimeHorizontal = snow.startTimeVertical = currentTime;

                produceCount++;
                if(produceCount >= mProduceNumPerTime){
                    break;
                }
            }
        }
    }
}
