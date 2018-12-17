package com.module_seekbar

import android.content.Context
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.doOnNextLayout
import timber.log.Timber


class MySeekBar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var fab: FloatingActionButton

    var adapter: SeekBarAdapter<*>? = null

    init {
        val content = inflate(context, R.layout.my_seek_bar, this)
        fab = findViewById(R.id.fab)!!

        doOnNextLayout {
            val range = content.width - fab.width
            Timber.d("fabParentWidth = ${range}")
            adapter?.bindView(this, 0)

            fab.horizontal(range) { _, currentLeft, mask ->
                if (mask == MotionEvent.ACTION_UP) {

                    val cellWidth = (range) * 1.0f / (getCellCount() - 1)

                    val position = getPopPosition(currentLeft, cellWidth)

                    adapter?.bindView(this@MySeekBar, position)

                    val marginLayoutParams = fab.layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.leftMargin = (cellWidth * position).toInt()
                    fab.layoutParams = marginLayoutParams

                    onReleased(position)

                    Timber.d("cellWidth = ${cellWidth}")
                    Timber.d("marginLayoutParams.leftMargin = ${marginLayoutParams.leftMargin}")
                } else if (mask == MotionEvent.ACTION_DOWN) {
                    //已经操作过，将边框变成蓝色
                    (findViewById<View>(R.id.rect)).background = resources.getDrawable(R.drawable.seek_bar_background_blue)
                }
            }
        }
    }

    private fun getPopPosition(dx: Int, cellWidth: Float): Int {
        return Math.round(dx / cellWidth)
    }

    var onReleased: (index: Int) -> Unit = {}

    fun setOnReleasedListener(action: (index: Int) -> Unit) {
        checkNotNull(action)
        onReleased = action
    }

    private fun getCellCount() = adapter?.getItemCount() ?: 0

}
