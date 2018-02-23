package com.ljt.freeviewcollection.bounceloadingview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljt.freeviewcollection.R;

public class BounceActivity extends AppCompatActivity {

    private BounceLoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce);
        loadingView = (BounceLoadingView) findViewById(R.id.loadingView);

        loadingView.addBitmap(R.mipmap.v4);
        loadingView.addBitmap(R.mipmap.v5);
        loadingView.addBitmap(R.mipmap.v6);
        loadingView.addBitmap(R.mipmap.v7);
        loadingView.addBitmap(R.mipmap.v8);
        loadingView.addBitmap(R.mipmap.v9);
        loadingView.setShadowColor(Color.LTGRAY);
        loadingView.setDuration(1000);
        loadingView.start();


    }
}
