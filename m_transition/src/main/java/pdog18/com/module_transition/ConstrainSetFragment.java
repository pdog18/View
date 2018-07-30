package pdog18.com.module_transition;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewCompat;
import android.view.View;

import pdog18.com.base.BaseFragment;
import timber.log.Timber;


public class ConstrainSetFragment extends BaseFragment {

    ConstraintLayout transitions_container;
    //    @BindView(R.id.text)
//    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constrain_set;
    }

    View view1;
    View frameLayout;
    View button3;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transitions_container = view.findViewById(R.id.transitions_container);
        view1 = view.findViewById(R.id.view);
        frameLayout = view.findViewById(R.id.frameLayout);
        button3 = view.findViewById(R.id.button3);
        view1.setOnClickListener(v -> {
            setClipBounds();
        });
        button3.setOnClickListener(v -> {
            button();
        });
    }

    @RequiresApi(23)
    void setClipBounds() {
        Rect sourceRect = new Rect();
        view1.getClipBounds(sourceRect);
        Timber.d("sourceRect = %s", sourceRect);

        Rect rect = new Rect(-500, -500, 1200, 1200);
        ViewCompat.setClipBounds(frameLayout,rect);
        ViewCompat.setClipBounds(view1, rect);

        Timber.d("rect = %s", rect);

        view1.getClipBounds(sourceRect);
        Timber.d("sourceRect = %s", sourceRect);
    }

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
