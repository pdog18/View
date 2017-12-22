package com.example.a18.path.map;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.provider.SyncStateContract;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.example.a18.path.R;

import butterknife.OnClick;
import timber.log.Timber;

public class MapEventActivity extends BaseMapActivity {

    boolean flag = false;
    private ValueAnimator ofFloat;

    @OnClick(R.id.setCompassEnabled)
    void setCompassEnabled() {
        mUiSettings.setCompassEnabled(flag ^= true);
    }


    @OnClick(R.id.setZoomControlsEnabled)
    void setZoomControlsEnabled() {
        mUiSettings.setZoomControlsEnabled(flag ^= true);
        Timber.d(flag + "");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map_event;
    }

    @Override
    protected void init() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }


    @OnClick(R.id.rd_mark)
    void rd_rotate() {
        MarkerOptions markerOption = new MarkerOptions();

        markerOption.position(new LatLng(latitude, longitude));
        markerOption.title("杭州").snippet("西安市：34.341568, 108.940174");

        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.ic_launcher)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
        Marker marker = aMap.addMarker(markerOption);








        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                Toast.makeText(MapEventActivity.this, title, Toast.LENGTH_SHORT).show();
                return flag ^= true;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
//        aMap.addMarker(markerOption);

        drag();
    }


    void drag() {
        // 定义 Marker拖拽的监听
        AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {

            // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0.getPosition());

            }

            // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0.getPosition());


            }

            // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0.getPosition());


            }
        };
// 绑定marker拖拽事件
        aMap.setOnMarkerDragListener(markerDragListener);
        System.out.println("aMap");
    }
}


