package com.android.hq.androidanimationdemo.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.hq.androidanimationdemo.R;

public class DragAndDropActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ViewGroup mTargetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);
        mImageView = findViewById(R.id.image_view);
        mImageView.setTag("mImageView");
        mTargetView = findViewById(R.id.target);
        initEvents();
    }

    private void initEvents() {
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(mImageView.getTag().toString());
                //ClipData代表剪贴板中剪切数据
                ClipData dragData = new ClipData(v.getTag().toString(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                //拖放阴影构造者对象，用于构造拖放阴影。
                mImageView.startDrag(dragData, new View.DragShadowBuilder(mImageView), null, 0);
                return false;
            }
        });

        mImageView.setOnDragListener(new OriginDragEventListener());
        mTargetView.setOnDragListener(new TargetDragEventListener());
    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        public MyDragShadowBuilder(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
        }
    }

    private class OriginDragEventListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.e("Test","Origin DragEvent.ACTION_DRAG_STARTED");
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        return true;
                    }
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.e("Test","Origin DragEvent.ACTION_DRAG_LOCATION");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.e("Test","Origin DragEvent.ACTION_DRAG_ENTERED");
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e("Test","Origin DragEvent.ACTION_DRAG_EXITED");
                    break;

                case DragEvent.ACTION_DROP:
                    Log.e("Test","Origin DragEvent.ACTION_DROP");
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.e("Test","Origin DragEvent.ACTION_DRAG_ENDED");
                    break;
            }
            return false;
        }
    }

    private class TargetDragEventListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.e("Test","Target DragEvent.ACTION_DRAG_STARTED");
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        mImageView.setVisibility(View.GONE);
                        return true;
                    }
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.e("Test","Target DragEvent.ACTION_DRAG_LOCATION");
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.e("Test","Target DragEvent.ACTION_DRAG_ENTERED");
                    mTargetView.setBackgroundColor(0xff0000ff);
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e("Test","Target DragEvent.ACTION_DRAG_EXITED");
                    mTargetView.setBackgroundResource(R.color.text_color_gray);
                    return true;

                case DragEvent.ACTION_DROP:
                    Log.e("Test","Target DragEvent.ACTION_DROP");
                    mTargetView.setBackgroundResource(R.color.text_color_gray);
                    ((ViewGroup)mImageView.getParent()).removeView(mImageView);
                    mTargetView.addView(mImageView);
                    mImageView.setX(event.getX()-(mImageView.getWidth()/2));
                    mImageView.setY(event.getY()-(mImageView.getHeight()/2));
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.e("Test","Target DragEvent.ACTION_DRAG_ENDED");
                    mImageView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    }
}
