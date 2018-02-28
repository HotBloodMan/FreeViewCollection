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
}






























