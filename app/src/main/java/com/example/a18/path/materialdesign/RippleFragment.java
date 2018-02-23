package com.example.a18.path.materialdesign;

import android.widget.Toast;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.OnClick;


public class RippleFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gif;
    }

    @OnClick(R.id.foreground)
    void foreground() {
        Toast.makeText(getContext(), "aaaa", Toast.LENGTH_SHORT).show();
    }
}

