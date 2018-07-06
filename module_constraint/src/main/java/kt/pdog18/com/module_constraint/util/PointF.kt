package kt.pdog18.com.module_constraint.util

import android.graphics.PointF

operator fun PointF.minusAssign(p: PointF) {
    this.apply {
        offset(-p.x, -p.y)
    }
}


/**
 * 一个点[this]相对于[0,0] 的弧度
 * @return 弧度
 */
fun PointF.toRadian() = getRadian(x, y)


/**
 * [this] 触摸点 [center] 圆心
 * @return 一个点[this]]相对于圆心{[center.x],[center.y]}的弧度
 */
fun PointF.toRadian(center: PointF) = this.apply {
    this -= center
}.toRadian()
