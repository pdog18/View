package kt.pdog18.com.module_constraint.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.withTranslation
import pdog18.radianutil.angle2Radian
import pdog18.radianutil.getRadian
import pdog18.radianutil.rectifyWithRadianAndRadius
import timber.log.Timber


class SkyWheels(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val center by lazy {
        PointF(width / 2f, height / 2f)
    }

    private val paint: Paint = Paint().apply {
        color = Color.RED
    }
    private val layoutPointF = PointF(0f, 0f)

    private val rectfyRidianPointF = PointF(0f, 0f)


    init {

        setWillNotDraw(false)

        (0..11).forEach {
            addView(TextView(context).apply {
                setBackgroundColor(Color.BLUE)
                layoutParams = LayoutParams(160, 160)
            })
        }
    }

    private var lastRadian = 0f

    private var rectifyRadian = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
                lastRadian = getRadian(event, center)
            }
            else -> {
                val currentRadian = getRadian(event, center)
                val dr = currentRadian - lastRadian
                rectifyRadian += dr
                lastRadian = currentRadian
                requestLayout()
            }
        }

        return true
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


        val radius = width / 2f
        val radian = 360f.angle2Radian().toFloat() / childCount

        (0 until childCount).forEach {
            val child = getChildAt(it)
            layoutPointF.rectifyWithRadianAndRadius(radius, radian * it + rectifyRadian)
            val dx = layoutPointF.x.toInt() - child.width / 2 + width / 2
            val dy = layoutPointF.y.toInt() - child.height / 2 + height / 2

            Timber.d("child = ${child}")

            child.layout(child.left + dx, child.top + dy, child.right + dx, child.bottom + dy)
            with(child as TextView) {
                this.text = "${child.left}  -  ${child.top}"
            }
            Timber.d("child.left = ${child}")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = width / 2f
        val radian = 360f.angle2Radian().toFloat() / childCount

        (0 until childCount).forEach {
            layoutPointF.rectifyWithRadianAndRadius(radius, radian * it)

            val dx = layoutPointF.x
            val dy = layoutPointF.y

            canvas.withTranslation(x = width / 2f, y = height / 2f) {
                drawLine(dx, dy, 0f, 0f, paint)
            }
        }
    }
}
