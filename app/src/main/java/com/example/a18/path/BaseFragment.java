package com.example.a18.path;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract  class BaseFragment extends Fragment {

    private Unbinder bind;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    protected abstract int getLayoutId();
}
