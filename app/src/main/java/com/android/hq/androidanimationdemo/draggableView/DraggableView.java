package com.android.hq.androidanimationdemo.draggableView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class DraggableView  extends LinearLayout {

    public DraggableView(Context context) {
        super(context);
    }

    public DraggableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DraggableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                DraggableUtils.handleActionDown(this,ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (DraggableUtils.shouldHandlerActionMove(this,ev)) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                DraggableUtils.handleActionMove(this,event);
                return true;
            case MotionEvent.ACTION_UP:
                DraggableUtils.startReleaseAnimation(this);
                break;
        }

        return super.onTouchEvent(event);
    }


}
