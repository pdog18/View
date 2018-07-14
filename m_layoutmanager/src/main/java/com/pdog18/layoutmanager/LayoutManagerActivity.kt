package com.pdog18.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout_manager.*

class LayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)

        recycler_view.apply {
            this.adapter = SampleAdapter(this@LayoutManagerActivity)

            this.layoutManager = TurnLayoutManager(this@LayoutManagerActivity,
                TurnLayoutManager.Gravity.END,
                TurnLayoutManager.Orientation.HORIZONTAL,
                800,
                0,
                false)
        }
    }
}
