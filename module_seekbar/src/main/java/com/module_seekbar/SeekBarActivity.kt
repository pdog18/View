package com.module_seekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_seekbar.*
import timber.log.Timber

class SeekBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seekbar)
        val seekbar = myseekbar
        seekbar.adapter = object : SeekBarAdapter<SeekBarData>(
            listOf(
                SeekBarData("0", "b", "c", "d"),
                SeekBarData("1", "222b", "c444", "d333"),
                SeekBarData("3", "222b", "c444", "d333"),
                SeekBarData("4", "222b", "c444", "d333"),
                SeekBarData("5", "222b", "c444", "d333"),
                SeekBarData("6", "222b", "c444", "d333"),
                SeekBarData("7", "ss22b", "344c", "asdgd"),
                SeekBarData("8", "ss22b", "344c", "asdgd"),
                SeekBarData("9", "ss22b", "344c", "asdgd"),
                SeekBarData("0", "ss22b", "344c", "asdgd"),
                SeekBarData("11", "ss22b", "344c", "asdgd"),
                SeekBarData("12", "ss22b", "344c", "asdgd"),
                SeekBarData("3", "b", "c", "d")
            )
        ) {
            override fun onBindView(itemView: View, item: SeekBarData) {
                itemView.findViewById<TextView>(R.id.tv_title).text = item.title
                itemView.findViewById<TextView>(R.id.tv_subtitle).text = item.subTitle
                itemView.findViewById<TextView>(R.id.tv_left).text = item.leftText
                itemView.findViewById<TextView>(R.id.tv_right).text = item.rightText
            }
        }
        seekbar.setOnReleasedListener {
            Timber.d("it = ${it}")
        }
    }
}
