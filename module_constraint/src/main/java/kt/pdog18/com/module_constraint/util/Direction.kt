package kt.pdog18.com.module_constraint.util

import kotlin.math.atan2


open class Top : Direction() {
    override fun getRadian(x: Float, y: Float) = (Math.PI * 2 + atan2(y, x)).toFloat()
}

open class Bottom : Direction() {
    override fun getRadian(x: Float, y: Float) = atan2(y, x)
}

class TopRight : Top()
class TopLeft : Top()
class BottomLeft : Bottom()
class BottomRight : Bottom()

sealed class Direction {
    abstract fun getRadian(x: Float, y: Float): Float
    fun getAngle(x: Float, y: Float) = getRadian(x, y).radian2Angle()
}

val TOP = Top()
val BOTTOM = Bottom()
val TOP_LEFT = TopLeft()
val TOP_RIGHT = TopRight()
val BOTTOM_RIGHT = BottomRight()
val BOTTOM_LEFT = BottomLeft()



fun getDirection(x: Float, y: Float) = when {
    (y > 0) -> when {
        x > 0 -> BOTTOM_RIGHT
        else -> BOTTOM_LEFT
    }
    else -> when {
        x > 0 -> TOP_RIGHT
        else -> TOP_LEFT
    }
}
/**
 * getVerticalDirection
 * 根据[y] 坐标来返回一个[Direction] ，（传入的值不可能为0）
 */
fun getVerticalDirection(y: Float) = when {
    y > 0 -> BOTTOM
    else -> TOP
}

//todo  getHorizontalDirection
//fun getHorizontalDirection(x: Float) = when {
//    x > 0 -> RIGHT
//    else -> LEFT
//}