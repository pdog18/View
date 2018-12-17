package com.pdog18.layoutmanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ProgressView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()

    private var progress = 0f

    fun update(color: Int, progress: Float) {
        this.progress = progress
        this.color = color
        invalidate()
    }

    private var color = Color.BLACK

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        rect.set(0f, 0f, width * progress, height * 1f)
        paint.color = color
        canvas?.drawRect(rect, paint)
    }
}
