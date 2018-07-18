package com.pdog18.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout_manager.*

class LayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)

        da_chart.setOnClickListener {
            da_chart.setValue(20.8f, floatArrayOf(20.8f,
                20.8f,
                20.8f,
                20.8f,
                20.8f,
                20.8f
                ))
        }


        btn.setOnClickListener {
            da_chart.doAnim()
        }
    }
}
