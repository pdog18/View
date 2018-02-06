package com.example.a18.path.materialdesign;

import android.widget.EditText;
import android.widget.ImageView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;


public class GifFragment extends BaseFragment {
    @BindView(R.id.img)
    ImageView imageView;

    @BindView(R.id.tv_result)
    EditText mResultText;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gif;
    }



}

