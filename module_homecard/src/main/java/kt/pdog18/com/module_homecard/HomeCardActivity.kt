package kt.pdog18.com.module_homecard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*


class HomeCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = RecyclerAdapter(R.layout.adapter_card_search)
        adapter.setNewData(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))


        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(recyclerview)


        img_notice.setOnClickListener {
            showAnimation()
        }
    }

    private fun showAnimation() {
        // 获取自定义动画实例
        val rotateAnim = CustomRotateAnim()
        // 一次动画执行1秒
        rotateAnim.duration = 1000
        // 设置为循环播放
        rotateAnim.repeatCount = -1
        // 设置为匀速
        rotateAnim.interpolator = LinearInterpolator()
        // 开始播放动画
        img_notice.startAnimation(rotateAnim)
    }


    inner class CustomRotateAnim : Animation() {

        /** 控件宽  */
        private var mWidth: Int = 0

        /** 控件高  */
        private var mHeight: Int = 0

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            this.mWidth = width
            this.mHeight = height
            super.initialize(width, height, parentWidth, parentHeight)
        }

        override  fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            // 左右摇摆
            t.matrix.setRotate((Math.sin(interpolatedTime.toDouble() * Math.PI * 2.0) * 50).toFloat(), mWidth / 2.0f, mHeight / 2.0f)
            super.applyTransformation(interpolatedTime, t)
        }
    }
}
