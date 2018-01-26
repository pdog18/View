package com.example.a18.path.transition;

import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;


public class AutoFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ViewGroup transitions_container;
    @BindView(R.id.text)
    TextView textView;
    private boolean visible;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auto;
    }


    @OnClick(R.id.button)
    void button() {
        TransitionManager.beginDelayedTransition(transitions_container);
        textView.setVisibility((visible = !visible) ? View.VISIBLE : View.GONE);
    }
}
