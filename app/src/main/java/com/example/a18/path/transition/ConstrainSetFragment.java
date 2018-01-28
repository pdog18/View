package com.example.a18.path.transition;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.OnClick;


public class ConstrainSetFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ConstraintLayout transitions_container;
//    @BindView(R.id.text)
//    TextView textView;
    private boolean visible;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constrain_set;
    }


    @OnClick(R.id.button3)
    void button() {

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(getContext(), R.layout.fragment_constrain_set_detail);

        TransitionManager.beginDelayedTransition(transitions_container);  // myView: 当前视图 ConstratintLayout 的 id

        constraintSet.applyTo(transitions_container);

//        constraintSet.apply(myView);

//        TransitionManager.beginDelayedTransition(transitions_container);
//        textView.setVisibility((visible = !visible) ? View.VISIBLE : View.GONE);
    }
}
