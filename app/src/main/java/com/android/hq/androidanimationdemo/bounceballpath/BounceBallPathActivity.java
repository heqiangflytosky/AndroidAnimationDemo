package com.android.hq.androidanimationdemo.bounceballpath;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-10-27.
 */
public class BounceBallPathActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_ball_path_animation_layout);
        testPathAnimator();
    }

    public void testPathAnimator(){
        final FrameLayout l = (FrameLayout) findViewById(R.id.root_view);

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.dot);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.addView(imageView, param);

        Path path = new Path();
        path.moveTo(200, 200);

        path.quadTo(800, 200, 800, 800);

        PathInterpolator pathInterpolator = new PathInterpolator(0.33f,0f,0.12f,1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                l.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                l.removeView(imageView);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        ObjectAnimator scalex = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 1.0f, 0.3f);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 1.0f, 0.3f);
        ObjectAnimator traslateAnimator = ObjectAnimator.ofFloat(imageView, "x", "y", path);

        animSet.playTogether(scalex, scaley, traslateAnimator);

        animSet.setInterpolator(pathInterpolator);
        animSet.setDuration(1500);
        animSet.start();
    }
}
