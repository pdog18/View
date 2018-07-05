package kt.pdog18.com.module_constraint.util

import kotlin.math.atan2


enum class Direction {
    BottomRight {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 0
    },
    BottomLeft {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 0
    },
    TopRight {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 360
    },
    TopLeft {
        override fun getAngle(x: Float, y: Float) = atan2(y, x) * 180 / Math.PI + 360
    };

    abstract fun getAngle(x: Float, y: Float): Double
}

fun getDirection(x: Float, y: Float): Direction {
    return if (y > 0) {
        if (x > 0) {
            Direction.BottomRight
        } else {
            Direction.BottomLeft
        }
    } else {
        if (x > 0) {
            Direction.TopRight
        } else {
            Direction.TopLeft
        }
    }
}