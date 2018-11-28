package nest.pdog.com.module_nest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nest.*
import timber.log.Timber

class NestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nest)

        appbar_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Timber.d("appBarLayout.height = ${appBarLayout.height}")
            Timber.d("verticalOffset = ${verticalOffset}")

            if (appBarLayout.height + verticalOffset <= 0) {    //完全隐藏
                tv_title.text = "Title"
            } else {
                tv_title.text = null
            }
        }
    }
}
