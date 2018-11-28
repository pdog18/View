package pdog18.com.core

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
