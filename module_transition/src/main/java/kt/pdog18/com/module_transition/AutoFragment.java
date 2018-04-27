package kt.pdog18.com.module_transition;

import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import kt.pdog18.com.base.BaseFragment;


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
