package pdog18.com.module_parabola

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class ParabolaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parabola)
        findViewById<View>(R.id.pv).setOnClickListener { v ->
            (v as ParabolaView).start()
            println("start")
        }
    }
}
