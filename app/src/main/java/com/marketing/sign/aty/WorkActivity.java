package com.marketing.sign.aty;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.elvishew.xlog.XLog;
import com.marketing.sign.R;
import com.marketing.sign.model.PathRecordModel;
import com.marketing.sign.service.SignService;

/**
 * 签到成功，开始工作界面
 * Created by shixq on 2017/7/2.
 */

public class WorkActivity extends BaseActivity implements View.OnClickListener {
    private Intent serviceIntent;
    private SignService signService;
    private boolean isBind;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            XLog.d("SignService onServiceConnected");
            SignService.MyBinder binder = (SignService.MyBinder) service;
            signService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            XLog.e("SignService onServiceDisconnected");
            isBind = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        findViewById(R.id.btn_sign_start).setOnClickListener(this);
        findViewById(R.id.btn_sign_end).setOnClickListener(this);
        serviceIntent = new Intent(this, SignService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_start:
                startService(serviceIntent);
                isBind = bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_sign_end:
                if(isBind)
                    unbindService(serviceConnection);
                stopService(serviceIntent);
                Intent intent = new Intent(this,PathRecordActivity.class);
                PathRecordModel pathRecordModel = signService.getPathRecordModel();
                intent.putExtra(PathRecordActivity.PATH_RECORDMODEL_KEY, pathRecordModel);
                startActivity(intent);
                break;
        }
    }
}
