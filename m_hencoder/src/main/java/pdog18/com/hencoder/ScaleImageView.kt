package pdog18.com.hencoder

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import pdog18.com.core.ext.dp


class ScaleImageView(context: Context, attr: AttributeSet) : View(context, attr) {

    private val bitmap: Bitmap

    private fun getBitmap(res: Resources, id: Int, size: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, id, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = size
        return BitmapFactory.decodeResource(res, id, options)
    }


    init {
        bitmap = getBitmap(resources, R.mipmap.ic_launcher, 300.dp)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, width / 2f - bitmap.width / 2, height / 2f - bitmap.height / 2, Paint())


        canvas.drawLine(width / 2f, 0f, width / 2f, height * 1f, Paint().apply { color = Color.BLACK })
        canvas.drawLine(0f, height / 2f, width * 1f, height / 2f, Paint().apply { color = Color.BLACK })
    }
}