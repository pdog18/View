package pdog18.com.hencoder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import pdog18.com.core.ext.dp


class ScaleImageView(context: Context, attr: AttributeSet) : View(context, attr) {
    private val p = Paint()
    private val bitmap: Bitmap = getBitmap(resources, R.mipmap.xx, 260.dp)

    private val offsetX by lazy { (width - bitmap.width) / 2f }
    private val offsetY by lazy { (height - bitmap.height) / 2f }

    private val overScaleFraction = 1.5f

    private val largeScale by lazy { getScale(true) * overScaleFraction }
    private val smallScale by lazy { getScale(false) }

    private var isLarge = false

    private val scaleAnimation = ObjectAnimator.ofFloat(this, "scaleFraction", 1f)

    @Suppress("unused")
    private var scaleFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var gestureDetector = GestureDetector(context, G()).apply {
        setOnDoubleTapListener(object : G() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                if (!isLarge) scaleAnimation.start() else scaleAnimation.reverse()

                isLarge = !isLarge
                return false
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent) = gestureDetector.onTouchEvent(event)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.withTranslation(offsetX, offsetY) {
            val scale = smallScale + (largeScale - smallScale) * scaleFraction

            canvas.withScale(scale, scale, bitmap.width / 2f, bitmap.height / 2f) {
                canvas.drawBitmap(bitmap, 0f, 0f, p)
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

    open class G : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean = true
    }
}