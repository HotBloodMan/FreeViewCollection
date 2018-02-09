package com.ljt.freeviewcollection.bounceloadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by ${JT.L} on 2018/2/9.
 */

public class BounceLoadingView extends View{

    private static final String TAG = BounceLoadingView.class.getSimpleName();
    private static final int SHADOW_COLOR= Color.LTGRAY;

    //图片每次跳起落下的时长
    private static final int DEFAULT_DURATION=800;

    private int mShadowColor = SHADOW_COLOR;

    private int mDuration = DEFAULT_DURATION;

    //画笔
    private Paint mBitmapPaint;
    private Paint mShadowPaint;

    private ArrayList<Bitmap> mBitmapList;
    private Bitmap mCurrentBitmap;
    private int mCurrentIndex;

    private float mPercent;

    private float mHalfShadowHeight;
    private float mHalfShadowWidth;
    private float mTopMargin;

    private Matrix matrix;
    private RectF mShdowRectF;
    private ValueAnimator animator;
    private RectF mShadowRectF;



    public BounceLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceLoadingView(Context context) {
        this(context, null);
    }

    public BounceLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BounceLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void setup() {

        mPercent = 0f;
        mCurrentIndex = 0;
        matrix = new Matrix();
        mShadowRectF = new RectF();
        mBitmapList = new ArrayList<>();

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setColor(mShadowColor);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,2*width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHalfShadowWidth=Math.max(16,getWidth()/2f*mPercent);
        mHalfShadowHeight=Math.max(8,getHeight()/8f*mPercent);

        mShadowRectF.set(getWidth()/2f-mHalfShadowWidth,getHeight()*7/8f-mHalfShadowHeight
        ,getWidth()/2f+mHalfShadowWidth,getHeight()*7 /8f+mHalfShadowHeight);
        canvas.drawOval(mShadowRectF,mShadowPaint);

        if(mCurrentBitmap!=null){
            canvas.save();
            mTopMargin = (getHeight() * 0.9f - mCurrentBitmap.getHeight()) * mPercent;
            canvas.translate(getWidth() / 2f - mCurrentBitmap.getWidth() / 2f, mTopMargin);
            canvas.drawBitmap(mCurrentBitmap, matrix, mBitmapPaint);
            canvas.restore();
        }
    }

    public void setDuration(int duration){
        this.mDuration=duration;
    }
    public void start(){
        stop();
        if(animator==null){
            animator=ValueAnimator.ofFloat(0f,1f,0f);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    if(value!=mPercent){
                        mPercent=value;
                        postInvalidate();
                    }
                }
            });
        }
    }

    public void stop(){
        if(animator != null){
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

}
