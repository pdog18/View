package kt.pdog18.com.module_homecard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*



class HomeCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = RecyclerAdapter(R.layout.adapter_card_search)
        adapter.setNewData(listOf(1,2,3,4,5,6,7,8,9,0))


        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL,false)
        PagerSnapHelper().attachToRecyclerView(recyclerview)
    }
}
