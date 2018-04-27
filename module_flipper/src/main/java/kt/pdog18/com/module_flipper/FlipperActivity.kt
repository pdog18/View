package kt.pdog18.com.module_flipper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.*

class FlipperActivity : AppCompatActivity() {

    /**
     * <set xmlns:android="http://schemas.android.com/apk/res/android">
     * <translate android:fromYDelta="100%p" android:toYDelta="0" android:duration="300"></translate>
     * <alpha android:fromAlpha="0.0" android:toAlpha="1.0" android:duration="300"></alpha>
    </set> *
     */
    private lateinit var mFlipper: Flipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flipper)

        mFlipper = findViewById(R.id.flipper)


        mFlipper.setDuration(300)
            .setIntervalTime(1200)
            .setTexts(Arrays.asList("111111", "22222222", "33333333", "4444444"))
            .startFlipping()

    }
}
