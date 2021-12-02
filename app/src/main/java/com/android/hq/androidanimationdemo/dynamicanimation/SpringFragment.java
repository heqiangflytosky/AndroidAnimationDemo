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
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;

import com.android.hq.androidanimationdemo.R;

public class SpringFragment extends Fragment {
    public static String TAG = "SpringFragment";
    private LinearLayout mShowingView;
    private RelativeLayout mControllerView;
    private ImageView mImage;
    private float mX;
    private float mY;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_animation_spring,null,false);
        mImage = view.findViewById(R.id.view);
        mX = mImage.getX();
        mY = mImage.getY();
        view.findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SpringForce springForce = new SpringForce(0)
//                        .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
//                        .setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//                final SpringAnimation anim = new SpringAnimation(mImage ,SpringAnimation.TRANSLATION_Y)
//                        .setSpring(springForce);
//                anim.setStartValue(-500);
//                anim.start();

                SpringForce springForce = new SpringForce(0)
                        .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                        .setStiffness(SpringForce.STIFFNESS_VERY_LOW);
                final SpringAnimation anim = new SpringAnimation(mImage ,SpringAnimation.TRANSLATION_Y)
                        .setSpring(springForce);
                anim.animateToFinalPosition(500);

            }
        });

        return view;
    }
}
