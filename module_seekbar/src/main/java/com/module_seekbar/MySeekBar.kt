package com.module_seekbar

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.doOnNextLayout
import timber.log.Timber


class MySeekBar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var fab: FloatingActionButton
    var fabParentWidth = 0f

    var adapter: SeekBarAdapter<*>? = null

    init {
        val fabParent = inflate(context, R.layout.my_seek_bar, this)
        fab = findViewById(R.id.fab)!!

//        val callback = CallBack()
//        viewDragHelper = ViewDragHelper.create(this, callback)
        doOnNextLayout {
            var fabParentWidth = fabParent.width.toFloat()
            adapter?.bindView(this, 0)

            fab.horizontal(fabParentWidth.toInt()) { view, i ->

                Timber.d("i = ${i}")
            }
        }

    }

//    inner class CallBack : ViewDragHelper.Callback() {
//
//        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
//            Timber.d("child == fab,${child == fab}")
//            Timber.d("child = ${child}")
//            return true
//        }
//
//        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
//            if (fab.left + left > 0 && fab.right + left < fabParentWidth) {
//                val params = fab.layoutParams as ViewGroup.MarginLayoutParams
//                params.leftMargin += left
//                fab.layoutParams = params
//            }
//            return 0
//        }
//
//        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
//            if (getCellCount() <= 0) {  //有数据时才会刷新
//                Log.e("Error", "(adapter == null || adapter.getItemCount() == 0)")
//                return
//            }
//
//            val position = offsetLeftAndRightSlider()
//
//            adapter?.bindView(this@MySeekBar, position)
//
//            onReleased(position)
//        }
//    }

    private fun offsetLeftAndRightSlider(dx: Int): Int {
        val cellWidth = (fabParentWidth - fab.width) / (getCellCount() - 1)
        val leave = dx % cellWidth

        val leavePercent = leave / cellWidth

        var position = dx / cellWidth.toInt()

        val layoutParams = fab.layoutParams as ViewGroup.MarginLayoutParams

        if (leavePercent > 0.5f) {  //超过一半 ，应该向右移动
            position++
            layoutParams.leftMargin += (cellWidth - leave).toInt()
        } else {
            layoutParams.leftMargin += -leave.toInt()
        }

        fab.layoutParams = layoutParams

        return position
    }


//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        if (getCellCount() <= 1) {
//            return false
//        }
//
//        viewDragHelper.processTouchEvent(event)
//        return true
//    }

    var onReleased: (index: Int) -> Unit = {}

    fun setOnReleasedListener(action: (index: Int) -> Unit) {
        checkNotNull(action)
        onReleased = action
    }

    private fun getCellCount() = adapter?.getItemCount() ?: 0

}
