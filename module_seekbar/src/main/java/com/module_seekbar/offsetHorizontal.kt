package com.module_seekbar

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import timber.log.Timber

/**
 * Created by pdog on 15/06/2018.
 */
fun View.horizontal(withLimit: Int, action: (View, Int) -> Unit) {
    this.setOnTouchListener { view, event ->
        var lastX = 0f
        val params = view.layoutParams as ViewGroup.MarginLayoutParams

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
            }
            MotionEvent.ACTION_MOVE -> {
                var cX = event.rawX

                var dx = cX - lastX
                var leftX = lastX + dx


                Timber.d("leftX = ${leftX}")

                params.leftMargin = leftX.toInt() - view.width - view.width / 2

                if (params.leftMargin + view.width > withLimit) {
                    Timber.d("nnnnnnn")
                    Timber.d("params.leftMargin = ${params.leftMargin}")
                    Timber.d("view.width = ${view.width}")
                    Timber.d("withLimit = ${withLimit}")
                    kotlin.io.println()

                } else {
                    Timber.d("leftX = ${leftX}")

                    view.layoutParams = params
                }
            }
            else -> {
                action(view, params.leftMargin)
            }
        }
        return@setOnTouchListener true
    }
}