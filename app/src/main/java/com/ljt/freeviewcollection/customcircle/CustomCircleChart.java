package com.ljt.freeviewcollection.customcircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/2/6.
 */

public class CustomCircleChart extends View {
    public static String TAG= CustomCircleChart.class.getSimpleName();
    //需要的数据
    private String[] sTexts={"1年级","2年级","3年级","4年级","5年级","6年级"};
    private int[] strPrercent={10,24,18,41,2,5};
    private float mRadius=300;
    private float mStrokeWidth=40;
    //文字大小
    private int textSize=20;
    //画笔 圆
    private Paint circlePaint;
    //文字的画笔
    private Paint textPaint;
    //标注的画笔
    private Paint labelPaint;
    //颜色
    private int[] mColor = new int[]{0xFFF06292, 0xFF9575CD, 0xFFE57373, 0xFF4FC3F7, 0xFFFFF176, 0xFF81C784};
    //文字颜色
    private int textColor=0xFF000000;

    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    public CustomCircleChart(Context context) {
        this(context,null);
    }

    public CustomCircleChart(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CustomCircleChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initPaint() {
        //边框画笔
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(mStrokeWidth);
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
        //标注画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2-mRadius/2,mHeight/2-mRadius/2);
        initPaint();
        drawCycle(canvas);
        drawTextAndLabel(canvas);
        invalidate();
    }

    //画圆环
    private void drawCycle(Canvas canvas){
        float startPercent=0;
        float sweepPercent=0;
        for(int i=0;i<strPrercent.length;i++){
            circlePaint.setColor(mColor[i]);
            startPercent=sweepPercent+startPercent-1;
            if(i<3){
                sweepPercent= (float) ((strPrercent[i]+2.5)*360/100);
            }else{
                sweepPercent=(strPrercent[i])*360/100;

            }
            canvas.drawArc(new RectF(0,0,mRadius,mRadius),startPercent,sweepPercent,false,circlePaint);
        }
    }

    //画文字和标注
    private void drawTextAndLabel(Canvas canvas){
        for(int i=0;i<strPrercent.length;i++){
            canvas.drawText(sTexts[i],mRadius+60,i*40,textPaint);

            labelPaint.setColor(mColor[i]);
            canvas.drawCircle(mRadius+40,i*40-5,10,labelPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
         Log.d(TAG,TAG+" ----->>>onSizeChanged mWidth="+mWidth+" mHeight="+mHeight);
    }
}
