package pdog18.radianutil

import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin

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

/**
 * 半径[radius] , 弧度[radian] 和 圆心 [centerX],[centerY] 确定一个点的坐标
 * @return  [this] 这个对象的坐标就是圆上的一个点
 */
fun PointF.rectifyWithRadianAndRadius(
    radius: Float,
    radian: Float,
    centerX: Float = 0f,
    centerY: Float = 0f) {

    val x1 = centerX + radius * cos(radian)
    val y1 = centerY + radius * sin(radian)

    this.set(x1, y1)
}

fun PointF.rectifyWithRadianAndRadius(
    radius: Float,
    radian: Float,
    center: PointF) {

    val x1 = center.x + radius * cos(radian)
    val y1 = center.y + radius * sin(radian)

    this.set(x1, y1)
}