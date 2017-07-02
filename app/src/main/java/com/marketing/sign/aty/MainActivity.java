package com.marketing.sign.aty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marketing.sign.R;
import com.marketing.sign.service.SignService;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_to_sign).setOnClickListener(this);
        startService(new Intent(this,SignService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_sign:
                startActivity(new Intent(this,WorkActivity.class));
                break;
        }
    }
}
