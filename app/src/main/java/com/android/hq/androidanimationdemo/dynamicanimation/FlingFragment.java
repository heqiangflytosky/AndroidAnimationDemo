package com.android.hq.androidanimationdemo.dynamicanimation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.fragment.app.Fragment;

import com.android.hq.androidanimationdemo.R;

public class FlingFragment extends Fragment {
    public static String TAG = "FlingFragment";
    private LinearLayout mShowingView;
    private RelativeLayout mControllerView;
    private ImageView mImage;
    private float mX;
    private float mY;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_animation_fling,null,false);
        mImage = view.findViewById(R.id.view);
        mX = mImage.getX();
        mY = mImage.getY();
        view.findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlingAnimation flingAnim = new FlingAnimation(mImage, DynamicAnimation.TRANSLATION_X)
                        // Sets the start velocity to -2000 (pixel/s)
                        .setStartVelocity(2000)
                        // Optional but recommended to set a reasonable min and max range for the animation.
                        // In this particular case, we set the min and max to -200 and 2000 respectively.
                        .setFriction(0.6f)
                        .setMinValue(-200)
                        .setMaxValue(1000);
                flingAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b, float v, float v1) {
                        mImage.setX(mX);
                        mImage.setY(mY);
                    }
                });
                flingAnim.start();

            }
        });
        return view;
    }
}
