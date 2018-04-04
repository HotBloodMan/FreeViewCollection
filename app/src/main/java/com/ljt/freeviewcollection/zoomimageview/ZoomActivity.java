package com.ljt.freeviewcollection.zoomimageview;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ljt.freeviewcollection.R;


public class ZoomActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private int[] mImgs = new int[]{R.drawable.screenshot1, R.drawable.screenshot1,
            R.drawable.screenshot1};
    private ImageView[] mImageViews = new ImageView[mImgs.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom2);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView zoomImageView = new ZoomImageView(getApplicationContext());
                zoomImageView.setImageResource(mImgs[position]);
                container.addView(zoomImageView);
                mImageViews[position] = zoomImageView;
                return zoomImageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }
        });
    }
}
