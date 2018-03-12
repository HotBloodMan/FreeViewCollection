package com.ljt.freeviewcollection.customwanim;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${JT.L} on 2018/3/9.
 */

public class PageHelper  implements ViewPager.OnPageChangeListener{

    private ImageView ivWomen;//在 view 滑动的时候走路的女人
    private ArrayList<IParallaxView> mViews = new ArrayList<>();//所有实现了ParallaxViewImp接口的 Fragment
    private int mWidth;//ViewPager 的宽度

    public PageHelper() {
    }
    public static PageHelper newInstance() {
        return new PageHelper();
    }

    public void startListener(ViewPager viewpager,ImageView iv,ArrayList<PageFragment> fragments){
        this.ivWomen=iv;
        viewpager.setOnPageChangeListener(this);
        mWidth=viewpager.getWidth();
        mViews.addAll(fragments);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        IParallaxView inFragment=getPosition(position-1);
        IParallaxView outFragment=getPosition(position);

        if(inFragment !=null){
            List<View> inViews = inFragment.getParallaxViews();
            if(inViews !=null){
                for(View view:inViews){
                    ParallaxViewTag tag= (ParallaxViewTag) view.getTag(view.getId());
                    if(tag==null){
                        continue;
                    }
                    //translationY改变view的偏移位置，translationY=100，代表view在其原始位置向下移动100
                    //仔细观察进入的fragment中view从远处过来，不断向下移动，最终停在原始位置
                    view.setTranslationX((mWidth-positionOffsetPixels)*tag.xIn);
                    view.setTranslationY((mWidth-positionOffsetPixels)*tag.yIn);
                }
            }
        }
        if (outFragment != null) {
            List<View> outViews = outFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(view.getId());
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    view.setTranslationX(0 - positionOffsetPixels * tag.xOut);
                    view.setTranslationY(0 - positionOffsetPixels * tag.yOut);
                }
            }
        }

    }
    private IParallaxView getPosition(int position){
        IParallaxView iParallaxView=null;
        if(position>=0 && position<=mViews.size()){
            iParallaxView=mViews.get(position);
        }
        return iParallaxView;
    }

    @Override
    public void onPageSelected(int position) {
        if (position == mViews.size() - 1) {
            ivWomen.setVisibility(View.INVISIBLE);
        } else {
            ivWomen.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Drawable background = ivWomen.getBackground();
        if(!(background instanceof AnimationDrawable)){
            return;
        }
        AnimationDrawable animationDrawable= (AnimationDrawable) background;
        switch (state){
            case ViewPager.SCROLL_STATE_DRAGGING:
                animationDrawable.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animationDrawable.stop();
                break;
            default:
                break;
        }
    }
}
