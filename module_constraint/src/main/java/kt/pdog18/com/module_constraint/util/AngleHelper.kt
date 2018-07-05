package kt.pdog18.com.module_constraint.util

import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.atan2

private fun horizontal(b: Float): Double {
    return if (b > 0) {
        0.0
    } else {
        180.0
    }
}

private fun vertical(a: Float): Double {
    return if (a > 0) {
        90.0
    } else {
        270.0
    }
}

fun computeAngle(p: PointF, center: PointF): Double = computeAngle(p.x, p.y, center.x, center.y)

fun computeAngle(touchX: Float, touchY: Float, centerX: Float, centerY: Float): Double {
    val a = touchX - centerX
    val b = touchY - centerY

    if (a == 0f) {
        return horizontal(b)
    }


    if (b == 0f) {
        return vertical(a)
    }

    val d = getDirection(a, b)

    val x = abs(a)
    val y = abs(b)
    return when (d) {
        is Direction.BottomRight -> {
            atan2(y, x) * 180 / Math.PI + 0
        }
        is Direction.BottomLeft -> {
            atan2(x, y) * 180 / Math.PI + 90
        }
        is Direction.TopLeft -> {
            atan2(y, x) * 180 / Math.PI + 180
        }
        is Direction.TopRight -> {
            atan2(x, y) * 180 / Math.PI + 270
        }
    }
}