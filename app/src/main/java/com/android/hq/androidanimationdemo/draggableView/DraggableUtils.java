package com.android.hq.androidanimationdemo.draggableView;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

public class DraggableUtils {
    private static final int CLICKRANGE = 30;

    public static final int OFFSET_H = 10;
    public static final int OFFSET_B = 30;
    public static final int OFFSET_T = 75;

    public static int downX = 0;
    public static int downY = 0;
    public static int startX = 0;
    public static int startY = 0;
    public static int startTouchViewLeft = 0;
    public static int startTouchViewTop = 0;
    public static void handleActionDown(View view, MotionEvent ev) {
        // 不要用 getY() 和 getX()，否则拖动时会抖动
        //https://blog.csdn.net/u010335298/article/details/51891653
        downX = (int)ev.getRawX();
        downY = (int)ev.getRawY();
        startX = (int)ev.getRawX();
        startY = (int)ev.getRawY();
        startTouchViewLeft = view.getLeft();
        startTouchViewTop = view.getTop();
    }

    public static boolean shouldHandlerActionMove(View view, MotionEvent ev) {
        if (Math.abs(downX - ev.getRawX()) > CLICKRANGE || Math.abs(downY - ev.getRawY()) > CLICKRANGE) {
            return true;
        } else {
            return false;
        }
    }

    public static void handleActionMove(View view, MotionEvent event) {
        // 不要用 getY() 和 getX()，否则拖动时会抖动
        //https://blog.csdn.net/u010335298/article/details/51891653
        float mx = event.getRawX();
        float my = event.getRawY();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        int leftMargin = (int)(mx - startX + startTouchViewLeft);
        int topMargin = (int)(my - startY + startTouchViewTop);
        if (leftMargin < DraggableUtils.OFFSET_H || (leftMargin + view.getWidth() + DraggableUtils.OFFSET_H) > ((ViewGroup)view.getParent()).getWidth()) {
            leftMargin = view.getLeft();
        }

        if (topMargin < DraggableUtils.OFFSET_T || (topMargin + view.getHeight() + DraggableUtils.OFFSET_B) > ((ViewGroup)view.getParent()).getHeight()) {
            topMargin = view.getTop();
        }
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        view.setLayoutParams(params);
    }

    public static void startReleaseAnimation(final View view) {
        int left = view.getLeft();
        int right = ((ViewGroup)view.getParent()).getWidth() - view.getRight();
        int top = view.getTop();
        int bottom = ((ViewGroup)view.getParent()).getHeight() - view.getBottom();
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

        final int initTop = ((LinearLayout.LayoutParams) view.getLayoutParams()).topMargin;
        final int maxTop = (((ViewGroup)view.getParent()).getHeight() - OFFSET_B - view.getHeight());

        final int initLeft = ((LinearLayout.LayoutParams) view.getLayoutParams()).leftMargin;
        final int maxLeft = (((ViewGroup)view.getParent()).getWidth() - OFFSET_H - view.getWidth());

        ValueAnimator trans = ValueAnimator.ofFloat(0, 1);
        trans.setInterpolator(new LinearInterpolator());
        trans.setDuration(200);
        trans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float percent = (Float)valueAnimator.getAnimatedValue();

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
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
                view.setLayoutParams(params);
            }
        });
        trans.start();
    }
}
