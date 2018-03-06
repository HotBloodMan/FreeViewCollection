package com.ljt.freeviewcollection.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/3/5.
 */

public class BezierViews extends View{
public static String TAG= BezierViews.class.getSimpleName();
    private  Paint mPaint;
    private PointF start,end,control;
    private int mCenterX,mCenterY;


    public BezierViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        start=new PointF(0,0);
        end=new PointF(0,0);
        control=new PointF(0,0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
         Log.d(TAG,TAG+" ----->>>w= "+w+" h= "+h+" oldw= "+oldw+" oldh= "+oldh);
         mCenterX=w/2;
        mCenterY=h/2;

         start.x=mCenterX-200;
         start.y=mCenterY;

         control.x=mCenterX;
         control.y=mCenterY+100;

         end.x=mCenterX+200;
         end.y=mCenterY-100;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x=event.getX()-200;
        control.y=event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(4);

        canvas.drawPoint(start.x,start.y,mPaint);
        canvas.drawPoint(end.x,end.y,mPaint);
        canvas.drawPoint(control.x,control.y,mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        canvas.drawLine(start.x,start.y,control.x,control.y,mPaint);
        canvas.drawLine(end.x,end.y,control.x,control.y,mPaint);

        Path path=new Path();
        path.moveTo(start.x,start.y);
        path.quadTo(control.x,control.y,end.x,end.y);

        canvas.drawPath(path,mPaint);

    }
}
