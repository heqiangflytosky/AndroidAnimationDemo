package com.android.hq.androidanimationdemo.draggableView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;


public class DraggableView  extends LinearLayout {
    private static final int CLICKRANGE = 30;
    private static final int OFFSET_H = 10;
    private static final int OFFSET_B = 30;
    private static final int OFFSET_T = 75;
    int downX = 0;
    int downY = 0;
    int startX = 0;
    int startY = 0;
    int startTouchViewLeft = 0;
    int startTouchViewTop = 0;
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
                // 不要用 getY() 和 getX()，否则拖动时会抖动
                //https://blog.csdn.net/u010335298/article/details/51891653
                downX = (int)ev.getRawX();
                downY = (int)ev.getRawY();
                startX = (int)ev.getRawX();
                startY = (int)ev.getRawY();
                startTouchViewLeft = getLeft();
                startTouchViewTop = getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(downX - ev.getRawX()) > CLICKRANGE || Math.abs(downY - ev.getRawY()) > CLICKRANGE) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchDragEvent(DragEvent event) {
        return super.dispatchDragEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 不要用 getY() 和 getX()，否则拖动时会抖动
                //https://blog.csdn.net/u010335298/article/details/51891653
                float mx = event.getRawX();
                float my = event.getRawY();
                LayoutParams params = (LayoutParams) getLayoutParams();
                int leftMargin = (int)(mx - startX + startTouchViewLeft);
                int topMargin = (int)(my - startY + startTouchViewTop);
                if (leftMargin < OFFSET_H || (leftMargin + getWidth() + OFFSET_H) > ((ViewGroup)getParent()).getWidth()) {
                    leftMargin = getLeft();
                }

                if (topMargin < OFFSET_T || (topMargin + getHeight() + OFFSET_B) > ((ViewGroup)getParent()).getHeight()) {
                    topMargin = getTop();
                }
                params.leftMargin = leftMargin;
                params.topMargin = topMargin;
                setLayoutParams(params);
                return true;
            case MotionEvent.ACTION_UP:
                startAnimation();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void startAnimation() {
        int left = getLeft();
        int right = ((ViewGroup)getParent()).getWidth() - getRight();
        int top = getTop();
        int bottom = ((ViewGroup)getParent()).getHeight() - getBottom();
        boolean aniLeft = false, aniRight = false, aniTop = false,aniBottom = false;
        if (left < right) {
            if (top < bottom) {
                if (left < top) {
                    aniLeft = true;
                } else {
                    aniTop = true;
                }
            } else {
                if (left < bottom) {
                    aniLeft = true;
                } else {
                    aniBottom = true;
                }
            }
        } else {
            if (top < bottom) {
                if (right < top) {
                    aniRight = true;
                } else {
                    aniTop = true;
                }
            } else {
                if (right < bottom) {
                    aniRight = true;
                } else {
                    aniBottom = true;
                }
            }
        }

        final boolean fAniLeft = aniLeft,fAniRight = aniRight,fAniTop = aniTop, fAniBottom = aniBottom;

        final int initTop = ((LayoutParams) getLayoutParams()).topMargin;
        final int maxTop = (((ViewGroup)getParent()).getHeight() - OFFSET_B - getHeight());

        final int initLeft = ((LayoutParams) getLayoutParams()).leftMargin;
        final int maxLeft = (((ViewGroup)getParent()).getWidth() - OFFSET_H - getWidth());

        ValueAnimator trans = ValueAnimator.ofFloat(0, 1);
        trans.setInterpolator(new LinearInterpolator());
        trans.setDuration(200);
        trans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float percent = (Float)valueAnimator.getAnimatedValue();

                LayoutParams params = (LayoutParams) getLayoutParams();
                int leftMargin = params.leftMargin;
                int topMargin = params.topMargin;
                if (fAniLeft) {
                    leftMargin = (int)(initLeft - (initLeft - OFFSET_H)*percent);
                } else if(fAniRight) {
                    leftMargin = (int)(initLeft + (maxLeft - initLeft)*percent);
                } else if(fAniTop) {
                    topMargin = (int)(initTop - (initTop - OFFSET_T)*percent);
                } else if (fAniBottom) {
                    topMargin = (int)(initTop + (maxTop - initTop)*percent);
                }
                params.topMargin = topMargin;
                params.leftMargin = leftMargin;
                setLayoutParams(params);
            }
        });
        trans.start();
    }
}
