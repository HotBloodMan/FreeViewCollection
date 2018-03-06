package com.ljt.freeviewcollection.paperfly;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/3/2.
 */

public class PaperFlyView extends View implements View.OnClickListener {

    public static String TAG= PaperFlyView.class.getSimpleName();
    private Bitmap flyBitmap;
    private float flyX,flyY;

    private float commandPointX,commandPointY;
    private float startPointX,startPointY;
    private float endPointX,endPointY;
    private ValueAnimator anim;


    public PaperFlyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.v4);
        Matrix m = new Matrix();
        m.setScale(0.125f,0.125f);
        flyBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,false);
        bitmap.recycle();
        flyX=540;
        flyY=540;
        //控制点
        commandPointX=540;
        commandPointY=540;
        //设置点击监听
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(flyBitmap,flyX,flyY,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flyX=2*flyBitmap.getWidth();
        flyY=h-3*flyBitmap.getHeight();
        //动画开始位置
        startPointX=flyX;
        startPointY=flyY;
        //动画结束位置
        endPointX=w/2-flyBitmap.getWidth();
        endPointY=3*flyBitmap.getHeight();
    }

    @Override
    public void onClick(View v) {
        //估值器
        BazierEvaluator bazierEvaluator = new BazierEvaluator(new PointF(commandPointX, commandPointY));
        //设置属性动画
        PointF startPointF = new PointF(startPointX, startPointY);
        PointF endPointF = new PointF(endPointX, endPointY);
        anim = ValueAnimator.ofObject(bazierEvaluator,startPointF,endPointF);
        anim.setDuration(1000);
        //在动画过程中，更新绘制的位置，位置的轨迹就是贝塞尔曲线
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                flyX=point.x;
                flyY=point.y;
                 Log.d(TAG,TAG+" ----->>> point-->= "+point.x+" point.y= "+point.y);
                invalidate();
            }
        });
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(null !=anim && anim.isRunning()){
            anim.cancel();
        }
    }
}
