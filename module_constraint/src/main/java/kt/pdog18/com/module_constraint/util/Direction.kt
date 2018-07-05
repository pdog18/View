package kt.pdog18.com.module_constraint.util

import kt.pdog18.com.module_constraint.util.Direction.Companion.BOTTOM_LEFT
import kt.pdog18.com.module_constraint.util.Direction.Companion.BOTTOM_RIGHT
import kt.pdog18.com.module_constraint.util.Direction.Companion.TOP_LEFT
import kt.pdog18.com.module_constraint.util.Direction.Companion.TOP_RIGHT


sealed class Direction {
    class BottomRight : Direction()
    class BottomLeft : Direction()
    class TopRight : Direction()
    class TopLeft : Direction()

    companion object {
        val BOTTOM_RIGHT = Direction.BottomRight()
        val BOTTOM_LEFT = Direction.BottomLeft()
        val TOP_RIGHT = Direction.TopRight()
        val TOP_LEFT = Direction.TopLeft()
    }
}

fun getDirection(x: Float, y: Float): Direction {
    return if (y > 0) {
        if (x > 0) {
            BOTTOM_RIGHT
        } else {
            BOTTOM_LEFT
        }
    } else {
        if (x > 0) {
            TOP_RIGHT
        } else {
            TOP_LEFT
        }
    }
}