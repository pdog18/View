package pdog18.com.hencoder

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun getBitmap(res: Resources, id: Int, size: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, id, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = size
    return BitmapFactory.decodeResource(res, id, options)
}