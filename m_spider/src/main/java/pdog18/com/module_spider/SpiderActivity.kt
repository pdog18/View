package pdog18.com.module_spider

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SpiderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(1 shl 10, 1 shl 10)
        setContentView(R.layout.activity_spider)

        val viewById = findViewById<SpiderWebView>(R.id.swb)
        viewById.setProgress(0.2f, 0.4f, 0.6f, 0.8f, 1.0f, 0.2f)

    }
}
