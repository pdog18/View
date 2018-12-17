package pdog18.com.module_canvas;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * author: 18
 * email: pdog@qq.com
 * time: 2017/11/12  13 :23
 */

public class $3D extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    float scale = 1;    // <------- 像素密度

    /**
     * Creates EvaluatorActivity new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around EvaluatorActivity center point on the 2D space, definied by EvaluatorActivity pair
     * of X and Y coordinates, called centerX and centerY. When the animation
     * starts, EvaluatorActivity translation on the Z axis (depth) is performed. The length
     * of the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees   the end angle of the 3D rotation
     * @param centerX     the X center of the 3D rotation
     * @param centerY     the Y center of the 3D rotation
     * @param reverse     true if the translation should be reversed, false otherwise
     */
    public $3D(Context context, float fromDegrees, float toDegrees,
               float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;

        scale = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();


        // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
        float[] mValues = new float[9];
        matrix.getValues(mValues);                //获取数值
        mValues[6] = mValues[6] / scale;            //数值修正
        mValues[7] = mValues[7] / scale;            //数值修正
        matrix.setValues(mValues);                //重新赋值


        System.out.println(matrix.toString());


        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}