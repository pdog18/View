package kt.pdog18.com.module_matrix

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.util.ArrayList

class MatrixMapActivity : AppCompatActivity() {

    internal var viewList: MutableList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_map)

        val tv_scale_x = findViewById<View>(R.id.tv_scale_x)
        val tv_scale_y = findViewById<View>(R.id.tv_scale_y)
        val tv_skew_x = findViewById<View>(R.id.tv_skew_x)
        val tv_skew_y = findViewById<View>(R.id.tv_skew_y)
        val tv_trans_x = findViewById<View>(R.id.tv_trans_x)
        val tv_trans_y = findViewById<View>(R.id.tv_trans_y)
        val tv_persp_0 = findViewById<View>(R.id.tv_persp_0)
        val tv_persp_1 = findViewById<View>(R.id.tv_persp_1)
        val tv_persp_2 = findViewById<View>(R.id.tv_persp_2)


        viewList.add(tv_persp_0)
        viewList.add(tv_persp_1)
        viewList.add(tv_persp_2)
        viewList.add(tv_scale_x)
        viewList.add(tv_scale_y)
        viewList.add(tv_skew_x)
        viewList.add(tv_skew_y)
        viewList.add(tv_trans_x)
        viewList.add(tv_trans_y)

        for (view in viewList) {
            (view as TextView).gravity = Gravity.CENTER
        }

        val radioGroup = findViewById<RadioGroup>(R.id.rg)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (view in viewList) {
                view.background = null
            }
            when (checkedId) {
                R.id.rd_mark -> {
                    tv_scale_x.setBackgroundColor(Color.argb(255, 244, 154, 43))
                    tv_scale_y.setBackgroundColor(Color.argb(255, 244, 154, 43))
                    tv_skew_x.setBackgroundColor(Color.argb(255, 244, 154, 43))
                    tv_skew_y.setBackgroundColor(Color.argb(255, 244, 154, 43))
                }
                R.id.rd_scale -> {
                    tv_scale_x.setBackgroundColor(Color.rgb(141, 109, 62))
                    tv_scale_y.setBackgroundColor(Color.rgb(141, 109, 62))
                }
                R.id.rd_perspective -> {
                    tv_persp_0.setBackgroundColor(Color.rgb(194, 243, 53))
                    tv_persp_1.setBackgroundColor(Color.rgb(194, 243, 53))
                    tv_persp_2.setBackgroundColor(Color.rgb(194, 243, 53))
                }
                R.id.rd_skew -> {
                    tv_skew_x.setBackgroundColor(Color.rgb(135, 137, 61))
                    tv_skew_y.setBackgroundColor(Color.rgb(135, 137, 61))
                }
                R.id.rd_translate -> {
                    tv_trans_x.setBackgroundColor(Color.rgb(34, 172, 242))
                    tv_trans_y.setBackgroundColor(Color.rgb(34, 172, 242))
                }
            }
        }

        (findViewById<View>(R.id.rd_perspective) as RadioButton).isChecked = true

    }
}
