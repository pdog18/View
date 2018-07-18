package com.pdog18.layoutmanager

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import kt.pdog18.com.core.ext.dp
import timber.log.Timber


/**
 * 这个指数图需要传入两个数据
 * 1. [baseLineValue] -> 这个值是预计值，Float类型 ，例如 10.8f
 * 2. [realValueArray] -> 这个值是实际值数组，FloatArray类型，例如[10.8f,12f,14f,8f]
 */
class DAChart(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val pathMeasure = PathMeasure()

    private var rocketVisibility = false

    private val animator = ObjectAnimator.ofFloat(this, "launchRocket", 1f)


    private val rocket = Path().apply {
        lineTo(0f, (-2f).dp)
        lineTo(4f.dp, 0f)
        lineTo(0f, 2f.dp)
        close()
    }


    private val rocketPosition = FloatArray(2)
    private val rocketTan = FloatArray(2)

    private var rocketDegrees = 0f
    private val segmentEndPoint = PointF()
    private val segmentPath = Path()


    private var launchRocket: Float = 0f
        set(value) {
            pathMeasure.getPosTan(value * pathMeasure.length, rocketPosition, rocketTan)
            rocketDegrees = Math.toDegrees(Math.atan2(rocketTan[1].toDouble(), rocketTan[0].toDouble())).toFloat()
            segmentEndPoint.set(rocketPosition[0], rocketPosition[1])

            segmentPath.reset()
            pathMeasure.getSegment(0f, value * pathMeasure.length, segmentPath, true)

            invalidate()
        }

    fun doAnim() {
        animator.apply {
            duration = 1800
            interpolator = AccelerateInterpolator()
            doOnStart {
                rocketVisibility = true
            }
            doOnEnd {
                rocketVisibility = false
            }
        }.start()
    }

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

        if (width != 0) {       //确保已经有宽度了
            bindData()
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        canvas.withTranslation(paddingStart.toFloat(), height / 2f) {

            //1. 基准虚线
            drawBaseDashLine(this, lightBluePaint)

            //2. 终点（1, 实心原点， 2, 带有透明度背景原点， 3,扩散出然后消失的圆）
            drawDestinationPoint(this, segmentEndPoint)

            //3. 实际值 的折线 （伴随动画）
            drawRealValuePath(this, segmentPath, whitePaint)

            //4. 动画过程中的实际值折线前方的箭头
            drawRocket(this, whitePaint)

            //5. 起点
            drawOriginPoint(this, lightBluePaint)
        }
    }

    private fun drawRocket(canvas: Canvas, paint: Paint) {
        if (!rocketVisibility) {
            return
        }
        canvas.withTranslation(segmentEndPoint.x, segmentEndPoint.y) {
            canvas.withRotation(rocketDegrees) {
                canvas.drawPath(rocket, paint)
            }
        }
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

        pathMeasure.setPath(path, false)
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

        return when (dValue) {
            0f -> 0f
            else -> {
                val lineHeight = height / 4f
                //每一份的高度
                lineHeight / dValue
            }
        }
    }


    private fun drawBaseDashLine(canvas: Canvas, paint: Paint) {
        val lineLength = width.toFloat() - paddingStart - paddingEnd /* lineLengthPercentOfWidth */

        paint.strokeWidth = 2f.dp
        paint.style = Paint.Style.STROKE
        paint.pathEffect = pathEffect
        canvas.drawLine(0f, 0f, lineLength, 0f, paint)
        paint.pathEffect = null
    }

    private fun drawDestinationPoint(canvas: Canvas, point: PointF) {
        if (rocketVisibility) {
            return
        }

        lightBluePaint.strokeWidth = 16f.dp
        lightBluePaint.alpha = 153 /* 40% alpha */
        canvas.drawPoint(point.x, point.y, lightBluePaint)
        lightBluePaint.alpha = 255 /* 0% alpha*/

        whitePaint.strokeWidth = 6f.dp
        canvas.drawPoint(point.x, point.y, whitePaint)
    }


    private fun drawRealValuePath(canvas: Canvas, path: Path, paint: Paint) {
        paint.strokeWidth = 2f.dp
        paint.shader = shader
        canvas.drawPath(path, paint)
        paint.shader = null
    }


    private fun drawOriginPoint(canvas: Canvas, paint: Paint) {
        paint.strokeWidth = 6f.dp
        canvas.drawPoint(0f, 0f, paint)
    }
}
