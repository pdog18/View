package pdog18.com.hencoder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import com.pdog.dimension.dp


class ScaleImageView(context: Context, attr: AttributeSet) : View(context, attr), Runnable {
    @SuppressLint("NewApi")
    override fun run() {
        if (!scroller.computeScrollOffset()) {
            return
        }
        offsetX = scroller.currX.toFloat()
        offsetY = scroller.currY.toFloat()

        invalidate()
        postOnAnimation(this)
    }

    private val bitmap: Bitmap = getBitmap(resources, R.mipmap.xx, 260.dp)

    private val centerPoint by lazy {
        PointF((width - bitmap.width) / 2f,
            (height - bitmap.height) / 2f)
    }

    private val scroller = OverScroller(context)

    private var offsetX = 0f
    private var offsetY = 0f

    private val overScaleFraction = 1.5f

    private val largeScale by lazy { getScale(true) * overScaleFraction }
    private val smallScale by lazy { getScale(false) }

    private var isLarge = false

    private val scaleAnimation = ObjectAnimator.ofFloat(this, "scaleFraction", 1f)
        .apply {
            doOnEnd {
                if (!isLarge) {
                    offsetX = 0f
                    offsetY = 0f
                }
            }
        }

    @Suppress("unused")
    private var scaleFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?) = true
        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (!isLarge) {
                return true
            }

            val widthLimit = (bitmap.width * largeScale - width) / 2
            val heightLimit = (bitmap.height * largeScale - height) / 2

            offsetX -= distanceX
            offsetY -= distanceY

            // 不能超过边界
            offsetX = Math.min(Math.max(-widthLimit, offsetX), widthLimit)
            offsetY = Math.min(Math.max(-heightLimit, offsetY), heightLimit)

            invalidate()
            return false
        }
        @SuppressLint("NewApi")
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val xLimit = (bitmap.width * largeScale - width) / 2
            val yLimit = (bitmap.height * largeScale - height) / 2

            scroller.fling(
                offsetX.toInt(), offsetY.toInt(),
                velocityX.toInt(), velocityY.toInt(),
                -xLimit.toInt(), xLimit.toInt(),
                -yLimit.toInt(), yLimit.toInt())

            postOnAnimation(this@ScaleImageView)
            return false
        }
    }).apply {
        setOnDoubleTapListener(object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                isLarge = !isLarge

                if (isLarge) {
                    offsetX = width / 2 - e.x
                    offsetY = height / 2 - e.y
                    scaleAnimation.start()
                } else {
                    scaleAnimation.reverse()
                }

                return false
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent) = gestureDetector.onTouchEvent(event)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.withTranslation(offsetX * scaleFraction, offsetY * scaleFraction) {
            canvas.withTranslation(centerPoint.x, centerPoint.y) {
                val scale = smallScale + (largeScale - smallScale) * scaleFraction

                canvas.withScale(scale, scale, bitmap.width / 2f, bitmap.height / 2f) {
                    canvas.drawBitmap(bitmap, 0f, 0f, null)
                }
            }
        }
    }

    /**
     *  1. 算出图片和View的宽高比
     *  2. 如果 宽度比 大于 高度比 ，那么说明 图片比较(胖)
     *  3. 那么 [smallScale] 就是 [View.width / bitmap.width ] 也就是 [1f / widthScale]
     *  4. 反正则相反
     *  5. 然后[large] 参数返回对应的[smallScale] 或者 [largeScale]
     */
    private fun getScale(large: Boolean): Float {
        val widthScale = bitmap.width.toFloat() / width
        val heightScale = bitmap.height.toFloat() / height

        val smallScale = if (widthScale > heightScale) {
            widthScale
        } else {
            heightScale
        }

        val largeScale = if (widthScale > heightScale) {
            heightScale
        } else {
            widthScale
        }

        return 1f / if (large) {
            largeScale
        } else {
            smallScale
        }
    }
}