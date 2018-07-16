package com.pdog18.layoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kt.pdog18.com.core.ext.dp
import kt.pdog18.com.core.ext.scaledDensity
import kt.pdog18.com.core.ext.sp

class LayoutManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)

//        recycler_view.apply {
//            this.adapter = SampleAdapter(this@LayoutManagerActivity)
//
//            this.layoutManager = TurnLayoutManager(this@LayoutManagerActivity,
//                TurnLayoutManager.Gravity.END,
//                TurnLayoutManager.Orientation.HORIZONTAL,
//                800,
//                0,
//                false)
//        }




    }


    override fun onResume() {
        super.onResume()
        Log.e(Tag, 10.dp.toString())
        Log.e(Tag, 10.sp.toString())

        Log.e(Tag, scaledDensity.toString())
    }
    val Tag = "TAG"
}
