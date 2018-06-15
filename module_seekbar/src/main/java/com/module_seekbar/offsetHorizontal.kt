package com.module_seekbar

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

fun View.horizontal(range: Int, action: (View, Int, Int) -> Unit) {

    fun getViewLayoutParams(view: View): ViewGroup.MarginLayoutParams {
        return view.layoutParams as ViewGroup.MarginLayoutParams
    }

    var downX = -1
    var lastX = -1

    this.setOnTouchListener { view, event ->
        var params = getViewLayoutParams(view)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.rawX.toInt()
                lastX = params.leftMargin
                action(view, params.leftMargin, MotionEvent.ACTION_DOWN)
            }
            MotionEvent.ACTION_MOVE -> {

                val currentX = event.rawX

                val dx = currentX - downX

                val leftMargin = lastX + dx

                if (leftMargin > range || leftMargin < 0) {
                    return@setOnTouchListener true
                } else {
                    params.leftMargin = leftMargin.toInt()
                    view.layoutParams = params
                }
                action(view, params.leftMargin, MotionEvent.ACTION_MOVE)
            }
            else -> {
                action(view, params.leftMargin, MotionEvent.ACTION_UP)
            }
        }
        return@setOnTouchListener true
    }
}
