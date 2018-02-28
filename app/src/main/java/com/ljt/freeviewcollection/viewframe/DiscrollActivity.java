package com.ljt.freeviewcollection.viewframe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljt.freeviewcollection.R;

public class DiscrollActivity extends Activity {

    protected DiscrollView mDiscrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discroll);
//        MyTextView mv= (MyTextView) findViewById(R.id.mtv);
        mDiscrollView= (DiscrollView) findViewById(R.id.layout);
    }
}
