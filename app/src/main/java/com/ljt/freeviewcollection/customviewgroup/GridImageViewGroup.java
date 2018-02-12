package com.ljt.freeviewcollection.customviewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/2/12.
 * 参考：https://www.cnblogs.com/whoislcj/p/5802899.html
 */

public class GridImageViewGroup extends ViewGroup {

    private int childVerticalSpace=0;
    private int childHorizontalSpace=0;
    private int columnNum=3;
    private int childWidth=0;
    private int childHeight=0;

    public GridImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GridImageViewGroup);
        if(attributes !=null){
            childVerticalSpace=attributes.getDimensionPixelSize(R.styleable.GridImageViewGroup_childVerticalSpce,0);
            childHorizontalSpace=attributes.getDimensionPixelSize(R.styleable.GridImageViewGroup_childHorizontalSpace,0);
            columnNum=attributes.getInt(R.styleable.GridImageViewGroup_columnNum,3);
            attributes.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rw=MeasureSpec.getSize(widthMeasureSpec);
        int rh=MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if(childCount>0){
            childWidth=(rw-(columnNum-1)*childHorizontalSpace)/columnNum;

            childHeight=childWidth;
            int vw=rw;
            if(childCount<columnNum){
                vw=childCount*(childHeight +childVerticalSpace);
            }
            int rowCount = childCount / columnNum + (childCount % columnNum != 0 ? 1 : 0);

            int vh = rowCount * childHeight + (rowCount > 0 ? rowCount - 1 : 0) * childVerticalSpace;

            setMeasuredDimension(vw, vh);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left=0;
        int top=0;
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            left = (i % columnNum) * (childWidth + childHorizontalSpace);
            top = (i / columnNum) * (childHeight + childVerticalSpace);
            child.layout(left, top, left + childWidth, top + childHeight);
        }
    }
}
