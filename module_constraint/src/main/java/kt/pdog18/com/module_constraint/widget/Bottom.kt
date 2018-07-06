package kt.pdog18.com.module_constraint.widget

import android.content.Context
import android.graphics.PointF
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kt.pdog18.com.module_constraint.R
import kt.pdog18.com.module_constraint.util.getRadian
import timber.log.Timber


class Bottom constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val apf: AnglePointF = AnglePointF(0f)
    private val centerPointF: PointF by lazy {
        PointF(width * 1f / 2, height * 1f / 2)
    }

    init {
        View.inflate(context, R.layout.bottom, this)
        val cl = getChildAt(0) as ViewGroup
        cl.children
            .map {
                val p = it.layoutParams as ConstraintLayout.LayoutParams
                Timber.d("p = ${p}")
            }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                requestDisallowInterceptTouchEvent(true)
                return true
            }
            else -> {
            }

        }
        apf.set(event.x, event.y, getRadian(event.x, event.y, centerPointF.x, centerPointF.y))
        Timber.d("apf.angle = ${apf.radian}")
        return super.onTouchEvent(event)
    }
}

data class AnglePointF(var radian: Float) {
    private val point = PointF()

    fun set(x: Float, y: Float, radian: Float) {
        this.point.set(x, y)
        this.radian = radian
    }
}
