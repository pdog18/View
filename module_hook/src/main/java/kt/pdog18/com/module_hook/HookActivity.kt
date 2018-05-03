package kt.pdog18.com.module_hook

import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import timber.log.Timber


class HookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook)

    }

    fun hookContext(){
        // 先获取到当前的ActivityThread对象
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val currentActivityThread = currentActivityThreadMethod.invoke(null)

        // 拿到原始的 mInstrumentation字段
        val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
        mInstrumentationField.isAccessible = true
        val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation

        // 创建代理对象
        val evilInstrumentation = EvilInstrumentation(mInstrumentation)

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation)


        var b = baseContext === this

        Timber.d("b = ${b}")
        var intent = Intent(baseContext, BActivity::class.java)
        startActivity(intent)
    }


    fun hookActivity(){
        // 先获取到当前的ActivityThread对象
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val currentActivityThread = currentActivityThreadMethod.invoke(null)

        // 拿到原始的 mInstrumentation字段
        val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
        mInstrumentationField.isAccessible = true
        val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation

        // 创建代理对象
        val evilInstrumentation = EvilInstrumentation(mInstrumentation)

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation)


        var b = baseContext === this

        Timber.d("b = ${b}")
        var intent = Intent(baseContext, BActivity::class.java)
        startActivity(intent)
    }
}
