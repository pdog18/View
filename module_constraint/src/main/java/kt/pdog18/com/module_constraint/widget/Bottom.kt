package kt.pdog18.com.module_constraint.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.withTranslation
import pdog18.radianutil.angle2Radian
import pdog18.radianutil.getRadian
import pdog18.radianutil.rectifyWithRadianAndRadius
import timber.log.Timber


class Bottom(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private lateinit var center: PointF

    private val paint: Paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
    }
    private val layoutPointF = PointF(0f, 0f)

    init {
        setWillNotDraw(false)

        (0..11).forEach {
            addView(TextView(context).apply {
                setBackgroundColor(Color.BLUE)
                layoutParams = LayoutParams(60, 60)
            })
        }
    }

    private var lastRadian = 0f

    private var rectifyRadian = 0f

    private lateinit var inner: Region
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initCenter()
    }

    private fun initCenter() {
        center = PointF(width / 2f, height * 3f)
    }

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


        val radian = 360f.angle2Radian().toFloat() / childCount

        (0 until childCount).forEach {
            val child = getChildAt(it)
            val radius = height * 2 + child.height * 4

            Timber.d("radius = ${radius}")
            Timber.d("height = ${height}")

            layoutPointF.rectifyWithRadianAndRadius(radius.toFloat(), radian * it + rectifyRadian)
            val dx = (layoutPointF.x - child.width / 2 + center.x).toInt()
            val dy = (layoutPointF.y - child.height / 2 + center.y).toInt()

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

        val radius = height * 2 + 180

        canvas.withTranslation(center.x, center.y) {
            canvas.drawCircle(0f, 0f, radius.toFloat(), paint)
        }
    }
}
