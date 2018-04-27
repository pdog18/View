package kt.pdog18.com.module_transition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Slide;
import android.support.transition.TransitionManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import kt.pdog18.com.base.BaseFragment;


public class LifelineFragment extends BaseFragment {

    @BindView(R.id.transitions_container)
    ViewGroup transitions_container;
    @BindView(R.id.linearlayout)
    View linearlayout;
    private boolean visible = true;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lifeline;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter());
    }

    @OnClick(R.id.button)
    void button() {
        Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
        TransitionManager.beginDelayedTransition(transitions_container,new Slide().setDuration(300));
        linearlayout.setVisibility((visible ^= true) ? View.VISIBLE : View.GONE);
    }
}
