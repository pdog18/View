package com.example.a18.path.transition;

import android.support.transition.ArcMotion;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;


public class PathFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ViewGroup transitionsContainer;
    @BindView(R.id.button)
    Button button;
    private boolean mToRightAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_path;
    }


    @OnClick(R.id.button)
    void button() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setPathMotion(new ArcMotion());
        changeBounds.setDuration(300);

        TransitionManager.beginDelayedTransition(transitionsContainer, changeBounds);

        mToRightAnimation = !mToRightAnimation;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
        params.gravity = mToRightAnimation ? (Gravity.RIGHT | Gravity.BOTTOM) :
            (Gravity.LEFT | Gravity.TOP);
        button.setLayoutParams(params);
    }
}
