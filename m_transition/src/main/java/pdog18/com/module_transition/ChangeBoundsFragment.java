package pdog18.com.module_transition;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import pdog18.com.base.BaseFragment;


public class ChangeBoundsFragment extends BaseFragment {

    ViewGroup transitions_container;
    TextView text;
    TextView button;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_intercept;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transitions_container = view.findViewById(R.id.transitions_container);
        text = view.findViewById(R.id.text);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(v -> {
            mToRightAnimation ^= true;
            Transition transition = new ChangeBounds();
            transition.setDuration(mToRightAnimation ? 700 : 300);
            transition.setInterpolator(mToRightAnimation ? new FastOutSlowInInterpolator() : new AccelerateInterpolator());
            transition.setStartDelay(mToRightAnimation ? 0 : 500);
            TransitionManager.beginDelayedTransition(transitions_container, transition);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
            params.gravity = mToRightAnimation ? (Gravity.RIGHT | Gravity.TOP) : (Gravity.LEFT | Gravity.TOP);
            button.setLayoutParams(params);
        });
    }

    boolean mToRightAnimation;

}
