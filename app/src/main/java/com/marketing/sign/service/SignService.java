package com.marketing.sign.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.elvishew.xlog.XLog;
import com.marketing.sign.R;
import com.marketing.sign.aty.WorkActivity;
import com.marketing.sign.db.PathDao;
import com.marketing.sign.model.PathRecordModel;
import com.marketing.sign.utils.TimeUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shixq on 2017/7/2.
 */

public class SignService extends Service implements AMapLocationListener {
    private final static int GRAY_SERVICE_ID = -1001;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private PathRecordModel pathRecordModel;
    private long mLocationCount;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    @Override
    public void onCreate() {
        super.onCreate();
        pathRecordModel = new PathRecordModel();
        pathRecordModel.setDate(TimeUtil.getNowTime());
        startlocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("标题");
        mBuilder.setContentText("内容");
        Intent workIntent = new Intent(this, WorkActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, workIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = mBuilder.build();
        // 设置为不能够自动清除
        notification.flags = Notification.FLAG_NO_CLEAR;
        startForeground(GRAY_SERVICE_ID, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTrimMemory(int level) {
        //内存不足时尽量释放内存
        super.onTrimMemory(level);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            XLog.d("onLocationChanged Longitude:" + aMapLocation.getLongitude() + "，Latitude:" + aMapLocation.getLatitude());
            mLocationCount++;
            pathRecordModel.addpoint(aMapLocation);
            //大约每10分钟保存一次记录
            if (mLocationCount >= 30) {
                XLog.d("保存轨迹记录");
                mLocationCount = 0;
                mExecutor.submit(new SavePathRunnable());
            }
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            XLog.e(errText);
        }
    }

    private void startlocation() {
        mLocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    class SavePathRunnable implements Runnable {

        @Override
        public void run() {
            PathDao.getInstance(getApplicationContext()).insertPathRecord(getApplicationContext(), pathRecordModel);
        }
    }
}
