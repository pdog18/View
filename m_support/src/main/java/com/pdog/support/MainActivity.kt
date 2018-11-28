package com.pdog.support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        list.adapter = Adapter().apply {
            setNewData(listOf(
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111",
                "1111"
            ))
        }
    }

    class Adapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item) {
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.getView<TextView>(R.id.tv_item).text = item
        }
    }
}
