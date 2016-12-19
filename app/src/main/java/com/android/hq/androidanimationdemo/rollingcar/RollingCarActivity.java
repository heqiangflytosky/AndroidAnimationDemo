package com.android.hq.androidanimationdemo.rollingcar;

import android.app.Activity;
import android.os.Bundle;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-12-15.
 */
public class RollingCarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0f);//去除ActionBar阴影
        setContentView(R.layout.activity_rolling_car);
    }


}
