package com.pdog18.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import androidx.core.widget.toast
import kotlinx.android.synthetic.main.activity_layout_manager.*

class LayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)

        btn.setOnClickListener {
            da_chart.setValue(20.8f, floatArrayOf(
                20.8f,
                10.8f,
                30.8f,
                40.8f,
                20.8f,
                10.8f,
                0.8f,
                20.8f,
                30.8f,
                40.8f
            ))
        }

        da_chart.setOnLaunchRocketEndListener {
            toast("launch rocket success !")
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = DAAdapter()
    }
}
