package kt.pdog18.com.module_constraint.util

import kotlin.math.atan2

/**
 * 描述方向
 */
enum class Direction {
    Bottom {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 0
    },
    Top {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 360
    };

    abstract fun getAngle(x: Float, y: Float): Double
}

/**
 * 根据[y] 坐标来返回一个[Direction] ，（传入的值不可能为0）
 */
fun getDirection(y: Float): Direction {
    return if (y > 0) {
        Direction.Bottom
    } else {
        Direction.Top
    }
}