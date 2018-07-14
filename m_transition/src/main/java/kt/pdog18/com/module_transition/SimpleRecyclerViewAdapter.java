package kt.pdog18.com.module_transition;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SimpleRecyclerViewAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    static List<Integer> data;

    static {
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(i);
        }
    }

    public SimpleRecyclerViewAdapter() {
        super(R.layout.item, data);
        Timber.d("data.size() = %s", data.size());
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        helper.setText(R.id.text, String.format("position = %s", helper.getAdapterPosition()));
    }
}
