package com.example.a18.path.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.a18.path.R
import com.example.a18.path.ext.dp
import timber.log.Timber

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint: Paint = Paint()
    val bitmap: Bitmap

    init {
        paint.color = Color.RED
        paint.strokeWidth = 20.dp
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)
    }

    var speedX = -5
    var speedY = -5


    fun sport() {
        postDelayed({
            doTranslate()
        }, 16)
    }

    private fun doTranslate() {
        updateSpeed()

        translationX += speedX
        translationY += speedY

        sport()
    }

    private fun updateSpeed() {
        val p = parent as ViewGroup

        if (x <= 0 || x +width >= p.width) {
            Timber.d("x <= 0 || x >= p.width")
            speedX = -speedX
        }

        if (y <= 0 || y + height >= p.height) {
            Timber.d("y <= 0 || y >= p.height")
            speedY = -speedY
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }
}