package pdog18.radianutil


/**
 * 弧度 转换成 角度
 * 弧度 = 角度 * 180 / Math.PI
 * @return angle = [this] * 180 /[Math.PI]
 */
fun Float.radian2Angle() = this * 180 / Math.PI


/**
 * 角度 转换成 弧度
 * 角度 = 弧度 * Math.PI / 180
 * @return radian = [this] *[Math.PI] /180
 */
fun Float.angle2Radian() = (this * Math.PI / 180)