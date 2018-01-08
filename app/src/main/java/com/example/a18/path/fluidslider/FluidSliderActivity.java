package com.example.a18.path.fluidslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FluidSliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluid_slider);
        ButterKnife.bind(this);
    }

    @BindView(R.id.img)
    ImageView imageView;
    @OnClick(R.id.cardview)
    void setV() {
        int visibility = imageView.getVisibility();
        if (visibility == View.GONE) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
