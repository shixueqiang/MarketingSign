package com.marketing.sign.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.marketing.sign.R;
import com.marketing.sign.aty.WorkActivity;

/**
 * Created by shixq on 2017/7/2.
 */

public class SignService extends Service {
    private final static int GRAY_SERVICE_ID = -1001;
    @Override
    public void onCreate() {
        super.onCreate();
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
}
