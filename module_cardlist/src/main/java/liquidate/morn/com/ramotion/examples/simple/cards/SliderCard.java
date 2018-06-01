package liquidate.morn.com.ramotion.examples.simple.cards;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import liquidate.morn.com.module_cardlist.R;


public class SliderCard extends RecyclerView.ViewHolder {

    private static int viewWidth = 0;
    private static int viewHeight = 0;

    private final ImageView imageView;


    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(@DrawableRes final int resId) {
        if (viewWidth == 0) {
            itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    viewWidth = itemView.getWidth();
                    viewHeight = itemView.getHeight();
                }
            });
        }
    }

}