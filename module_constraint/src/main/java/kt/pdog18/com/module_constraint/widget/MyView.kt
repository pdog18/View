package kt.pdog18.com.module_constraint.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withTranslation
import kt.pdog18.com.module_constraint.util.getRadian
import kt.pdog18.com.module_constraint.util.radian2Angle
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.sin


class MyView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val center by lazy {
        PointF(width / 2f, height / 2f)
    }

    private val touchPointF = PointF(0f, 0f)
    private val touchPointF2 = PointF(0f, 0f)

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        strokeWidth = 30f
        style = Paint.Style.FILL
    }

    private val paint2 = Paint(paint).apply {
        color = Color.RED
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val radian = getRadian(event, center)

        Timber.d("radian = ${radian} ,angle = ${radian.radian2Angle()}")

        rectifyPointF(touchPointF, width / 3, 0f, 0f, radian)
        touchPointF2.set(event.x, event.y)
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withTranslation(width / 2f, height / 2f) {
            canvas.drawPoint(0f, 0f, paint)
            canvas.drawPoint(touchPointF.x, touchPointF.y, paint)
        }
        canvas.drawPoint(touchPointF2.x, touchPointF2.y, paint2)

    }

    /**
     * 圆心，半径 和弧度 确定一个点的坐标
     */
    private fun rectifyPointF(pointF: PointF, r: Int, cx: Float, cy: Float, radian: Float) {
        val x1 = cx + r * cos(radian)
        val y1 = cy + r * sin(radian)

        pointF.set(x1, y1)
    }
}
