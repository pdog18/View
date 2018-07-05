package kt.pdog18.com.module_constraint.util

import android.graphics.PointF

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

/**
 * [touch] 触摸点
 * [center] 圆心
 * return [Double] 符合 android 坐标系的角度 ,
 */
fun computeAngle(touch: PointF, center: PointF): Double = computeAngle(touch.x, touch.y, center.x, center.y)

fun computeAngle(touchX: Float, touchY: Float, centerX: Float, centerY: Float): Double {
    val a = touchX - centerX
    val b = touchY - centerY

    if (a == 0f) { //为了提高精度
        //a == 0f 269.99999734373984
        return horizontal(b)
    }


    if (b == 0f) {
        return vertical(a)
    }

    return getAngle(a, b)
}

private fun getAngle(a: Float, b: Float): Double {
    val d = getDirection(b)

    return d.getAngle(a, b)
}
