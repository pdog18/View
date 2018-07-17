package com.pdog18.layoutmanager

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation
import kt.pdog18.com.core.ext.dp


/**
 * 这个指数图需要传入两个数据
 * 1. [baseLineValue] -> 这个值是预计值，Float类型 ，例如 10.8f
 * 2. [realValueArray] -> 这个值是实际值数组，FloatArray类型，例如[10.8f,12f,14f,8f]
 */
class DAChart(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private val whitePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
        color = Color.WHITE
        style = Paint.Style.STROKE
    }

    private val lightBluePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BFD7FF")
        strokeCap = Paint.Cap.ROUND
    }

    private val pathEffect = DashPathEffect(floatArrayOf(5f.dp, 5f.dp), 0f)

    private var baseLineValue = 0f
    private var realValueArray: FloatArray = floatArrayOf()


    private var shader: Shader = LinearGradient(0f, 0f, 360f.dp, 0f,
        Color.parseColor("#7793FD"),
        Color.WHITE, Shader.TileMode.CLAMP)


    private val pathByData = Path()
    private val endPointOfPath = PointF()

    fun setValue(baseValue: Float, realData: FloatArray) {
        this.baseLineValue = baseValue
        this.realValueArray = realData

        if (width != 0) {
            bindData()
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        canvas.withTranslation(paddingStart.toFloat(), height / 2f) {

            //0. 终点 淡蓝色点
            drawBaseLinePoint(this)

            //1. 基准虚线
            drawBaseDashLine(this, lightBluePaint)

            //4. 实际值
            drawRealValuePath(this, pathByData, whitePaint)

            //5. 终点
            drawAtticPoint(this, endPointOfPath)
        }
    }

    private fun drawAtticPoint(canvas: Canvas, point: PointF) {
        lightBluePaint.strokeWidth = 6f.dp
        canvas.drawPoint(0f, 0f, lightBluePaint)

        whitePaint.strokeWidth = 6f.dp
        canvas.drawPoint(point.x, point.y, whitePaint)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bindData()
    }

    private fun bindData() {
        val data = realValueArray
        if (data.isEmpty()) {
            return
        } else {
            setValuePath(baseLineValue, data, pathByData)
        }
    }

    private fun setValuePath(baseLine: Float, array: FloatArray, path: Path) {
        val percentWidth = getPercentWidth(array)
        val percentHeight = getPercentHeight(baseLine, array)


        array.withIndex().map {
            val x = percentWidth * it.index
            val y = percentHeight * (baseLine - it.value)

            if (it.index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }


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

    private fun drawBaseLinePoint(canvas: Canvas) {
        lightBluePaint.strokeWidth = 16f.dp
        val originAlpha = lightBluePaint.alpha
        lightBluePaint.alpha = 100
        canvas.drawPoint(endPointOfPath.x, endPointOfPath.y, lightBluePaint)
        lightBluePaint.alpha = originAlpha
    }

    private fun drawRealValuePath(canvas: Canvas, path: Path, paint: Paint) {
        paint.strokeWidth = 2f.dp
        paint.shader = shader
        canvas.drawPath(path, paint)
        paint.shader = null
    }
}
