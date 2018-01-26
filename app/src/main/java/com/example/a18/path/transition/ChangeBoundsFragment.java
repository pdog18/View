package com.example.a18.path.transition;

import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;


public class ChangeBoundsFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ViewGroup transitionsContainer;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button)
    TextView button;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_intercept;
    }

    boolean mToRightAnimation;

    @OnClick(R.id.button)
    void button() {
        mToRightAnimation ^= true;
        Transition transition = new ChangeBounds();
        transition.setDuration(mToRightAnimation ? 700 : 300);
        transition.setInterpolator(mToRightAnimation ? new FastOutSlowInInterpolator() : new AccelerateInterpolator());
        transition.setStartDelay(mToRightAnimation ? 0 : 500);
        TransitionManager.beginDelayedTransition(transitionsContainer, transition);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
        params.gravity = mToRightAnimation ? (Gravity.RIGHT | Gravity.TOP) : (Gravity.LEFT | Gravity.TOP);
        button.setLayoutParams(params);
    }
}
