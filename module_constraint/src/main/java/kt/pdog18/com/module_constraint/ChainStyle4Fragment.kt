package kt.pdog18.com.module_constraint


import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.fragment_match_constraint4.*
import kt.pdog18.com.base.BaseFragment
import kt.pdog18.com.base.Layout

@Layout(R.layout.fragment_match_constraint4)
class ChainStyle4Fragment : BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab.setOnClickListener {
            val fab1_params = fab1.layoutParams as ConstraintLayout.LayoutParams
            val fab2_params = fab2.layoutParams as ConstraintLayout.LayoutParams
            val fab3_params = fab3.layoutParams as ConstraintLayout.LayoutParams


            //弹出动画
            val translateAnimator = ValueAnimator.ofFloat(1f).apply {
                duration = 100
                addUpdateListener { animation ->
                    val fraction = animation.animatedFraction
                    fab1_params.circleRadius = (fraction * 300).toInt()
                    fab2_params.circleRadius = (fraction * 300).toInt()
                    fab3_params.circleRadius = (fraction * 300).toInt()
                    fab1.requestLayout()
                    fab2.requestLayout()
                    fab3.requestLayout()
                }
            }


//        animator.setRepeatCount(ValueAnimator.INFINITE);
            val circleAngle = fab1_params.circleAngle

            //打开动画
            val sweepValueAnimator = ValueAnimator.ofFloat(1f).apply {
                duration = 500
                addUpdateListener { animation ->
                    val fraction = animation.animatedFraction
                    fab1_params.circleAngle = circleAngle + (fraction * 0).toInt()
                    fab2_params.circleAngle = circleAngle + (fraction * 60).toInt()
                    fab3_params.circleAngle = circleAngle + (fraction * 120).toInt()

                    fab1.requestLayout()
                    fab2.requestLayout()
                    fab3.requestLayout()
                }
            }

            translateAnimator.doOnEnd { _ ->
                sweepValueAnimator.start()
            }
            translateAnimator.start()
        }
    }
}
