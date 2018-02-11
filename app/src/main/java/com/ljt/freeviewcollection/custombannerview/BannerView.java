package com.ljt.freeviewcollection.custombannerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/2/11.
 * 滚动广告条
 */

public class BannerView extends View{

    private Paint mPaint;
    private float x,startY,endY,nextStartY,secondY;
    private float firstY;

    //整个View的宽高是以第一个为标准的，所以第二句话长度必须小于第一句话
    private String[] text = {"今日特卖：毛衣3.3折>>>", "公告：全场半价>>>"};
    private float textWidth,textHeight;

    //滚动速度
    private float speech=0;
    private static final int CHANGE_SPEECH = 0x01;
    //是否已经在滚动
    private boolean isScroll = false;

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
        //测量文字的宽高，以第一句话为标准
        Rect rect = new Rect();
        mPaint.getTextBounds(text[0], 0, text[0].length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        //文字开始的x,y坐标
        //由于文字是以基准线为基线的，文字底部会突出一点，所以向上收5px
        x = getX() + getPaddingLeft();
        startY = getTop() + textHeight + getPaddingTop() - 5;
        //文字结束的x,y坐标
        endY = startY + textHeight + getPaddingBottom();
        //下一个文字滚动开始的y坐标
        //由于文字是以基准线为基线的，文字底部会突出一点，所以向上收5px
        nextStartY = getTop() - 5;
        //记录开始的坐标
        firstY = startY;
        secondY = nextStartY;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureWidth(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
    private int measureHeight(int heightMeasureSpec){
        int result=0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            result= (int) (getPaddingTop()+getPaddingBottom()+textHeight);
            if(mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGE_SPEECH:
                    speech=1f;
                    break;
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //启动滚动
        if (!isScroll) {
            mHandler.sendEmptyMessageDelayed(CHANGE_SPEECH, 2000);
            isScroll = true;
        }
        canvas.drawText(text[0], x, startY, mPaint);
        canvas.drawText(text[1], x, nextStartY, mPaint);
        startY+=speech;
        nextStartY+=speech;

        //超出View的控件时
        if(startY>endY || nextStartY >endY){
            if(startY>endY){
                //
                startY=secondY;
                nextStartY=firstY;
            }else if(nextStartY > endY){
                //第二次滚动过后交换值
                startY=firstY;
                nextStartY=secondY;
            }
            speech=0;
            isScroll=false;
        }
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //点击事件
                if(listener!=null){
                    if(startY>=firstY && nextStartY <firstY){
                        listener.touchListener(text[0]);
                    }else if(nextStartY >= firstY && startY <firstY){
                        listener.touchListener(text[1]);
                    }
                }
                break;
        }
        return true;
    }

    public onTouchListener listener;
    public interface  onTouchListener{
        void touchListener(String s);
    }
    public void setListener(onTouchListener listener){
        this.listener=listener;
    }

}
