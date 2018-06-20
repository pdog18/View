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


    private val leftDot: Dot
    private val rightDot: Dot
    private val backPaint: Paint
    private val forePaint: Paint


    private val radius = 11f.dp
    private var maxWidth: Float = 0f

    private var active: Dot? = null
    private var unactive: Dot? = null

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

        val leftPoint = PointF(0f, 0f)
        val rightPoint = PointF(0f, 0f)

        leftDot = Dot(paint, leftPoint, radius)
        rightDot = Dot(paint, rightPoint, radius)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //把右边的点，移到最右侧
        maxWidth = w.toFloat() - (2 * radius)
        rightDot.translate(maxWidth)
    }

    private var lastX = -1f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionMasked = event.actionMasked

        when (actionMasked) {
            MotionEvent.ACTION_DOWN -> {

                val x = (event.x - radius).toInt()
                val y = (event.y - height / 2).toInt()

                lastX = event.x

                active = when {
                    leftDot.contains(x, y) -> {
                        Timber.d("leftDot ")
                        unactive = rightDot
                        leftDot
                    }
                    rightDot.contains(x, y) -> {
                        Timber.d("rightDot ")
                        unactive = leftDot
                        rightDot
                    }
                    else -> {
                        Timber.d("null ")

                        return false
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                active?.let {
                    val currX = event.x
                    val dx = currX - lastX

                    if (active === leftDot) {//滑动left
                        Timber.d("滑动left = ${active}")
                        Timber.d("it.point.x + dx >= 0 = ${it.point.x + dx >= 0}")
                        Timber.d(" it.point.x <= rightDot.point.x = ${it.point.x <= rightDot.point.x}")
                        if (it.point.x + dx >= 0 && it.point.x + dx + radius * 2 <= rightDot.point.x) {
                            it.translate(dx)
                        }
                    } else if (active === rightDot) {
                        Timber.d("滑动right = ${active}")
                        if (it.point.x + dx >= leftDot.point.x + radius * 2 && it.point.x + dx <= maxWidth) {
                            it.translate(dx)
                        }
                    } else {
                        Timber.d("滑动null = ${active}")
                    }

                    invalidate()
                    lastX = currX
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

            }
        }

        return true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.withTranslation(x = radius, y = height * 1.0f / 2) {

            //1. draw 灰色背景
            drawLine(0f, 0f, maxWidth, 0f, backPaint)


            //2. draw 蓝色选中区域
            drawLine(leftDot.point.x, 0f, rightDot.point.x, 0f, forePaint)

            //3. draw 蓝色点
            leftDot.draw(this)
            rightDot.draw(this)

        }
    }

    class Dot(private val paint: Paint, val point: PointF, val radius: Float) {

        private val rect: Rect

        init {

            //根据 Point radius 创建 rect
            val x = point.x.toInt()
            val y = point.y.toInt()
            val r = (radius * 1.5f).toInt()
            rect = Rect(x - r, y - r, x + r, y + r)
        }

        fun draw(canvas: Canvas) {
            canvas.drawCircle(point.x, point.y, radius, paint)
        }

        fun contains(x: Int, y: Int): Boolean {
            return rect.contains(x, y)
        }

        fun translate(x: Float) {
            point.x += x.toInt()        //rect 和circle 移动不同步
            rect.offset(x.toInt(), 0)
        }
    }
}
