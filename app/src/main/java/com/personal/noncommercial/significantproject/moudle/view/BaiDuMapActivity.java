package com.personal.noncommercial.significantproject.moudle.view;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaExtractor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/6/4
 * E-mail:lizc@bsoft.com.cn
 * @类说明 百度定位
 */

public class BaiDuMapActivity extends BaseActivity {


    @BindView(R.id.map_view)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;

    boolean isFirstLoc = true; // 是否首次定位
    private MyBDLocationListener myListener;


    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initLocation();
    }

    private void initLocation() {
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_LOCATION_PERMISSION)) {
            initBaiduLocation();
        }
    }

    private void initBaiduLocation() {
        // 定位初始化
        mLocClient = new LocationClient(this);
        myListener = new MyBDLocationListener();
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && PermissionHelper.checkAllPermissionResult(grantResults))
                    initBaiduLocation();
                else
                    PermissionHelper.addLocationPermissionDialog(mContext);

                break;
        }


    }


    @Override
    protected int getRootLayout() {
        return R.layout.activity_map_baidu;
    }

    /**
     * 定位SDK监听
     */

    public class MyBDLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                // 构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
                // 构建MarkerOption，用于在地图上添加Marker
                OverlayOptions overlayOptions = new MarkerOptions().position(ll).icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(overlayOptions);
            }
        }

    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
