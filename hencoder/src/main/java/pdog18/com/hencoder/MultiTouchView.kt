package pdog18.com.hencoder

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.pdog.dimension.dp

class MultiTouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val bitmap = getBitmap(resources, R.mipmap.xx, 260.dp)

    var offsetX = 0f
    var offsetY = 0f

    var downX = 0f
    var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x - offsetX
                downY = event.y - offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = event.x - downX
                offsetY = event.y - downY

                invalidate()
            }
        }

        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, null)
    }
}