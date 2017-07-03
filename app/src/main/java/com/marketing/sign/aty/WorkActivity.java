package com.marketing.sign.aty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marketing.sign.R;
import com.marketing.sign.service.SignService;

/**
 * 签到成功，开始工作界面
 * Created by shixq on 2017/7/2.
 */

public class WorkActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        findViewById(R.id.btn_sign_start).setOnClickListener(this);
        findViewById(R.id.btn_sign_end).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_start:
                startService(new Intent(this,SignService.class));
                break;
            case R.id.btn_sign_end:
                stopService(new Intent(this,SignService.class));
                break;
        }
    }
}
