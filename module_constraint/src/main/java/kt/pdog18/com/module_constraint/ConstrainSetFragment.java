package kt.pdog18.com.module_constraint;

import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;
import kt.pdog18.com.base.BaseFragment;
import timber.log.Timber;


public class ConstrainSetFragment extends BaseFragment {

    @BindViews({
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9,
    })
    List<Button> buttons;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint_layout;
    }

    int start = 0;

    @OnClick(R.id.button5)
    void animate() {
        for (Button button : buttons) {
            if (start > 360) {
                start = 0;
            }
            start += 30;
            animate(button,start);
        }
    }

    private void animate(View view, int start) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                layoutParams.circleAngle = fraction * 360 + start + 45;
                view.requestLayout();
                Timber.d("layoutParams.circleAngle = %s", layoutParams.circleAngle);
            }
        });
        animator.setInterpolator(new SpringInterpolator(0.4f));
        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.start();
    }
}
