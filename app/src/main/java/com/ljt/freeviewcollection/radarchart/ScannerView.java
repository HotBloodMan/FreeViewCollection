package com.ljt.freeviewcollection.radarchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ${JT.L} on 2018/3/12.
 */

public class ScannerView extends View{
    public static String TAG= ScannerView.class.getSimpleName();

    private int count=6;
    private float angle=(float)(Math.PI*2/count);
    private float radius;
    private int centerX;
    private int centerY;
    private String[] titles={"a","b","c","d","e","f"};
    private double[] data = {100,60,60,60,100,50,10,20}; //各维度分值
    private float maxValue = 100;             //数据最大值
    private Paint mainPaint;                //雷达区画笔
    private Paint valuePaint;               //数据区画笔
    private Paint textPaint;                //文本画笔

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mainPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setStrokeWidth(2);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setColor(Color.BLUE);
        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(24);
        textPaint.setColor(Color.RED);
        textPaint.setStrokeWidth(1);
        textPaint.setStyle(Paint.Style.STROKE);

        valuePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setStrokeWidth(2);
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setColor(Color.BLACK);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius=Math.min(h,w)/2*0.9f;
        centerX=w/2;
        centerY=h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegin(canvas);

    }
    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r=radius/(count-1);
        for(int i=1;i<count;i++){
            float curR=r*i;
            path.reset();
            for(int j=0;j<count;j++){
                if(j==0){
                    path.moveTo(centerX+curR,centerY);
                }else{
                    float x= (float) (centerX+curR*Math.cos(angle*j));
                    float y= (float) (centerY+curR*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();
            canvas.drawPath(path,mainPaint);
        }
    }

    private void drawLines(Canvas canvas){
        Path path = new Path();
        for(int i=0;i<count;i++){
            path.reset();
            path.moveTo(centerX,centerY);
            float x= (float) (centerX+radius*Math.cos(angle*i));
            float y = (float) (centerY+radius*Math.sin(angle*i));
            Log.d(TAG,TAG+" ----->>> x= "+x+ " y= "+y);
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if(angle*i>=0 && angle*i<=Math.PI/2){
                ////第4象限
                canvas.drawText(titles[i],x,y,textPaint);
            }else if(angle*i>=Math.PI/2&&angle*i<=Math.PI){
                //第3象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y+dis,textPaint);
            }
            else if(angle*i>Math.PI&&angle*i<=Math.PI*3/2){
                //第2象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }else if(angle*i>=Math.PI*3/2&&angle*i<2*Math.PI){
                //第1象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }
        }
    }
    private void drawRegin(Canvas canvas){
        Path path = new Path();
        valuePaint.setAlpha(255);
        for(int i=0;i<6;i++){
            double percent = data[i] / maxValue;
            float x=(float)(centerX+radius*Math.cos(angle*i)*percent);
            float y=(float)(centerY+radius*Math.sin(angle*i)*percent);
            if(i==0){
                path.moveTo(x,centerY);
            }
//            else if(i==4){
////                float x1=(float)(centerX+radius*Math.cos(angle*(i-1))*percent);
////                float y1=(float)(centerY+radius*Math.sin(angle*(i-1))*percent);
////                PathMeasure pathMeasure = new PathMeasure();
////                float length = pathMeasure.getLength();
////                pathMeasure.getSegment(x1,y1,path,false);
////                canvas.drawPath(path,valuePaint);
//            }
            else{
                path.lineTo(x,y);
            }
            //
            canvas.drawCircle(x,y,10,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,valuePaint);
        valuePaint.setAlpha(127);

        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path,valuePaint);
    }

}
