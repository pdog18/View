package com.pdog18.layoutmanager

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation
import kt.pdog18.com.core.ext.dp

class CView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    //    #7793FD <-- start color

    private val lightBlue = Color.parseColor("#BFD7FF")

    private val whitePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
        color = Color.WHITE
        style = Paint.Style.STROKE
    }

    private val lightBluePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = lightBlue
        strokeCap = Paint.Cap.ROUND
    }

    private val pathEffect = DashPathEffect(floatArrayOf(5f.dp, 5f.dp), 0f)

    private val mockBaseLineValue = 10.8f
    private val mockData = floatArrayOf(10.8f, 9f, 8f, 6f, 7.5f, 8f, 8.5f, 9f, 9.5f, 10f, 10.8f, 11.8f, 12.8f)

    private val pathByData = Path()
    private val endPointOfPath = PointF()

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#526AE5"))

        canvas.withTranslation(paddingStart.toFloat(), height / 2f) {

            //1. 基准虚线
            drawBaseDashLine(this, lightBluePaint)

            //2. 基础原点
            drawBaseLinePoint(this, lightBluePaint)

            //4. 实际值
            drawRealValuePath(this, pathByData, whitePaint)

            //5. 终点
            drawEndPoint(this, endPointOfPath, whitePaint, lightBluePaint)
        }
    }

    private fun drawEndPoint(canvas: Canvas, point: PointF, whitePaint: Paint, lightBluePaint: Paint) {

        whitePaint.strokeWidth = 6f.dp
        canvas.drawPoint(point.x, point.y, whitePaint)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        val data = mockData
        if (data.isEmpty()) {
            return
        } else {
            setValuePath(mockBaseLineValue, data, pathByData)
        }
    }

    private fun setValuePath(baseLine: Float, array: FloatArray, path: Path) {

        val percentWidth = getPercentWidth(array)
        val percentHeight = getPercentHeight(baseLine, array)


        array.withIndex().map {
            val x = percentWidth * it.index
            val y = percentHeight * (baseLine - it.value)
            path.lineTo(x, y)

            if (it.index == array.size - 1) {
                endPointOfPath.set(x, y)
            }
        }
    }

    private fun getPercentWidth(array: FloatArray): Float {
        val lineLength = width.toFloat() - paddingStart - paddingEnd /* lineLengthPercentOfWidth */

        return if (array.size == 1) {
            lineLength
        } else {
            lineLength / (array.size - 1)
        }
    }

    private fun getPercentHeight(baseLine: Float, array: FloatArray): Float {
        val maxDValue = Math.abs(array.max()!! - baseLine)
        val minDValue = Math.abs(array.min()!! - baseLine)

        //上/下相对于基准线的最大值
        val dValue = Math.max(maxDValue, minDValue)
        val lineHeight = height / 4f
        //每一份的高度
        return lineHeight / dValue
    }


    private fun drawBaseDashLine(canvas: Canvas, paint: Paint) {
        val lineLength = width.toFloat() - paddingStart - paddingEnd /* lineLengthPercentOfWidth */

        paint.strokeWidth = 2f.dp
        paint.style = Paint.Style.STROKE
        paint.pathEffect = pathEffect
        canvas.drawLine(0f, 0f, lineLength, 0f, paint)
        paint.pathEffect = null

    }

    private fun drawBaseLinePoint(canvas: Canvas, paint: Paint) {
        paint.strokeWidth = 6f.dp
        canvas.drawPoint(0f, 0f, paint)

        lightBluePaint.strokeWidth = 16f.dp

        val originAlpha = lightBluePaint.alpha
        lightBluePaint.alpha = 100
        canvas.drawPoint(endPointOfPath.x, endPointOfPath.y, lightBluePaint)
        lightBluePaint.alpha = originAlpha
    }

    private fun drawRealValuePath(canvas: Canvas, path: Path, paint: Paint) {
        paint.strokeWidth = 2f.dp
        canvas.drawPath(path, paint)
    }
}
