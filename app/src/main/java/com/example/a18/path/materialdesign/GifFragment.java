package com.example.a18.path.materialdesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.a18.path.BaseFragment;
import com.example.a18.path.R;

import butterknife.BindView;


public class GifFragment extends BaseFragment {
    @BindView(R.id.img)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gif;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Glide.with(imageView)
//            .asGif()
//            .load(R.drawable.sound)
//            .into(imageView);
    }
}
