package kt.pdog18.com.module_transition;

import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewCompat;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import kt.pdog18.com.base.BaseFragment;
import timber.log.Timber;


public class ConstrainSetFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ConstraintLayout transitions_container;
    //    @BindView(R.id.text)
//    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constrain_set;
    }

    @BindView(R.id.view)
    View view;
    @BindView(R.id.frameLayout)
    View frameLayout;

    @RequiresApi(23)
    @OnClick(R.id.view)
    void setClipBounds() {
        Rect sourceRect = new Rect();
        view.getClipBounds(sourceRect);
        Timber.d("sourceRect = %s", sourceRect);

        Rect rect = new Rect(-500, -500, 1200, 1200);
        ViewCompat.setClipBounds(frameLayout,rect);
        ViewCompat.setClipBounds(view, rect);

        Timber.d("rect = %s", rect);

        view.getClipBounds(sourceRect);
        Timber.d("sourceRect = %s", sourceRect);


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
