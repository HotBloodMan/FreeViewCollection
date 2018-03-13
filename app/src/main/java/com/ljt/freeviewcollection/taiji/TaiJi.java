package com.ljt.freeviewcollection.taiji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/3/12.
 */

public class TaiJi extends View{

    private Paint whitePaint;   //白色画笔
    private Paint blackPaing;   //黑色画笔
    PointF p=new PointF();

    public TaiJi(Context context) {
        super(context);
        initPaints();
    }

    public TaiJi(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }
    //初始化画笔函数
    private void initPaints() {
        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);

        blackPaing = new Paint();
        blackPaing.setAntiAlias(true);
        blackPaing.setColor(Color.BLACK);
    }
    private float degrees = 0;          //旋转角度
    public void setRotate(float degrees) {
        this.degrees = degrees;
        invalidate();                   //重绘界面
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        int width = canvas.getWidth();                          //画布宽度
        int height = canvas.getHeight();                        //画布高度
        Point centerPoint = new Point(width / 2, height / 2);   //画布中心点
        canvas.rotate(degrees,centerPoint.x,centerPoint.y);


        canvas.translate(centerPoint.x, centerPoint.y);         //将画布移动到中心
        int radius=Math.min(width,height)/2-100;
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rectF,90,180,true,blackPaing);
        canvas.drawArc(rectF,-90,180,true,whitePaint);

        int smallRadius=radius/2;
        canvas.drawCircle(0,-smallRadius,smallRadius,blackPaing);
        canvas.drawCircle(0,smallRadius,smallRadius,whitePaint);

        //绘制鱼眼（两个更小的圆）
        canvas.drawCircle(0, -smallRadius, smallRadius / 4, whitePaint);
        canvas.drawCircle(0, smallRadius, smallRadius / 4, blackPaing);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    }
}
