package pdog18.com.module_constraint;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.leochuan.CenterSnapHelper;
import com.leochuan.ScaleLayoutManager;

import pdog18.com.base.BaseFragment;

public class ChainStyle3Fragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_constraint3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        ScaleLayoutManager viewPagerLayoutManager = createLayoutManager();
        recyclerview.setAdapter(new DataAdapter());
        recyclerview.setLayoutManager(viewPagerLayoutManager);
        new CenterSnapHelper().attachToRecyclerView(recyclerview);
        viewPagerLayoutManager.setInfinite(true);
    }

    protected ScaleLayoutManager createLayoutManager() {
        return new ScaleLayoutManager(this.getContext(), 30);
    }



    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
        private int[] images = {R.drawable.item1, R.drawable.item2, R.drawable.item3,
            R.drawable.item4, R.drawable.item5, R.drawable.item6, R.drawable.item7,
            R.drawable.item8, R.drawable.item9, R.drawable.item10};

        @Override
        public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
        }

        @Override
        public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
            holder.imageView.setImageResource(images[position]);
            holder.imageView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
                imageView.setOnClickListener(v -> Toast.makeText(v.getContext(), "clicked:" + v.getTag(), Toast.LENGTH_SHORT).show());
            }
        }
    }
}
