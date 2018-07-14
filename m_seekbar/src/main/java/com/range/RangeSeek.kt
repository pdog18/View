package com.range

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withTranslation
import kt.pdog18.com.core.ext.dp
import timber.log.Timber


class RangeSeek(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var STATE = NO_DOT

    private companion object {
        const val RIGHT_DOT = 1
        const val LEFT_DOT = 0
        const val NO_DOT = -1
    }

    private val leftDot: Dot
    private val rightDot: Dot
    private val backPaint: Paint
    private val forePaint: Paint

    private val radius = 11f.dp

    private var start = 0f
    private var maxWidth = 0f

    private var min = 0
    private var max = 999

    init {
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#3160FF")
        }

        backPaint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#EEF2F5")
            strokeWidth = 8f.dp
            strokeCap = Paint.Cap.ROUND
        }

        forePaint = Paint(backPaint).apply {
            color = Color.parseColor("#BECEFF")
        }

        leftDot = Dot(paint, radius)
        rightDot = Dot(paint, radius)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //把右边的点，移到最右侧
        maxWidth = w.toFloat() - (2 * radius)

        rightDot.translate(maxWidth, maxWidth)
    }

    private var lastX = -1f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

                val x = (event.x - radius)
                val y = (event.y - height / 2)

                lastX = event.x

                STATE = when {
                    leftDot.contains(x, y) -> LEFT_DOT
                    rightDot.contains(x, y) -> RIGHT_DOT
                    else -> NO_DOT
                }

                if (STATE == NO_DOT) {
                    return false
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val currX = event.x
                val dx = currX - lastX

                when (STATE) {
                    LEFT_DOT -> {
                        if (leftDot.x + dx + radius * 2 <= rightDot.x) {
                            Timber.d("dx = ${dx}")
                            leftDot.translate(dx, maxWidth)
                        }
                    }
                    RIGHT_DOT -> {
                        if (rightDot.x + dx >= leftDot.x + radius * 2) {
                            Timber.d("dx = ${dx}")
                            rightDot.translate(dx, maxWidth)
                        }
                    }
                }

                invalidate()
                lastX = currX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

            }
        }

        val left = (leftDot.x / maxWidth) * (max - min) + min
        val right = (rightDot.x / maxWidth) * (max - min) + min
        callback(this, left, right)
        return true
    }

    private var callback: ((RangeSeek, Float, Float) -> Unit) = { rangeSeek, left, right -> }

    fun setOnRangeChangedListener(li: (RangeSeek, Float, Float) -> Unit) {
        callback = li
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.withTranslation(x = leftDot.radius, y = height * 1.0f / 2) {

            //1. draw 灰色背景
            drawLine(start, 0f, maxWidth, 0f, backPaint)

            //2. draw 蓝色选中区域
            drawLine(leftDot.x, 0f, rightDot.x, 0f, forePaint)

            //3. draw 蓝色点
            leftDot.draw(this)
            rightDot.draw(this)

        }
    }

    private class Dot(private val paint: Paint, val radius: Float, x: Float = 0.0f, y: Float = 0.0f) : PointF(x, y) {

        private val rect: RectF

        init {
            //根据 Point radius 创建 rect
            val r = (radius * 1.5f)
            rect = RectF(x - r, y - r, x + r, y + r)
        }


        fun draw(canvas: Canvas) {
            canvas.drawCircle(x, y, radius, paint)
//            canvas.drawRect(rect, paint)
        }

        fun contains(x: Float, y: Float): Boolean {
            return rect.contains(x, y)
        }

        fun translate(dx: Float, maxWidth: Float) {
            var offset = dx

            if (dx + x < 0) {
                offset = -x
            } else if (dx + x > maxWidth) {
                offset = maxWidth - x
            }

            x += offset

            rect.offset(offset, 0f)
        }
    }
}
