package kt.pdog18.com.core.tool

import android.os.Handler
import android.os.Looper

private val mainHandler = Handler(Looper.getMainLooper())

fun onMainThread(block: () -> Unit) {
    mainHandler.post(block)
}