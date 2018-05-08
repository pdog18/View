package kt.pdog18.com.module_timeline

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_timeline.*

class TimelineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_timeline) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.tv, item)
                helper.setText(R.id.tv_time, "2000年${item}月")
                helper.setText(R.id.tv_state, "状态${item}执行")
                val timelineView = helper.getView<TimelineState>(R.id.img_state)
                when (helper.adapterPosition) {
                    0 -> {      //第一个就是头
                        timelineView.changeState(HEAD)
                    }
                    1 -> {      //第二个就是当前状态了
                        timelineView.changeState(ACTIVE)
                        helper.itemView.setBackgroundColor(Color.BLUE)
                        helper.setTextColor(R.id.tv,Color.WHITE)
                        helper.setTextColor(R.id.tv_time,Color.WHITE)
                        helper.setTextColor(R.id.tv_state,Color.WHITE)
                    }
                    9 -> {
                        timelineView.changeState(FOOTER)
                    }
                    else -> {
                        timelineView.changeState(BODY)
                    }
                }
            }
        }.apply {
            setNewData((0..9).map { it.toString() })
        }
    }
}
