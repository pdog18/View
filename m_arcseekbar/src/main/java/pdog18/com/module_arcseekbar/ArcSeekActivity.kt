package pdog18.com.module_arcseekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import timber.log.Timber

class ArcSeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_seek)


        val seekbar = findViewById<ArcSeekbar>(R.id.dv)
        seekbar.setOnProgressChangeListener({ percent -> Toast.makeText(this@ArcSeekActivity, "percent = " + percent, Toast.LENGTH_SHORT).show() })

        findViewById<View>(R.id.dv).postDelayed({ seekbar.setCellCount(5) }, 3000)


        Timber.d("seekbar = ${seekbar}")
//        val arcView = findViewById<View>(R.id.arcseekbar2)


    }
}
