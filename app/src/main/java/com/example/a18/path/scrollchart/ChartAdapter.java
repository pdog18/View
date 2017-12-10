package com.example.a18.path.scrollchart;

import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a18.path.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个adapter 进行了封装 ，传入数据进行一次封装，传入wrap中。
 * 在数据第一次传入的时候将第一个wrap 中的状态 为激活状态，其他的为非激活状态
 */
public abstract class ChartAdapter<T> extends BaseQuickAdapter<Wrap, BaseViewHolder> {
    public ChartAdapter(int layoutResId, List<T> datas) {
        super(layoutResId);

        setWrapData(datas);
    }

    private void setWrapData(List<T> datas) {
        Wrap<T> first = new Wrap<>(datas.get(0));
        first.setDark(true);

        List<Wrap> uList = new ArrayList<>();
        uList.add(first);

        for (int i = 1; i < datas.size(); i++) {
            uList.add(new Wrap<>(datas.get(i)));
        }

        replaceData(uList);
    }


    @Override
    protected void convert(BaseViewHolder helper, Wrap item) {
        ProgressBar progressBar = helper.getView(R.id.pb_vertical_simple_shape);
        progressBar.setProgress(helper.getAdapterPosition());

        Drawable light = helper.itemView.getResources().getDrawable(R.drawable.progress_light);
        Drawable dark = helper.itemView.getResources().getDrawable(R.drawable.progress_dark);

        if (item.dark) {
            progressBar.setProgressDrawable(dark);
            helper.setVisible(R.id.tv_progress, true);
        } else {
            progressBar.setProgressDrawable(light);
            helper.setVisible(R.id.tv_progress, false);
        }

        helper.itemView.setOnClickListener(v -> {
            List<Wrap> data = getData();
            for (Wrap datum : data) {
                datum.setDark(false);
            }

            item.setDark(true);

            if (listener !=null) {
                listener.onClick(helper.getAdapterPosition());
            }
            notifyDataSetChanged();
        });
    }

    ClickListener listener;

    public void setListener(ClickListener listener){
      this.listener   = listener;
    }


    int last = -1;

    interface ClickListener{
        void onClick(int i);
    }

}
