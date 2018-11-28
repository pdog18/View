package com.pdog18.layoutmanager

import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.adapter = SimpleAdapter()
        list.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this, VERTICAL))
    }
}
