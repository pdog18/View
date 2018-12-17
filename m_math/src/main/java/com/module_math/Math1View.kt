package com.module_math

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation
import com.pdog.dimension.dp
import com.pdog.dimension.sp


class Math1View(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val pointPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        strokeWidth = 6f.dp
    }

    private val linePaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        strokeWidth = 3f.dp
    }

    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = 24f.sp
    }

    private val pointC = PointF(0f, 0f)
    private val pointB = PointF(0f, (-100f).dp)
    private val pointA = PointF((-100f).dp, 0f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withTranslation(x = width / 2f, y = height / 2f) {

            drawPoint(pointC, pointPaint)
            canvas.drawTextOnPoint("C", textPaint, pointC)

            drawPoint(pointB, pointPaint)
            canvas.drawTextOnPoint("B", textPaint, pointB)

            drawPoint(pointA, pointPaint)
            canvas.drawTextOnPoint("A", textPaint, pointA)


            drawLineBetween(linePaint, pointB, pointC)
            drawLineBetween(linePaint, pointA, pointC)
            drawLineBetween(linePaint, pointA, pointB)
        }

        /**
         * 直角边
         */
        val tan = Math.atan2(100.0, 100.0)
        val angleA = 180 * tan / Math.PI
    }
}
