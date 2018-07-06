package kt.pdog18.com.module_constraint.util

import android.graphics.PointF
import android.view.MotionEvent


fun getRadian(event: MotionEvent, x: Float, y: Float): Float = getRadian(event.x, event.y, x, y)

fun getRadian(event: MotionEvent, pointF: PointF): Float = getRadian(event.x, event.y, pointF.x, pointF.y)

/**
 * [touchPoint] 触摸点
 * [center] 圆心
 * @return   符合 android 坐标系的角度 ,
 */
fun getRadian(touchPoint: PointF, center: PointF): Float = getRadian(touchPoint.x, touchPoint.y, center.x, center.y)

fun getRadian(touchX: Float, touchY: Float, centerX: Float = 0f, centerY: Float = 0f): Float {
    val dx = touchX - centerX
    val dy = touchY - centerY
    val direction = getVerticalDirection(dy)

    return direction.getRadian(dx, dy)
}