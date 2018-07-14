package com.module_seekbar

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

fun View.horizontal(range: Int, action: (View, Int, Int) -> Unit) {

    var downX = -1
    var lastX = -1

    this.setOnTouchListener { view, event ->
        with(view.layoutParams as ViewGroup.MarginLayoutParams) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.rawX.toInt()
                    lastX = this.leftMargin
                    action(view, this.leftMargin, MotionEvent.ACTION_DOWN)
                }
                MotionEvent.ACTION_MOVE -> {

                    val currentX = event.rawX

                    val dx = currentX - downX

                    val leftMargin = lastX + dx

                    if (leftMargin > range || leftMargin < 0) {
                        return@setOnTouchListener true
                    } else {
                        this.leftMargin = leftMargin.toInt()
                        view.layoutParams = this
                    }
                    action(view, this.leftMargin, MotionEvent.ACTION_MOVE)
                }
                else -> {
                    action(view, this.leftMargin, MotionEvent.ACTION_UP)
                }
            }
        }
        return@setOnTouchListener true
    }
}
