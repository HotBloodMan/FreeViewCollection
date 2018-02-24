package com.ljt.freeviewcollection.artsearch.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/2/24.
 */

public class CircleViewPadding extends View{

    private int mColor=Color.RED;
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleViewPadding(Context context) {
        super(context);
        init();
    }

    public CircleViewPadding(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleViewPadding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleViewPadding);
        mColor = a.getColor(R.styleable.CircleViewPadding_circleColor, Color.RED);
        a.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(modeWidth==MeasureSpec.AT_MOST && modeHeight==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(modeWidth==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,sizeHeight);
        }else if(modeHeight==MeasureSpec.AT_MOST){
            setMeasuredDimension(sizeWidth,200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth()-paddingLeft-paddingRight;
        int height = getHeight()-paddingTop-paddingBottom;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width/2+paddingLeft,height/2+paddingTop,radius,mPaint);

    }
}
