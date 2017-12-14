package com.example.a18.path.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.example.a18.path.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MapActivity extends AppCompatActivity implements OfflineMapManager.OfflineMapDownloadListener {
    @BindView(R.id.map)
    MapView mMapView;

    AMap aMap;
    private OfflineMapManager amapManager;

    @OnClick(R.id.stop)
    void downloadOffline() {
        //下载离线地图
        //构造OfflineMapManager对象
        amapManager = new OfflineMapManager(MapActivity.this, this);
        try {
            amapManager.downloadByCityName("北京市");
        } catch (AMapException e) {
            e.printStackTrace();

        }

    }

    @OnClick(R.id.btn1)
    void btn1() {
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);//卫星
    }

    @OnClick(R.id.btn2)
    void btn2() {
        aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图，aMap是地图控制器对象。
    }

    @OnClick(R.id.btn3)
    void btn3() {
        aMap.setTrafficEnabled(true);//夜景地图，aMap是地图控制器对象。
    }

    @OnClick(R.id.获取城市列表)
    void 获取城市列表() {
        ArrayList<OfflineMapCity> offlineMapCityList = amapManager.getOfflineMapCityList();
        Timber.d(offlineMapCityList.toString());

    }

    @OnClick(R.id.获取已下载城市列表)
    void 获取已下载城市列表() {
        Timber.d(amapManager.getDownloadOfflineMapCityList().toString());


    }

    @OnClick(R.id.获取正在或等待下载城市列表)
    void 获取正在或等待下载城市列表() {
        Timber.d(amapManager.getDownloadingCityList().toString());
    }
    @OnClick(R.id.定地图样式文件)
    void 定地图样式文件() {

        String[] locales = getAssets().getLocales();
        for (String locale : locales) {
            System.out.println(locale);
        }


        aMap.setCustomMapStylePath("/assets/mystyle_sdk_1513232338_0100.data");
    }



    @OnClick(R.id.获取省列表)
    void 获取省列表() {

        ArrayList<OfflineMapProvince> offlineMapProvinceList = amapManager.getOfflineMapProvinceList();
        Timber.d(offlineMapProvinceList.toString());


    }

    @OnClick(R.id.检查更新)
    void 检查更新() {

        //通过updateOfflineCityByName方法判断离线地图数据是否存在更新
        try {
            amapManager.updateOfflineCityByName("北京市");
        } catch (AMapException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.删除某一城市的离线地图包)
    void 删除某一城市的离线地图包() {
        amapManager.remove("北京市");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mMapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.addArc(new ArcOptions());
        }

        request();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.strokeColor(Color.parseColor("#ff0000"));
        myLocationStyle.radiusFillColor(Color.parseColor("#ff0000"));
        aMap.setOnMyLocationChangeListener(location -> {

        });

    }

    private void request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断该应用是否有写SD卡权限，如果没有再去申请
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            System.out.println(grantResult);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDownload(int i, int i1, String s) {
        Timber.d(s);
    }

    @Override
    public void onCheckUpdate(boolean b, String s) {
        Timber.d(s);
    }

    @Override
    public void onRemove(boolean b, String s, String s1) {
        Timber.d(s);
    }
}