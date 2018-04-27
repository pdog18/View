package kt.pdog18.com.module_scratch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.graphics.withSave

/**
 * 刮刮奖效果
 */
class ScratchView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    //被刮开的区域
    private val clipPath: Path = Path()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // 记录移动的点；
                clipPath.addCircle(event.x, event.y, 50f, Path.Direction.CW)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.withSave {
            //绘制可以刮开的区域
            drawColor(Color.GRAY)

            //裁剪出可以显示图片的区域
            clipPath(clipPath)
            //绘制原本的图片
            super.onDraw(canvas)
        }
    }
}