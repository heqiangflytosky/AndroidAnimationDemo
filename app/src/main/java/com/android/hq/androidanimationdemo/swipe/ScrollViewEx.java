package com.android.hq.androidanimationdemo.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-12-8.
 */
public class ScrollViewEx extends ScrollView implements SwipeHelper.Callback {
    private int[] mColorArray = {0xFFD1EEEE,0xFFFFBBFF,0xFFFFB90F,0xFFCD9B9B,0xFF9F79EE,0xFF548B54};
    private LinearLayout mContainerView;
    private SwipeHelper mSwipeHelper;
    public ScrollViewEx(Context context) {
        super(context);
        initView();
    }

    public ScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSwipeHelper.onInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mSwipeHelper.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    @Override
    public void removeViewInLayout(final View view) {
        //mSwipeHelper.dismissChild(view,0);
    }


    @Override
    public View getChildAtPosition(MotionEvent ev) {
        final float x = ev.getX() + getScrollX();
        final float y = ev.getY() + getScrollY();
        for (int i = 0; i < mContainerView.getChildCount(); i++) {
            View item = mContainerView.getChildAt(i);
            if (x >= item.getLeft() && x < item.getRight()
                    && y >= item.getTop() && y < item.getBottom()) {

                return item;
            }
        }
        return null;
    }

    @Override
    public View getChildContentView(View v) {
        return v;
    }

    @Override
    public boolean canChildBeDismissed(View v) {
        return true;
    }

    @Override
    public void onBeginDrag(View v) {
        requestDisallowInterceptTouchEvent(true);
        v.setActivated(true);
    }

    @Override
    public void onChildDismissed(View v) {
        mContainerView.removeView(v);
    }

    @Override
    public void onDragCancelled(View v) {
        v.setActivated(false);
    }

    private void initView(){
        mSwipeHelper = new SwipeHelper(SwipeHelper.X, this, getResources().getDisplayMetrics().density, ViewConfiguration.get(getContext()).getScaledPagingTouchSlop());
        mContainerView = new LinearLayout(getContext());
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mContainerView.setLayoutParams(para);
        mContainerView.setOrientation(LinearLayout.VERTICAL);
        addView(mContainerView);

    }

    public void addViews(int count){
        for(int i = 0 ;i < count; i++){
            View v = View.inflate(getContext(), R.layout.swipe_item, null);
            TextView textView = (TextView) v.findViewById(R.id.content_title);
            textView.setText(String.valueOf(i));
            textView.setBackgroundColor(mColorArray[i%6]);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mContainerView.addView(v,lp);
        }

    }
}
