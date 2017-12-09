package com.example.a18.path.scrollchart;

import java.util.List;



public class BackMoneyAdapter extends ChartAdapter<BackMoneyAdapter.Body> {
    public BackMoneyAdapter(int layoutResId, List<Body> datas) {
        super(layoutResId, datas);
    }

    public static class Body{
        int progress;

        public int getProgress() {
            return progress;
        }
    }
}
