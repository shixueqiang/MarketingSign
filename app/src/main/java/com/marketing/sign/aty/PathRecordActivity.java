package com.marketing.sign.aty;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.elvishew.xlog.XLog;
import com.marketing.sign.R;
import com.marketing.sign.model.PathRecordModel;
import com.marketing.sign.utils.MapUtil;

import java.util.List;

/**
 * 轨迹回放界面
 * Created by shixq on 2017/7/2.
 */

public class PathRecordActivity extends BaseActivity implements AMap.OnMapLoadedListener, TraceListener {
    private MapView mMapView;
    private AMap aMap;
    private PathRecordModel pathRecordModel;
    public static final String PATH_RECORDMODEL_KEY = "PATH_RECORDMODEL_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        pathRecordModel = (PathRecordModel) getIntent().getParcelableExtra(PATH_RECORDMODEL_KEY);
        XLog.d("path date : " + pathRecordModel.getDate());
        initMap();
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setOnMapLoadedListener(this);
        }
    }

    /**
     * 地图上添加纠偏后轨迹线路及起终点、轨迹动画小人
     */
    private void addGraspTrace(List<LatLng> graspList) {
        if (graspList == null || graspList.size() < 2) {
            return;
        }
        LatLng startPoint = graspList.get(0);
        LatLng endPoint = graspList.get(graspList.size() - 1);
        aMap.addPolyline(new PolylineOptions().setCustomTexture(
                BitmapDescriptorFactory.fromResource(R.mipmap.grasp_trace_line)).width(40).addAll(graspList));
        aMap.addMarker(new MarkerOptions().position(startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        aMap.addMarker(new MarkerOptions().position(endPoint).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.end)));
        aMap.addMarker(new MarkerOptions().position(startPoint).icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.walk))));
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
    public void onMapLoaded() {
        // 轨迹纠偏初始化
        LBSTraceClient mTraceClient = LBSTraceClient.getInstance(getApplicationContext());
        List<AMapLocation> recordList = pathRecordModel.getPathline();
        List<TraceLocation> mGraspTraceLocationList = MapUtil.parseTraceLocationList(recordList);
        // 调用轨迹纠偏，将mGraspTraceLocationList进行轨迹纠偏处理
        mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList, LBSTraceClient.TYPE_AMAP, this);
    }

    @Override
    public void onRequestFailed(int i, String s) {
        XLog.e("轨迹纠偏失败：" + s);
    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }

    /**
     * 轨迹纠偏完成数据回调
     */
    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {
        addGraspTrace(list);
    }
}
