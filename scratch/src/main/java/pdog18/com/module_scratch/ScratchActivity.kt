package pdog18.com.module_scratch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ScratchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)

        val viewById = findViewById<ScratchView>(R.id.sv)
        viewById.setImageResource(R.drawable.k)
    }
}
