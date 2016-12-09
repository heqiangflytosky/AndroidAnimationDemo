package com.android.hq.androidanimationdemo.swipe;

import android.app.Activity;
import android.os.Bundle;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-12-8.
 */
public class SwipeActivity extends Activity {
    private ScrollViewEx mScrollViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0f);//去除ActionBar阴影
        setContentView(R.layout.activity_swipe);

        mScrollViewEx = (ScrollViewEx) findViewById(R.id.scrollView);

        mScrollViewEx.addViews(30);

    }
}
