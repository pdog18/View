package kt.pdog18.com.module_timeline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import kt.pdog18.com.core.ext.dp

const val HEAD = 0        //最上方 ，只有一个
const val ACTIVE = 1      //当前状态 ，只有一个
const val BODY = 2        //过往状态，多个
const val FOOTER = 3      //底部， 只有一个

class TimelineState(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {


    private var state: Int = 1

    private val paint: Paint = Paint(ANTI_ALIAS_FLAG).apply {
        strokeWidth = 1.dp
        color = Color.LTGRAY
    }


    fun changeState(state: Int) {
        this.state = state
        when (state) {
            HEAD -> {
                setImageResource(R.mipmap.head)
            }
            ACTIVE -> {
                setImageResource(R.mipmap.active)
            }
            BODY,FOOTER -> {
                setImageResource(R.mipmap.body)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        when (state) {
        //只有一半
            FOOTER -> {
                canvas?.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(),(height / 2).toFloat() , paint)
            }
        //铺满高度
            else -> {
                canvas?.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(), height.toFloat(), paint)
            }
        }
        super.onDraw(canvas)
    }

}