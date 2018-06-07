package kt.pdog18.com.module_constraint;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import kt.pdog18.com.base.BaseFragment;
import kt.pdog18.com.base.Layout;
import timber.log.Timber;


@Layout(layoutId = R.layout.activity_constraint_layout)
public class ConstrainSetFragment extends BaseFragment {

    Button button5;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button5 = view.findViewById(R.id.button5);

        final ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(view.findViewById(R.id.button6));
        buttons.add(view.findViewById(R.id.button7));
        buttons.add(view.findViewById(R.id.button8));
        buttons.add(view.findViewById(R.id.button9));
        button5.setOnClickListener(v -> {
            for (Button button : buttons) {
                if (start > 360) {
                    start = 0;
                }
                start += 30;
                animate(button, start);
            }
        });
    }

    int start = 0;


    private void animate(View view, int start) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(1);
        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            layoutParams.circleAngle = fraction * 360 + start + 45;
            view.requestLayout();
            Timber.d("layoutParams.circleAngle = %s", layoutParams.circleAngle);
        });
        animator.setInterpolator(new SpringInterpolator(0.4f));
        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.start();
    }
}
