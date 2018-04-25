package kt.pdog18.com.core.ext

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Created by Victor on 2017/8/21. (ง •̀_•́)ง
 */

val displayMetrics = app.resources.displayMetrics!!


inline val screenWidth: Int
    get() = app.resources.displayMetrics.widthPixels

inline val screenHeight: Int
    get() = app.resources.displayMetrics.heightPixels

inline val screenDensity: Float
    get() = app.resources.displayMetrics.density

inline val scaledDensity: Float
    get() = app.resources.displayMetrics.scaledDensity


/**
 * 正常编码中一般只会用到 [dp]/[sp] 和 [px] ;
 * 其中[dp]/[sp] 会根据系统分辨率将输入的dp/sp值转换为对应的px
 * 而[px]只是返回自身，目的是表明自己是px值
 */

val Number.dp: Int      // [xxhdpi](360 -> 1080)
    get() = (this.toFloat() * displayMetrics.density + 0.5f).toInt()

val Number.sp: Int      // [xxhdpi](360 -> 1080)
    get() = (this.toFloat() * displayMetrics.scaledDensity + 0.5f).toInt()

val Number.px: Int      // [xxhdpi](360 -> 360)
    get() = this.toInt()

/**
 * 在(可能存在的?)某些特殊情况会需要将px值转换为对应的dp/sp
 * 对应方法[Number.px2dp]/[Number.px2sp]
 */
val Number.px2dp: Int       // [xxhdpi](360 -> 120)
    get() = (this.toFloat() / displayMetrics.density + 0.5f).toInt()

val Number.px2sp: Int       // [xxhdpi](360 -> 120)
    get() = (this.toFloat() / displayMetrics.scaledDensity + 0.5f).toInt()


fun getStatusBarHeight(): Int {
    var result = 0
    val resourceId = app.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = app.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun getVirNavBarHeight(): Int {
    var height: Int
    val wm = (app.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
    val display = wm.defaultDisplay
    val p = Point()
    display.getSize(p)
    val screenHeight = Math.max(p.y, p.x)
    val dm = DisplayMetrics()
    val c: Class<*> = Class.forName("android.view.Display")
    val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
    method.invoke(display, dm)
    //横屏在右|竖屏在底
    height = Math.max(dm.heightPixels, dm.widthPixels) - screenHeight
    //横屏在底|竖屏在底
    if (height == 0) {
        height = Math.min(dm.heightPixels, dm.widthPixels) - Math.min(p.y, p.x)
    }
    return height
}