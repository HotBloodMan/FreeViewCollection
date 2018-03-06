package com.ljt.freeviewcollection.paperfly;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by ${JT.L} on 2018/3/2.
 */

public class BazierEvaluator implements TypeEvaluator<PointF>{

    private PointF mPointF;

    public BazierEvaluator(PointF mPointF) {
        this.mPointF = mPointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return null;
    }
/*
*
*@param t  曲线长度比例
* @param p0 起始点
* @param p1 控制点
*@param p2 终止点
* @return t对应的点
* */

    private PointF calculateBezierPointForQuaratic(float t,PointF p0,PointF p1,PointF p2){
        PointF point = new PointF();
        float temp=1-t;
        point.x=temp*temp*p0.x+2*t*temp*p1.x+t*t*p2.x;
        point.y=temp*temp*p0.y+2*t*temp*p1.y+t*t*p2.y;
        return point;
    }
}
