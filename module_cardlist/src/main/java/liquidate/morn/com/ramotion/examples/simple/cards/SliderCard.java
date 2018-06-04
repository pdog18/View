package liquidate.morn.com.ramotion.examples.simple.cards;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import liquidate.morn.com.module_cardlist.R;


public class SliderCard extends RecyclerView.ViewHolder {


    private final ImageView imageView;


    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(@DrawableRes final int resId) {
    }
}