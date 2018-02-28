package com.ljt.freeviewcollection.viewframe;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/2/27/027.
 */

public class DiscrollvableView extends FrameLayout  implements  Discrollvable{

    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    @SuppressLint("NewApi")
    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    private float mDiscrollveThreshold;
    private int mDiscrollveFromBgColor;
    private int mDiscrollveToBgColor;
    private boolean mDiscrollveAlpha;
    private int mDiscrollveTranslation;
    private boolean mDiscrollveScaleX;
    private boolean mDiscrollveScaleY;

    private int mWidth;
    private int mHeight;

    public DiscrollvableView(@NonNull Context context) {
        super(context);
    }

    public DiscrollvableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollvableView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    public void setmDiscrollveTranslation(int mDiscrollveTranslation) {
        this.mDiscrollveTranslation = mDiscrollveTranslation;
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM) && isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)){
            throw new IllegalArgumentException("cannot translate from bottom and top");
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT) && isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT))
        {
            throw new IllegalArgumentException("cannot translate from left and right");
        }
    }
    private boolean isDiscrollveTranslationFrom(int translationMask){
        if(mDiscrollveTranslation==-1){
            return false;
        }
        return (mDiscrollveTranslation & translationMask)==translationMask;
    }

    public void setmDiscrollveThreshold(float mDiscrollveThreshold) {
        if(mDiscrollveThreshold<0.0f || mDiscrollveThreshold>1.0f){
            throw new IllegalArgumentException("threshold must be >= 0.0f and <= 1.0f");
        }
        this.mDiscrollveThreshold = mDiscrollveThreshold;
    }

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }
    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }
    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }
    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }


    @Override
    public void onDiscrollve(float ration) {
      if(ration>=mDiscrollveThreshold){
          ration=withThreshold(ration);
          float ratioInverse=1-ration;
          if(mDiscrollveAlpha){
              setAlpha(ration);
          }
          if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)){
              setTranslationY(mHeight* ratioInverse);
          }
          if(isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)){
              setTranslationY(-mHeight*ratioInverse);
          }
          if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)){
              setTranslationX(-mWidth * ratioInverse);
          }
          if(isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)){
              setTranslationX(mWidth*ratioInverse);
          }
          if(mDiscrollveScaleX){
              setScaleX(ration);
          }
          if(mDiscrollveScaleY){
              setScaleY(ration);
          }
          if(mDiscrollveFromBgColor !=-1 && mDiscrollveToBgColor !=-1){
              setBackgroundColor((Integer) sArgbEvaluator.evaluate(ration,mDiscrollveFromBgColor,
                      mDiscrollveToBgColor));
          }

      }
    }
    private float withThreshold(float ratio){
        return (ratio-mDiscrollveThreshold)/(1.0f-mDiscrollveThreshold);
    }

    @Override
    public void onResetDiscrollve() {
        if(mDiscrollveAlpha){
            setAlpha(0.0f);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight);
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);
        }
        if(isDiscrollveTranslationFrom(TRANSLATION_FROM_LEFT)){
            setTranslationX(-mWidth);
        }
        if (isDiscrollveTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);
        }
       if(mDiscrollveScaleX){
           setScaleX(0.0f);
       }
        if (mDiscrollveScaleY) {
            setScaleY(0.0f);
        }
        if(mDiscrollveFromBgColor !=-1 && mDiscrollveToBgColor !=-1){
            setBackgroundColor(mDiscrollveFromBgColor);
        }
    }
}
