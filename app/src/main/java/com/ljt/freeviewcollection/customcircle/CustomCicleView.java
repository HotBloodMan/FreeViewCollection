package com.ljt.freeviewcollection.customcircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/2/6.
 */

public class CustomCicleView extends View implements Runnable{

    private Context mContext;
    private Paint paint;

    private int radius=30;

    public CustomCicleView(Context context) {
        this(context,null);
    }

    public CustomCicleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomCicleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initPaint();
    }
    private void initPaint() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300,300,radius,paint);
    }

    @Override
    public void run() {
        while(true){
            try {
                if(radius<=200){
                    radius+=10;
                    //刷新view
                    postInvalidate();
                }else{
                    radius=0;
                }
                Thread.sleep(40);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
