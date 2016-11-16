package com.android.hq.androidanimationdemo.touch3d;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by heqiang on 16-11-16.
 */
public class TopLayout extends LinearLayout {
    private View mView;
    public TopLayout(Context context) {
        super(context);
    }

    public TopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDrawView(View v){
        mView = v;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mView != null){
            int sv = canvas.save();
            canvas.scale(0.8f,0.8f,getWidth()*0.5f,getHeight()*0.5f);
            mView.draw(canvas);
            canvas.restoreToCount(sv);
        }
    }
}
