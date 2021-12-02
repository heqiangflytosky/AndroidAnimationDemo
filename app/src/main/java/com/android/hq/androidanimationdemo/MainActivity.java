package com.android.hq.androidanimationdemo;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.hq.androidanimationdemo.bounceballpath.BounceBallPathActivity;
import com.android.hq.androidanimationdemo.draganddrop.DragAndDropActivity;
import com.android.hq.androidanimationdemo.draggableView.DraggableViewActivity;
import com.android.hq.androidanimationdemo.draggableView.DraggableViewActivity2;
import com.android.hq.androidanimationdemo.dynamicanimation.DynamicAnimationActivity;
import com.android.hq.androidanimationdemo.rollingcar.RollingCarActivity;
import com.android.hq.androidanimationdemo.snow.SnowActivity;
import com.android.hq.androidanimationdemo.swipe.SwipeActivity;
import com.android.hq.androidanimationdemo.touch3d.ThreeDimensTouchActivity;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData(){
        ArrayList<ListAdapter.DataBean> list = new ArrayList<>();
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_path_animation_bounce_ball),
                getResources().getString(R.string.desc_path_animation_bounce_ball), BounceBallPathActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_3d_touch),
                getResources().getString(R.string.desc_3d_touch), ThreeDimensTouchActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_snow),
                getResources().getString(R.string.desc_snow), SnowActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_swipe),
                getResources().getString(R.string.desc_swipe), SwipeActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_rolling_car),
                getResources().getString(R.string.desc_rolling_car), RollingCarActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_draggable_view),
                getResources().getString(R.string.desc_draggable_view), DraggableViewActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_draggable_view_2),
                getResources().getString(R.string.desc_draggable_view_2), DraggableViewActivity2.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_drag_drop),
                getResources().getString(R.string.desc_drag_drop), DragAndDropActivity.class));
        list.add(new ListAdapter.DataBean(getResources().getString(R.string.title_dynamic_animation),
                getResources().getString(R.string.desc_dynamic_animation), DynamicAnimationActivity.class));
        mListAdapter.updateData(list);
    }

//    public static void startAnimActivity(Activity activity, int type){
//        Intent intent = null;
//        if(type == MainActivity.BOUNCE_BALL_DEMO){
//            intent = new Intent(activity, BounceBallPathActivity.class);
//        }else if(type == MainActivity.THREE_DIMENS_TOUCH_DEMO){
//            intent = new Intent(activity, ThreeDimensTouchActivity.class);
//        }else if(type == MainActivity.SNOW_DEMO){
//            intent = new Intent(activity, SnowActivity.class);
//        }
//        activity.startActivity(intent);
//    }
}
