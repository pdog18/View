package kt.pdog18.com.core

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kt.pdog18.com.core.tool.IMMLeaks

abstract class BaseActivity : AppCompatActivity() {
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IMMLeaks.fixFocusedViewLeak(this.application) // 修复 InputMethodManager 引发的内存泄漏
        startCreate()
        initData(intent)
        setContentView(getLayoutId())
        initViews()
        loadData(intent)
        endCreate()
    }

    open protected fun startCreate() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initData(intent: Intent?)

    abstract fun initViews()

    abstract fun loadData(intent: Intent?)

    open protected fun endCreate() {

    }
}
