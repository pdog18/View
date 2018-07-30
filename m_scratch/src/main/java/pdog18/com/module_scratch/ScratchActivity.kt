package pdog18.com.module_scratch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ScratchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scratch)

        val viewById = findViewById<ScratchView>(R.id.sv)
        viewById.setImageResource(R.drawable.k)
    }
}
