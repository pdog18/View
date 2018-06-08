package com.module_math

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import timber.log.Timber


inline fun Canvas.drawTextOnPoint(
    text: String,
    paint: Paint,
    p: PointF
) {
    drawText(text, p.x, p.y, paint)
}

inline fun Canvas.drawPoint(
    point: PointF,
    paint: Paint
) {

    drawPoint(point.x, point.y, paint)
}

inline fun Canvas.drawLineBetween(
    paint: Paint,
    //vararg.size >= 2
    vararg points: PointF
) {
    points.indices
        .filter { it < points.size - 1 /* times = size - 1 */ }
        .forEach {
            Timber.d("it = ${it}")
            val p1 = points[it]
            val p2 = points[it + 1]
            drawLine(p1.x, p1.y, p2.x, p2.y, paint)
        }
}

inline fun Canvas.withTranslation2Point(
    p: PointF,
    block: Canvas.() -> Unit
) {
    val checkpoint = save()
    translate(p.x, p.y)
    try {
        block()
    } finally {
        restoreToCount(checkpoint)
    }
}