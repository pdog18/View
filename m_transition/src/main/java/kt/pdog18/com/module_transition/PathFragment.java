package kt.pdog18.com.module_transition;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.ArcMotion;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import kt.pdog18.com.base.BaseFragment;


public class PathFragment extends BaseFragment {

    ViewGroup transitions_container;
    Button button;
    private boolean mToRightAnimation;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transitions_container = view.findViewById(R.id.transitions_container);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new ArcMotion());
            changeBounds.setDuration(300);

            TransitionManager.beginDelayedTransition(transitions_container, changeBounds);

            mToRightAnimation = !mToRightAnimation;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
            params.gravity = mToRightAnimation ? (Gravity.RIGHT | Gravity.BOTTOM) :
                (Gravity.LEFT | Gravity.TOP);
            button.setLayoutParams(params);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_path;
    }


}