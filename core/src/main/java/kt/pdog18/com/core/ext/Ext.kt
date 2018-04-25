package kt.pdog18.com.core.ext

import android.annotation.SuppressLint
import android.app.Application

@SuppressLint("StaticFieldLeak")
object Ext {
    lateinit var ctx: Application

    fun with(app: Application) {
        this.ctx = app
    }
}