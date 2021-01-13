package com.android.hq.androidanimationdemo.draggableView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 2019/4/16.
 */

public class DraggableViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除ActionBar阴影
        getActionBar().setElevation(0f);
        setContentView(R.layout.activity_draggable_view);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.heqiangfly.com"));
                startActivity(intent);
            }
        });
    }
}
