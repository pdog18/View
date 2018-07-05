package kt.pdog18.com.module_constraint.util

import kotlin.math.atan2


enum class Direction {
    Bottom {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 0
    },
    Top {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 360
    };

    abstract fun getAngle(x: Float, y: Float): Double
}

fun getDirection(y: Float): Direction {
    return if (y > 0) {
        Direction.Bottom
    } else {
        Direction.Top
    }
}