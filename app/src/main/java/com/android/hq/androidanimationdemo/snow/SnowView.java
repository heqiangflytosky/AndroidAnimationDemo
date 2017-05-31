package com.android.hq.androidanimationdemo.snow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.android.hq.androidanimationdemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by heqiang on 16-12-1.
 */
public class SnowView extends View {

    private SnowUtils mSnowUtils;

    public static final int MSG_PRODUCE_SNOW = 1;
    public static final int MSG_UPDATE_SNOW = 2;
    public static final int MSG_INVALIDATE_VIEW = 3;

    public static final int SPEED_ACCELARATE = 1;
    public static final int SPEED_DECELARATE = 2;

    public int REFRESH_VIEW_INTERVAL = 30;



    private CountDownLatch mMeasureLatch = new CountDownLatch(1);

    public SnowView(Context context) {
        super(context);
        initSnowFlakes();
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSnowFlakes();
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSnowFlakes();
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSnowFlakes();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mSnowUtils != null){
            mSnowUtils.draw(canvas);
        }
    }

    private void initSnowFlakes(){
        mSnowUtils = new SnowUtils(getContext());

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSnowUtils.init(getMeasuredWidth(), getMeasuredHeight());
                mMeasureLatch.countDown();
            }
        });
    }

    public void produceSnowFlake(){
        mSnowUtils.produceSnowFlake();
    }

    private int getProduceSnowInterval(){
        return mSnowUtils.getProduceSnowInterval();
    }

    public void startSnowAnim(int level){
        mSnowUtils.setSnowLevel(level);
        startThread.start();
    }

    private void startSnowAnim(){
        mSnowHandler.removeMessages(MSG_PRODUCE_SNOW);
        mSnowHandler.obtainMessage(MSG_PRODUCE_SNOW).sendToTarget();
    }

    public void stopAnim(){
        mSnowUtils.removeAllSnowFlake();
        mSnowHandler.removeCallbacksAndMessages(null);
    }

    public void changeSnowLevel(int level){
        mSnowUtils.setSnowLevel(level);
        stopAnim();
        startSnowAnim();
    }

    private SnowHandler mSnowHandler = new SnowHandler(this);
    public static class SnowHandler extends Handler{
        private WeakReference<SnowView> mSnowView;
        public SnowHandler(SnowView view){
            mSnowView = new WeakReference<SnowView>(view);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_PRODUCE_SNOW:
                    if(mSnowView.get() != null){
                        mSnowView.get().produceSnowFlake();
                        removeMessages(MSG_PRODUCE_SNOW);
                        sendMessageDelayed(obtainMessage(MSG_PRODUCE_SNOW), mSnowView.get().getProduceSnowInterval());

                        removeMessages(MSG_INVALIDATE_VIEW);
                        sendMessage(obtainMessage(MSG_INVALIDATE_VIEW));
                    }
                    break;
                case MSG_UPDATE_SNOW:

                    break;
                case MSG_INVALIDATE_VIEW:
                    if(mSnowView.get() != null){
                        mSnowView.get().postInvalidateOnAnimation();
                        removeMessages(MSG_INVALIDATE_VIEW);
                        sendMessageDelayed(obtainMessage(MSG_INVALIDATE_VIEW), mSnowView.get().REFRESH_VIEW_INTERVAL);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private Thread startThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if(getContext() != null && !((Activity)getContext()).isDestroyed()){
                try {
                    mMeasureLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(getContext() != null && !((Activity)getContext()).isDestroyed()){
                    startSnowAnim();
                }
            }
        }
    });
}
