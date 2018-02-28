package com.ljt.freeviewcollection.viewframe;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ljt.freeviewcollection.R;


/**
 * Created by ${JT.L} on 2018/2/27.
 */

public class DiscrollViewContent extends LinearLayout{
    public DiscrollViewContent(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public DiscrollViewContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public DiscrollViewContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyLayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new MyLayoutParams(lp.width,lp.height);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(asDiscrollvable(child, (MyLayoutParams) params),index,params);
    }

    private  View  asDiscrollvable(View child, MyLayoutParams lp){
        if(!isDiscrollvable(lp)){
            System.out.println("isDiscrollvable false");
            return child;
        }
        DiscrollvableView discrollvableView = new DiscrollvableView(getContext());
        discrollvableView.setmDiscrollveAlpha(lp.mDiscrollveAlpha);
        discrollvableView.setmDiscrollveTranslation(lp.mDiscrollveTranslation);
        discrollvableView.setmDiscrollveScaleX(lp.mDiscrollveScaleX);
        discrollvableView.setmDiscrollveScaleY(lp.mDiscrollveScaleY);
        discrollvableView.setmDiscrollveThreshold(lp.mDiscrollveThreshold);
        discrollvableView.setmDiscrollveFromBgColor(lp.mDisrollveFromBgColor);
        discrollvableView.setmDiscrollveToBgColor(lp.mDiscrollveToBgColor);
        discrollvableView.addView(child);
        return discrollvableView;
    }
    private boolean isDiscrollvable(MyLayoutParams lp){
        return lp.mDiscrollveAlpha || lp.mDiscrollveTranslation !=-1 ||
                lp.mDiscrollveScaleX || lp.mDiscrollveScaleY ||
                (lp.mDisrollveFromBgColor !=-1 && lp.mDiscrollveToBgColor !=-1);
    }

    public static class MyLayoutParams extends LinearLayout.LayoutParams{

        private int mDisrollveFromBgColor;
        private int mDiscrollveToBgColor;
        private float mDiscrollveThreshold;
        private boolean mDiscrollveAlpha;
        private boolean mDiscrollveScaleX;
        private boolean mDiscrollveScaleY;
        private int mDiscrollveTranslation;

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DiscrollView_MyLayoutParams);
            try {
                mDiscrollveAlpha=a.getBoolean(R.styleable.DiscrollView_MyLayoutParams_discrollve_alpha,false);
                mDiscrollveScaleX=a.getBoolean(R.styleable
                        .DiscrollView_MyLayoutParams_discrollve_scaleX,false);
                mDiscrollveScaleY = a.getBoolean(
                        R.styleable.DiscrollView_MyLayoutParams_discrollve_scaleY, false);
                mDiscrollveTranslation = a.getInt(
                        R.styleable.DiscrollView_MyLayoutParams_discrollve_translation, -1);
                mDiscrollveThreshold = a.getFloat(
                        R.styleable.DiscrollView_MyLayoutParams_discrollve_threshold, 0.0f);
                mDisrollveFromBgColor = a.getColor(
                        R.styleable.DiscrollView_MyLayoutParams_discrollve_fromBgColor, -1);
                mDiscrollveToBgColor = a.getColor(
                        R.styleable.DiscrollView_MyLayoutParams_discrollve_toBgColor, -1);
            }finally {
                a.recycle();
            }
        }
        public MyLayoutParams(int w,int h){
            super(w,h);
        }
    }
}
