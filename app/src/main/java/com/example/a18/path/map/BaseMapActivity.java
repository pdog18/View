package com.example.a18.path.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class BaseMapActivity extends AppCompatActivity {
    @BindView(R.id.map)
    protected MapView mapView;

    protected AMap aMap;

    protected UiSettings mUiSettings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        ButterKnife.bind(this);
        aMap = mapView.getMap();

        mapView.onCreate(savedInstanceState);

        mUiSettings = aMap.getUiSettings();
        init();

    }
//    latitude=30.249054#longitude=120.215563#

    protected static float latitude = 30.249054f;
    protected static float longitude = 120.215563f;

    protected void init() {
    }


    protected abstract int getLayout();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

}
