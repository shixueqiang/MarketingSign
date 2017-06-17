package com.marketing.sign.aty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.marketing.sign.R;
import com.marketing.sign.db.PathDao;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
