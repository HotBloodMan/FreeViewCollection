package com.ljt.freeviewcollection.rote3dview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ljt.freeviewcollection.R;
import com.ljt.freeviewcollection.custombannerview.BannerView;

public class Rote3DActivity extends AppCompatActivity {

    private BannerView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rote3_d);
        bv = (BannerView) findViewById(R.id.bv);
        bv.setListener(new BannerView.onTouchListener() {
            @Override
            public void touchListener(String s) {
                Toast.makeText(Rote3DActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
