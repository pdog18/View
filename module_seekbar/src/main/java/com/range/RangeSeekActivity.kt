package com.range

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.module_seekbar.R
import kotlinx.android.synthetic.main.activity_range_seek.*
import timber.log.Timber

class RangeSeekActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range_seek)
        range.setOnRangeChangedListener { view, min, max ->
            println()
            println(view.currentRange[0])
            println(view.currentRange[1])
        }

        seek.callback = { seek, left, right ->
            Timber.d("left = ${Math.round(left)} right = ${Math.round(right)}")
        }
    }
}
