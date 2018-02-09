package com.ljt.freeviewcollection.rote3dview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/2/9.
 * 参考：http://blog.csdn.net/zhangke3016/article/details/52093776
 */

public class CameraTestView extends View{

    private Camera camera;
    private Matrix matrix;
    private Paint paint;
    public CameraTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        camera=new Camera();
        matrix=new Matrix();
        setBackgroundColor(Color.parseColor("#3f51b5"));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ff4081"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        camera.save();
        camera.rotateX(60);
        camera.rotateY(60);
        camera.rotateZ(60);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-getWidth()/2, -getHeight()/2);
        matrix.postTranslate(getWidth()/2, getHeight()/2);
//        canvas.concat(matrix);
//        canvas.drawCircle(60,60,60,paint);
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(), R.drawable.page2,option);
        option.inSampleSize = calculateInSampleSize(option, getWidth()/2, getHeight()/2);
        option.inJustDecodeBounds = false;
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.page2,option), matrix, paint);
    }
    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
