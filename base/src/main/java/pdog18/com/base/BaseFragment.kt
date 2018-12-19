package pdog18.com.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : androidx.fragment.app.Fragment() {

    protected open fun getLayoutId(): Int = getClassAnnotation<Layout>().layoutId


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    inline fun <reified T : Annotation> Any.getClassAnnotation(): T {
        return this::class.java.getAnnotation(T::class.java)!!
    }
}
