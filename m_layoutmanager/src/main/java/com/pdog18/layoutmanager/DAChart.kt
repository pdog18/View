package com.pdog18.layoutmanager

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.withRotation
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import kt.pdog18.com.core.ext.dp
import timber.log.Timber


/**
 * 这个指数图需要传入两个数据
 * 1. [baseLineValue] -> 这个值是预计值，Float类型 ，例如 10.8f
 * 2. [realValueArray] -> 这个值是实际值数组，FloatArray类型，例如[10.8f,12f,14f,8f]
 */
class DAChart(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private val rocketAnim = ObjectAnimator.ofFloat(this, "launchRocket", 1f)
    private val destinationAnim = ObjectAnimator.ofFloat(this, "destination", 1f)


    private val pathMeasure = PathMeasure()

    private val interpolator = OvershootInterpolator(5f)

    companion object {
        const val ROCKET_VISIBLE = 0
        const val ROCKET_GONE = 1
        const val ROCKET_SCALEING = 2

    }

    private var rocketVisibility = ROCKET_VISIBLE


    private val rocket = Path().apply {
        lineTo(0f, (-2f).dp)
        lineTo(4f.dp, 0f)
        lineTo(0f, 2f.dp)
        close()
    }


    private val pathMeasurePoint = FloatArray(2)
    private val tan = FloatArray(2)

    private var rocketDegrees = 0f
    private val rocketPoint = PointF()
    private val rocketTraces = Path()


    private val destinationPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
    }

    @Suppress("unused")
    private var destination: Float = 0f
        set(value) {
            field = value
            invalidate()
        }


    @Suppress("unused")
    private var launchRocket: Float = 0f
        set(value) {
            val pathLength = pathMeasure.length
            pathMeasure.getPosTan(value * pathLength, pathMeasurePoint, tan)

            rocketDegrees = Math.toDegrees(Math.atan2(tan[1].toDouble(), tan[0].toDouble())).toFloat()
            rocketPoint.set(pathMeasurePoint[0], pathMeasurePoint[1])

            rocketTraces.reset()
            pathMeasure.getSegment(0f, value * pathLength, rocketTraces, true)

            invalidate()
        }

    fun doAnim() {
        rocketAnim.apply {
            duration = 1800
            interpolator = AccelerateInterpolator()
            doOnStart {
                rocketVisibility = ROCKET_VISIBLE
            }
            doOnEnd {
                rocketVisibility = ROCKET_SCALEING
                playDestinationAnim()
            }
        }.start()
    }

    private fun playDestinationAnim() {
        destinationAnim
            .apply {
                interpolator = LinearInterpolator()
            }
            .setDuration(1000L)
            .start()
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

    private val baselinePathEffect = DashPathEffect(floatArrayOf(5f.dp, 5f.dp), 0f)

    private var baseLineValue = 0f
    private var realValueArray: FloatArray = floatArrayOf()


    private var shader: Shader = LinearGradient(0f, 0f, 360f.dp, 0f,
        Color.parseColor("#7793FD"),
        Color.WHITE, Shader.TileMode.CLAMP)


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
            drawDestinationPoint(this, rocketPoint, destinationPaint)

            //3. 实际值 的折线 （伴随动画）
            drawRocketTraces(this, rocketTraces, whitePaint)

            //4. 动画过程中的实际值折线前方的箭头
            drawRocket(this, whitePaint)

            //5. 起点
            drawOriginPoint(this, lightBluePaint)
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
            setValuePath(baseLineValue, data, Path())
        }
    }

    private fun setValuePath(baseLine: Float, array: FloatArray, path: Path) {
        val cellWidth = getCellWidth(array)
        val cellHeight = getCellHeight(baseLine, array)

        array.withIndex().map {
            val x = cellWidth * it.index
            val y = cellHeight * (baseLine - it.value)

            if (it.index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        pathMeasure.setPath(path, false)
    }

    private fun getCellWidth(array: FloatArray): Float {
        val lineLength = width.toFloat() - paddingStart - paddingEnd /* lineLengthPercentOfWidth */

        return if (array.size == 1) {
            lineLength
        } else {
            lineLength / (array.size - 1)
        }
    }

    private fun getCellHeight(baseLine: Float, array: FloatArray): Float {
        val maxDValue = Math.abs(array.max()!! - baseLine)
        val minDValue = Math.abs(array.min()!! - baseLine)

        //  上/下相对于基准线的最大值
        val dValue = Math.max(maxDValue, minDValue)

        return when (dValue) {
            0f -> 0f
            else -> (height / 4f) / dValue  //  每一份的高度
        }
    }


    private fun drawBaseDashLine(canvas: Canvas, paint: Paint) {
        val lineLength = width.toFloat() - paddingStart - paddingEnd /* lineLengthPercentOfWidth */

        paint.strokeWidth = 2f.dp
        paint.style = Paint.Style.STROKE
        paint.pathEffect = baselinePathEffect
        canvas.drawLine(0f, 0f, lineLength, 0f, paint)
        paint.pathEffect = null
    }

    private fun drawDestinationPoint(canvas: Canvas, point: PointF, paint: Paint) {
        if (rocketVisibility == ROCKET_VISIBLE) {
            return
        }


        paint.color = Color.parseColor("#BFD7FF")
        paint.alpha = (255 * (1 - destination)).toInt()
        paint.strokeWidth = 32f.dp * destination
        canvas.drawPoint(point.x, point.y, paint)


        paint.color = Color.parseColor("#BFD7FF")
        paint.alpha = 153 /* 40% alpha */
        paint.strokeWidth = 16f.dp * destination
        canvas.drawPoint(point.x, point.y, paint)


        Timber.d("AAA   paint.strokeWidth = ${paint.strokeWidth}")

        paint.alpha = 255 /* 0% alpha*/
        paint.color = Color.WHITE
        paint.strokeWidth = 3f.dp + 3f.dp * interpolator.getInterpolation(destination)
        Timber.d("BBB   paint.strokeWidth = ${paint.strokeWidth}")
        canvas.drawPoint(point.x, point.y, paint)
    }


    private fun drawRocketTraces(canvas: Canvas, path: Path, paint: Paint) {
        paint.strokeWidth = 2f.dp
        paint.shader = shader
        canvas.drawPath(path, paint)
        paint.shader = null
    }


    private fun drawRocket(canvas: Canvas, paint: Paint) {
        when (rocketVisibility) {
            ROCKET_VISIBLE -> {
                canvas.withTranslation(rocketPoint.x, rocketPoint.y) {
                    canvas.withRotation(rocketDegrees) {
                        canvas.drawPath(rocket, paint)
                    }
                }
            }
            ROCKET_SCALEING -> {
                val scaleFactor = 1 - destination
                canvas.withTranslation(rocketPoint.x, rocketPoint.y) {
                    canvas.withScale(scaleFactor, scaleFactor, 2f.dp, 0f) {
                        canvas.drawPath(rocket, paint)
                    }
                }
            }
        }

    }

    private fun drawOriginPoint(canvas: Canvas, paint: Paint) {
        paint.strokeWidth = 6f.dp
        canvas.drawPoint(0f, 0f, paint)
    }
}
