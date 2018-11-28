package pdog18.com.module_patheffect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PathEffectActivity : AppCompatActivity() {

    private var pathEffectView: PathEffectView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_effect)

        pathEffectView = findViewById<PathEffectView>(R.id.pev)
        pathEffectView!!.post(Runnable { pathEffectView!!.start() })

    }
}
