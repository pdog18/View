package com.example.a18.path.constraintlayout;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

import timber.log.Timber;

public class ConstraintLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);

//        new Handler().postDelayed(() -> {
//            ConstraintLayout myView = findViewById(R.id.constrain);
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(ConstraintLayoutActivity.this, R.layout.constraint_detail);
//
//            TransitionManager.beginDelayedTransition(myView);  // myView: 当前视图 ConstratintLayout 的 id
//
//            constraintSet.applyTo(myView);
//        }, 1000);

//

        View button6 = findViewById(R.id.button6);
        View button7 = findViewById(R.id.button7);
        View button8 = findViewById(R.id.button8);
        View button9 = findViewById(R.id.button9);
        anmite(button6,0);
        anmite(button7,90);
        anmite(button8,180);
        anmite(button9,270);
    }

    private void anmite(View view,int start) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                layoutParams.circleAngle = fraction * 360 + start;
                view.requestLayout();
                Timber.d("layoutParams.circleAngle = %s", layoutParams.circleAngle);
            }
        });
        animator.setInterpolator(new SpringInterpolator(0.4f));
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.start();
    }
}
