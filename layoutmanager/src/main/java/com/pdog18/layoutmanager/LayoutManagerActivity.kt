package com.pdog18.layoutmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout_manager.*

class LayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)

        da_chart.setOnClickListener {
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
            startActivity(Intent(this@LayoutManagerActivity,MainActivity::class.java))
        }

        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv.adapter = DAAdapter()
    }
}
