package pdog18.com.module_wave

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WaveActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        val viewById = findViewById<WaveView>(R.id.bezier)
        viewById.post(Runnable { viewById.start() })

    }
}
