package kt.pdog18.com.module_canvas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView

class Canvas1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas1)
        val imageView = findViewById<ImageView>(R.id.img)
        imageView.post {
            val simpleName = imageView.context.javaClass.simpleName
            println(simpleName)
            println(imageView.context === this@Canvas1Activity)

            val `$3D` = `$3D`(imageView.context, 0f, 360f, (imageView.width / 2).toFloat(), (imageView.height / 2).toFloat(), 0f, true)
            `$3D`.setDuration(3000)
            `$3D`.setInterpolator(LinearInterpolator())
            `$3D`.setRepeatCount(Animation.INFINITE)
            imageView.startAnimation(`$3D`)
        }
    }
}
