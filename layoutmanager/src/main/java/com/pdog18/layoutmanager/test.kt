package com.pdog18.layoutmanager

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

fun test() {
    Canvas().bindPaintDrawPoints(Paint())(
        arrayOf(PointF(1f, 2f),
            PointF(2f, 2f),
            PointF(3f, 2f),
            PointF(4f, 2f))
    )
}

fun Canvas.bindPaintDrawPoints(paint: Paint): (Array<PointF>) -> Unit {
    return {
        drawPoints(paint, it)
    }
}

fun Canvas.drawPoints(paint: Paint, points: Array<PointF>) {
    for (p in points) {
        this.drawPoint(p.x, p.y, paint)
    }
}