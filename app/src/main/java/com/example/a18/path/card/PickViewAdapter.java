package com.example.a18.path.card;


import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class PickViewAdapter<T> {
    List<T> items;

    public PickViewAdapter(List<T> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }


    int getCount() {
        return items.size();
    }

    View getView(int position, ViewGroup parent) {
        return createView(parent, items.get(position), position);
    }

    protected abstract View createView(ViewGroup parent, T t, int position);
}
