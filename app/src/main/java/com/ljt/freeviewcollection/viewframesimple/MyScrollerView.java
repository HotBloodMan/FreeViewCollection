package com.ljt.freeviewcollection.viewframesimple;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.ljt.freeviewcollection.viewframe.DiscrollViewContent;

/**
 * Created by ${JT.L} on 2018/3/6.
 */

public class MyScrollerView extends ScrollView {
    public static String TAG= MyScrollerView.class.getSimpleName();

    private int mHeight;
    public MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
         Log.d(TAG,TAG+" ----->>> w="+w+" h= "+h);
       mHeight=h;
    }

//    private void setupFirstView() {
//        View first = mContent.getChildAt(0);
//        if(first!=null){
////            first.getLayoutParams().height=getHeight();
//            mHeight=first.getLayoutParams().height;
//        }
//    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//         Log.d(TAG,TAG+" ----->>> "+"onFinishInflate()");
//        View content = getChildAt(0);
//        if(!(content instanceof MyLinearLayout)){
//            throw new IllegalStateException("Discrollview must host a DiscrollViewContent.");
//        }
//        mContent= (MyLinearLayout) content;
//        if(mContent.getChildCount()<2){
//            throw new IllegalStateException("Discrollview must have at least 2 children.");
//        }
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        ViewGroup vp = (ViewGroup) getChildAt(0);
        MyLinearLayout vp = (MyLinearLayout) getChildAt(0);
        for(int i=1;i<vp.getChildCount();i++){
            if(vp.getChildAt(i) instanceof  MyAnimalView){
                MyAnimalView itemView = (MyAnimalView) vp.getChildAt(i);

                int topView = itemView.getTop();
                int scrollY=t;
                int toScreenTopDis=topView-scrollY;

                int visiableHeight=mHeight-toScreenTopDis;
                if(visiableHeight>0 && visiableHeight<=itemView.getHeight()){
                    float rate = visiableHeight / (float) itemView.getHeight();
                    itemView.excuteAnimal(rate);
                }
            }
        }
    }
}
