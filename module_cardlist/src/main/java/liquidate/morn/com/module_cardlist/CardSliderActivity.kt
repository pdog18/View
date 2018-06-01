package liquidate.morn.com.module_cardlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_slider.*
import liquidate.morn.com.ramotion.cardslider.CardSliderLayoutManager
import liquidate.morn.com.ramotion.examples.simple.cards.SliderAdapter

class CardSliderActivity : AppCompatActivity() {

    private val pics = intArrayOf(
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_slider)

        val sliderAdapter = SliderAdapter(pics, 20) { _ ->
        }

        rv.adapter = sliderAdapter
        rv.layoutManager = CardSliderLayoutManager(this)

    }
}
