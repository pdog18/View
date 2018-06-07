package nest.pdog.com.ramotion.examples.simple.cards;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nest.pdog.com.module_cardlist.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderCard> {

    private final int count;
    private final int[] content;
    private final View.OnClickListener listener;

    public SliderAdapter(int[] content, int count, View.OnClickListener listener) {
        this.content = content;
        this.count = count;
        this.listener = listener;
    }

    @Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_slider_card, parent, false);

        if (listener != null) {
            view.setOnClickListener(view1 -> listener.onClick(view1));
        }

        return new SliderCard(view);
    }

    @Override
    public void onBindViewHolder(SliderCard holder, int position) {
        holder.setContent(content[position % content.length]);
    }

    @Override
    public void onViewRecycled(SliderCard holder) {
    }

    @Override
    public int getItemCount() {
        return count;
    }

}
