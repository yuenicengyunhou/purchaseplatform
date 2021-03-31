package com.rails.purchaseplatform.address.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.rails.purchaseplatform.address.R;
import com.rails.purchaseplatform.address.adapter.PoiAdapter;
import com.rails.purchaseplatform.address.databinding.ActivityAddressMappoiBinding;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.utils.LocationUtil;
import com.rails.purchaseplatform.common.widget.BaseRecyclerView;
import com.rails.purchaseplatform.framwork.adapter.listener.PositionListener;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rx.functions.Action1;

/**
 * @author comic
 * @date 地图，周边
 */
@Route(path = ConRoute.ADDRESS.ADDRESS_MAP)
public class MappoiActivity extends ToolbarActivity<ActivityAddressMappoiBinding> implements PoiSearch.OnPoiSearchListener, PositionListener<PoiItem> {

    private UiSettings uiSettings;
    private AMap aMap = null;
    private LatLng latLng;

    private PoiSearch poiSearch;
    private PoiSearch.Query query;
    private GeocodeSearch geocoderSearch;

    private Marker screenMarker;

    private PoiAdapter poiAdapter;

    @Override
    protected void initialize(Bundle bundle) {

        binding.titleBar
                .setShowLine(true).setImgLeftRes(R.drawable.svg_back_black)
                .setTitle("地图poi");

        barBinding.map.onCreate(bundle);
        if (aMap == null) {
            aMap = barBinding.map.getMap();
            uiSettings = aMap.getUiSettings();
            uiSettings.setAllGesturesEnabled(true);
            uiSettings.setZoomControlsEnabled(false);
            uiSettings.setZoomPosition(13);
        }
        geocoderSearch = new GeocodeSearch(this);

        poiAdapter = new PoiAdapter(this);
        poiAdapter.setListener(this);
        barBinding.recycler.setLayoutManager(BaseRecyclerView.LIST, RecyclerView.VERTICAL, false, 0);
        barBinding.recycler.setAdapter(poiAdapter);

        latLng = new LatLng(0, 0);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        loadData();
    }

    protected void loadData() {
        RxPermissions.getInstance(this).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean)
                    initLocation();
            }
        });
    }


    /**
     * 定位当前的位置
     */
    private void initLocation() {
        LocationUtil.getLocation(this, new LocationUtil.CallBack() {
            @Override
            public void getMapLocation(AMapLocation mapLocation) {
                if (null != mapLocation) {
                    double lat = mapLocation.getLatitude();
                    double lng = mapLocation.getLongitude();
                    latLng = new LatLng(lat, lng);
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                } else
                    ToastUtil.show(MappoiActivity.this, "定位失败");
            }
        });


        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                latLng = cameraPosition.target;
                addMarkerInScreenCenter(latLng);
                getStringPoint(new LatLonPoint(latLng.latitude, latLng.longitude));

            }
        });

        if (latLng != null)

            getStringPoint(new LatLonPoint(latLng.latitude, latLng.longitude));
    }


    /**
     * 根据坐标获取文字描述地址
     */
    private void getStringPoint(final LatLonPoint latLonPoint) {
        final RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(regeocodeQuery);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                query = new PoiSearch.Query("", "", regeocodeResult.getRegeocodeAddress().getCityCode());
                query.setPageNum(0);
                query.setPageSize(100);
                poiSearch = new PoiSearch(MappoiActivity.this, query);
                poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 2000));
                poiSearch.setOnPoiSearchListener(MappoiActivity.this);
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
    }


    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter(LatLng latLng) {
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }


    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

        ArrayList<PoiItem> poiItems = new ArrayList<>();
        for (PoiItem item : poiResult.getPois()) {
            if (TextUtils.isEmpty(item.getTitle()))
                continue;
            poiItems.add(item);
        }
        if (poiItems.isEmpty())
            return;
        poiAdapter.update(poiItems, true);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onPosition(PoiItem bean, int type) {
        Intent intent = new Intent();
        intent.putExtra("poi", bean);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        barBinding.map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barBinding.map != null)
            barBinding.map.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (barBinding.map != null)
            barBinding.map.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (barBinding.map != null)
            barBinding.map.onDestroy();
    }

}
