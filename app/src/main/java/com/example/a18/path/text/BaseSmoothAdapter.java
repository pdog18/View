package com.example.a18.path.text;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseSmoothAdapter<T> {
    List<T> list;

    public BaseSmoothAdapter(List<T> list) {
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public View getView(ViewGroup viewGroup, int index) {
        return createView(viewGroup,list.get(index), index);
    }

    public abstract View createView(ViewGroup viewGroup, T t, int index);

}
