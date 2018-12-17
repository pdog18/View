package pdog18.com.module_bezier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = "/module/bezier")
class BezierActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
    }
}
