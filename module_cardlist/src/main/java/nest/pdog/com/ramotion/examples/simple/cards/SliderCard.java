package nest.pdog.com.ramotion.examples.simple.cards;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import nest.pdog.com.module_cardlist.R;


public class SliderCard extends RecyclerView.ViewHolder {


    private final ImageView imageView;


    public SliderCard(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    void setContent(@DrawableRes final int resId) {
    }
}