package com.ljt.freeviewcollection.customwanim;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ljt.freeviewcollection.R;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_page);
        setUpView();
    }

    private void setUpView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageView ivWomen = (ImageView) findViewById(R.id.iv_woman);
        ArrayList<PageFragment> fragments = createFragments();

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pageAdapter);
      PageHelper.newInstance().startListener(viewPager,ivWomen,fragments);
    }
    private ArrayList<PageFragment> createFragments(){
        ArrayList<PageFragment> pageFragments = new ArrayList<>();
        pageFragments.add(PageFragment.newInstance(R.layout.view_intro_1));
        pageFragments.add(PageFragment.newInstance(R.layout.view_intro_2));
        pageFragments.add(PageFragment.newInstance(R.layout.view_intro_3));
        pageFragments.add(PageFragment.newInstance(R.layout.view_intro_4));
        pageFragments.add(PageFragment.newInstance(R.layout.view_intro_5));
        pageFragments.add(PageFragment.newInstance(R.layout.view_login));
        return pageFragments;
    }

}
