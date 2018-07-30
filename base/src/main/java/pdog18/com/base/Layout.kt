package pdog18.com.base


import android.support.annotation.LayoutRes

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Layout(
        @LayoutRes val layoutId: Int)
