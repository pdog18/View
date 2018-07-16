package kt.pdog18.com.core.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager

val displayMetrics = Resources.getSystem().displayMetrics!!

inline val screenWidth: Int
    get() = displayMetrics.widthPixels

inline val screenHeight: Int
    get() = displayMetrics.heightPixels

inline val scaledDensity: Float
    get() = displayMetrics.scaledDensity


fun getStatusBarHeight(): Int {
    var result = 0
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
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