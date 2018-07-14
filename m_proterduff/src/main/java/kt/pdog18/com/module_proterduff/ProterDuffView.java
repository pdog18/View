package kt.pdog18.com.module_proterduff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by machao on 2017/11/27.
 */
public class ProterDuffView extends AppCompatImageView {

    private final Paint paint;
    private PorterDuffXfermode xfermode;
    private final Bitmap dst;
    private final Bitmap src;

    public ProterDuffView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);


        dst = BitmapFactory.decodeResource(getResources(), R.drawable.destination);
        src = BitmapFactory.decodeResource(getResources(), R.drawable.source);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = dst.getWidth();
        int height = dst.getHeight();
        int widthMS = MeasureSpec.makeMeasureSpec(width, widthMeasureSpec);
        int heightMS = MeasureSpec.makeMeasureSpec(height, heightMeasureSpec);
        setMeasuredDimension(widthMS,heightMS);
    }

    private static final String TAG = "ProterDuffView";

    public void setXfermode(PorterDuff.Mode xfermode) {
        Log.e(TAG, "setXfermode:   " + xfermode.name());

        this.xfermode = new PorterDuffXfermode(xfermode);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dst, 0, 0, paint);
        paint.setXfermode(xfermode);

        canvas.drawBitmap(src, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);

        canvas.restore();
    }
}
