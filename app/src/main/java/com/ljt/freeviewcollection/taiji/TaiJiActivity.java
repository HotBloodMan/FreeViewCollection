package com.ljt.freeviewcollection.taiji;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljt.freeviewcollection.R;

public class TaiJiActivity extends AppCompatActivity {

    private TaiJi testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_ji);
        testView = (TaiJi) findViewById(R.id.activity_tai_ji);
        final Handler handler = new Handler() {
            private float degrees = 0;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                testView.setRotate(degrees += 2);
                this.sendEmptyMessageDelayed(0, 20);
            }
        };
        handler.sendEmptyMessageDelayed(0, 20);
    }
}
