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

            MotionEvent.ACTION_MOVE -> {
                val currentRadian = getRadian(event, center)
                rectifyRadian += (currentRadian - lastRadian)
                lastRadian = currentRadian
                requestLayout()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val currentRadian = getRadian(event, center)

                rectifyRadian += (currentRadian - lastRadian)

                val cellRadian = (360f / childCount).angle2Radian()

                val dr = Math.abs(rectifyRadian) % cellRadian

                var position = (rectifyRadian / cellRadian).toInt()

                val antiDr = cellRadian - dr

                if (antiDr > dr) {
                    if (rectifyRadian < 0) {
                        rectifyRadian += dr.toFloat()
                    } else {
                        rectifyRadian -= dr.toFloat()
                    }
                } else {
                    if (rectifyRadian < 0) {
                        position--
                        rectifyRadian -= antiDr.toFloat()
                    } else {
                        position++
                        rectifyRadian += antiDr.toFloat()
                    }
                }

                lastRadian = rectifyRadian
                requestLayout()

                callback(position)

                Timber.d("position = ${position}")
            }
        }

        return true
    }

    var callback: (Int) -> Unit = {}


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        layoutChildren(rectifyRadian)
    }

    private fun layoutChildren(rectifyRadian: Float) {
        (0 until childCount).forEach {
            val child = getChildAt(it)
            val radius = (center.y - height /* height * 2 */) + child.height * 4

            val radian = 360f.angle2Radian().toFloat() / childCount


            layoutPointF.rectifyWithRadianAndRadius(radius.toFloat(), radian * it + rectifyRadian)
            val dx = (layoutPointF.x - child.width / 2 + center.x).toInt()
            val dy = (layoutPointF.y - child.height / 2 + center.y).toInt()


            child.layout(child.left + dx, child.top + dy, child.right + dx, child.bottom + dy)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = height * 2.6

        canvas.withTranslation(center.x, center.y) {
            canvas.drawCircle(0f, 0f, radius.toFloat(), paint)
        }
    }
}
