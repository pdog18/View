package com.example.a18.path.text;

import android.view.View;

@FunctionalInterface
public interface ChildViewChangedListener {
    void onViewChanged(View last, View current);
}
