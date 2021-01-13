package com.android.hq.androidanimationdemo.draggableView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.hq.androidanimationdemo.R;

/**
 * 在 OnClickListener 中处理 touch 事件
 */

public class DraggableViewActivity2 extends Activity {
    boolean mTouchIntercept = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除ActionBar阴影
        getActionBar().setElevation(0f);
        setContentView(R.layout.activity_draggable_view_2);
        final ImageView imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.heqiangfly.com"));
                startActivity(intent);
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mTouchIntercept = false;
                    DraggableUtils.handleActionDown(imageView,event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (DraggableUtils.shouldHandlerActionMove(imageView,event)) {
                        mTouchIntercept = true;
                        DraggableUtils.handleActionMove(imageView,event);
                    }

                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    if (mTouchIntercept) {
                        DraggableUtils.startReleaseAnimation(imageView);
                    } else {
                        v.performClick();
                    }
                }
                return true;
            }
        });
    }
}
