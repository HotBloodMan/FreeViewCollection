package com.ljt.freeviewcollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljt.freeviewcollection.customcircle.CustomCicleView;
import com.ljt.freeviewcollection.customcircle.CustomCircleChart;

import java.util.ArrayList;
import java.util.List;

/*
* 方式1 继承View
* （1）继承自View创建自定义控件
* （2）如有需要自定义View属性，也就是在values/attrs.xml中定义属性集
* （3）在xml中引入命名控件，设置属性；
* （4）在代码中读取xml中的属性，初始化视图；
* （5）测量视图大小
* （6）绘制视图内容。
*
* */

public class MainActivity extends AppCompatActivity {

    private CustomCicleView mCicleView;
    private CustomCircleChart cChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initView();
    }

    private void initView() {
        //实现动态缩放圆形
//        mCicleView = (CustomCicleView) findViewById(R.id.ccv_main1);
        //圆环图表
//        cChart = (CustomCircleChart) findViewById(R.id.cc_chart2);
//        swichCustomView(1);
    }

    public void swichCustomView(int i){
        switch (i){
            case 1:
                //实现动态缩放圆形
                new Thread(mCicleView).start();
            break;
            case 2:
            break;
            case 3:
            break;
            case 4:
            break;
            case 5:
            break;
            case 6:
            break;
            case 7:
            break;
            case 8:
            break;
            case 9:
            break;
        }
    }
}
