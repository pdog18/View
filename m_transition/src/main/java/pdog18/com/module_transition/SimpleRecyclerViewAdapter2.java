package pdog18.com.module_transition;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SimpleRecyclerViewAdapter2 extends RecyclerView.Adapter<SimpleRecyclerViewAdapter2.VH> {

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH vh = new VH(parent);
        vh.itemView.setOnClickListener(v -> {
            // position
//            vh.getAdapterPosition()
//            vh.getLayoutPosition()
//            vh.getPosition()
        });
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
