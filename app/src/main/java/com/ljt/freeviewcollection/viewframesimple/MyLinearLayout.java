package com.ljt.freeviewcollection.viewframesimple;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ljt.freeviewcollection.R;
import com.ljt.freeviewcollection.viewframe.DiscrollViewContent;

/**
 * Created by ${JT.L} on 2018/3/6.
 */

public class MyLinearLayout extends LinearLayout{

    private Context mContext;
    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ScrollLayoutParams(mContext,attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(((ScrollLayoutParams)params).hasAnimal()){
            MyAnimalView myAnimalView = new MyAnimalView(mContext);

            myAnimalView.setAlphaFrom(((ScrollLayoutParams) params).alphaFrom);
            myAnimalView.setAlphaTo(((ScrollLayoutParams) params).alphaTo);

            myAnimalView.setFromBottom(((ScrollLayoutParams) params).translate);
            myAnimalView.setFromLeft(((ScrollLayoutParams) params).translate);

            myAnimalView.setScaleX(((ScrollLayoutParams) params).isScaleX);

            myAnimalView.addView(child);

            super.addView(myAnimalView,index,params);
        }else {
            super.addView(child, index, params);
        }
    }

    public class ScrollLayoutParams extends  LinearLayout.LayoutParams{

        float alphaFrom;
        float alphaTo;
        int translate;
        boolean isScaleX;

        public ScrollLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.AnimalFrame);
            alphaFrom=a.getFloat(R.styleable.AnimalFrame_alphaFrom,-1);
            alphaTo=a.getFloat(R.styleable.AnimalFrame_alphaTo,-1);
            translate=a.getInt(R.styleable.AnimalFrame_translate,-1);
            isScaleX=a.getBoolean(R.styleable.AnimalFrame_scaleX,false);
            a.recycle();
        }
        public boolean hasAnimal() {
            return alphaFrom != -1 && alphaTo != -1
                    || translate != -1
                    || isScaleX;
        }
    }
}











