package com.example.a18.path.proterduff;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.example.a18.path.R;

public class ProterDuffActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ProterDuffView proterDuffView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proter_duff);
        proterDuffView = (ProterDuffView) findViewById(R.id.pdv);


        ((RadioGroup) findViewById(R.id.rg)).setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.clear:
                proterDuffView.setXfermode(PorterDuff.Mode.CLEAR);
                break;
            case R.id.src:
                proterDuffView.setXfermode(PorterDuff.Mode.SRC);
                break;
            case R.id.dst:
                proterDuffView.setXfermode(PorterDuff.Mode.DST);
                break;
            case R.id.src_over:
                proterDuffView.setXfermode(PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.dst_over:
                proterDuffView.setXfermode(PorterDuff.Mode.DST_OVER);
                break;
            case R.id.src_in:
                proterDuffView.setXfermode(PorterDuff.Mode.SRC_IN);
                break;
            case R.id.dst_in:
                proterDuffView.setXfermode(PorterDuff.Mode.DST_IN);
                break;
            case R.id.src_out:
                proterDuffView.setXfermode(PorterDuff.Mode.SRC_OUT);
                break;
            case R.id.dst_out:
                proterDuffView.setXfermode(PorterDuff.Mode.DST_OUT);
                break;
            case R.id.src_atop:
                proterDuffView.setXfermode(PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.dst_atop:
                proterDuffView.setXfermode(PorterDuff.Mode.DST_ATOP);
                break;
            case R.id.xor:
                proterDuffView.setXfermode(PorterDuff.Mode.XOR);
                break;
            case R.id.add:
                proterDuffView.setXfermode(PorterDuff.Mode.ADD);
                break;
            case R.id.multiply:
                proterDuffView.setXfermode(PorterDuff.Mode.MULTIPLY);
                break;
            case R.id.screen:
                proterDuffView.setXfermode(PorterDuff.Mode.SCREEN);
                break;
            case R.id.overlay:
                proterDuffView.setXfermode(PorterDuff.Mode.OVERLAY);
                break;
            case R.id.darken:
                proterDuffView.setXfermode(PorterDuff.Mode.DARKEN);
                break;
            case R.id.lighten:
                proterDuffView.setXfermode(PorterDuff.Mode.LIGHTEN);
                break;
        }
    }

    public void setBackgroundColor(View view) {
        proterDuffView.setBackgroundColor(Color.GRAY);

    }

    public void clearBackgroundColor(View view) {
        proterDuffView.setBackground(null);
    }
}
