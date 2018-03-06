package com.ljt.freeviewcollection.viewframe;

import android.content.Context;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by ${JT.L} on 2018/2/27.
 */

public class DiscrollView extends ScrollView {

    public static String TAG= DiscrollView.class.getSimpleName();
    private DiscrollViewContent mContent;

    public DiscrollView(Context context) {
        super(context);
    }

    public DiscrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupFirstView();
    }

    private void setupFirstView() {
        View first = mContent.getChildAt(0);
        if(first!=null){
            first.getLayoutParams().height=getHeight();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new IllegalStateException("Discrollview must host one child.");
        }
        View content = getChildAt(0);
        if(!(content instanceof DiscrollViewContent)){
            throw new IllegalStateException("Discrollview must host a DiscrollViewContent.");
        }
        mContent= (DiscrollViewContent) content;
        if(mContent.getChildCount()<2){
            throw new IllegalStateException("Discrollview must have at least 2 children.");
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        onScrollChanged(t);
    }

    private void onScrollChanged(int top) {
        int scrollViewHeight = getHeight(); //父布局的高度
        int scrollViewBottom = getAbsoluteBottom(); //
        int scrollViewHalfHeight=scrollViewHeight/2;

        for(int index=1;index<mContent.getChildCount();index++){
            View child = mContent.getChildAt(index);
            if(!(child instanceof Discrollvable)){
                continue;
            }
            Discrollvable discrollvable= (Discrollvable) child;
            int discrollvableTop = child.getTop();
            int discrollvableHeight = child.getHeight();
            int discrollvableAbsoluteTop=discrollvableTop-top;

            if(scrollViewBottom-child.getBottom() < discrollvableHeight + scrollViewHalfHeight){
                if(discrollvableAbsoluteTop <=scrollViewHeight){
                    int visibleGap=scrollViewHeight-discrollvableAbsoluteTop;
                    discrollvable.onDiscrollve(clamp(visibleGap/(float)discrollvableHeight,0.0f,1.0f));
                }else{
                    discrollvable.onResetDiscrollve();
                }
            } else {
                if(discrollvableAbsoluteTop <= scrollViewHalfHeight){
                    int visibleGap=scrollViewHalfHeight -discrollvableAbsoluteTop;
                    discrollvable.onDiscrollve(clamp(visibleGap/(float)discrollvableHeight,0.0f,1.0f));
                }else{
                    discrollvable.onResetDiscrollve();
                }
            }
        }

    }
    private int getAbsoluteBottom(){
        View last = getChildAt(getChildCount() - 1);
        if(last==null){
            return 0;
        }
        return last.getBottom();
    }

    public static float clamp(float value,float max,float min){
        return Math.max(Math.min(value,min),max);
    }
}






























