package com.android.hq.androidanimationdemo.snow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-12-1.
 */
public class SnowActivity extends Activity {

    private SnowView mSnowView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0f);//去除ActionBar阴影
        setContentView(R.layout.activity_snow);
        mSnowView = (SnowView) findViewById(R.id.snow_view);
        mSnowView.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_snow, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.level_small:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_SMALL);
                return true;
            case R.id.level_middle:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_MIDDLE);
                return true;
            case R.id.level_heavy:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_HEAVY);
                return true;

        }
        return super.onMenuItemSelected(featureId, item);
    }
}
