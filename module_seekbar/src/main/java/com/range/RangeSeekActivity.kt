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
            Timber.d("view.currentRange = ${view.currentRange}")
        }
    }
}
