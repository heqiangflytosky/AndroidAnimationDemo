package com.android.hq.androidanimationdemo.dynamicanimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.hq.androidanimationdemo.R;

public class DynamicAnimationActivity extends AppCompatActivity {

    enum ANIMATION_TYPE {
        SPRING,
        FLING
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_animation);
        switchView(ANIMATION_TYPE.FLING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dynamic_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fling) {
            switchView(ANIMATION_TYPE.FLING);
            return true;
        } else if (id == R.id.action_spring) {
            switchView(ANIMATION_TYPE.SPRING);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchView(ANIMATION_TYPE type) {
        switch (type) {
            case FLING:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FlingFragment.TAG);
                if (fragment != null) {
                    ft.show(fragment);
                } else {
                    ft.add(R.id.container,new FlingFragment(),FlingFragment.TAG);
                }
                ft.commit();
                break;

            case SPRING:
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                Fragment fSpring = getSupportFragmentManager().findFragmentByTag(SpringFragment.TAG);
                if (fSpring != null) {
                    ft1.show(fSpring);
                } else {
                    ft1.add(R.id.container,new SpringFragment(),SpringFragment.TAG);
                }
                ft1.commit();
                break;
        }
    }
}