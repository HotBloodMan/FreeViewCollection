package com.ljt.freeviewcollection.rote3dview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/2/8.
 */

public class Rote3DView  extends ViewGroup{

    private int mCurScreen=1;
    //滑动速度
    private static final  int SNAP_VELOCITY=500;
    private VelocityTracker mVelocTracker;
    private int mWidth;
    private Scroller mScroller;
    private Camera mCamera;
    private Matrix mMatrix;
    // 旋转的角度，可以进行修改来观察效果
    private float angle = 90;



    public Rote3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
        mCamera=new Camera();
        mMatrix=new Matrix();
        initScreens();
    }

    private void initScreens() {
        ViewGroup.LayoutParams p =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        for(int i=0;i<3;i++){
            this.addView(new ImageView(this.getContext()),i,p);
        }
        ((ImageView)this.getChildAt(0)).setImageResource(R.drawable.page11);
        ((ImageView)this.getChildAt(1)).setImageResource(R.drawable.page2);
        ((ImageView)this.getChildAt(2)).setImageResource(R.drawable.page3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(widthMode!=MeasureSpec.EXACTLY){
            throw new IllegalStateException("仅支持精确尺寸");
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode!=MeasureSpec.EXACTLY){
            throw new IllegalStateException("仅支持精确尺寸");
        }
        int count = getChildCount();
        for(int i=0;i<count;i++){
            getChildAt(i).measure(widthMeasureSpec,heightMeasureSpec);
        }
        scrollTo(mCurScreen*width,0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft=0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            if(childView.getVisibility()!=View.GONE){
                int childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft,0,childLeft+childWidth,childView.getMeasuredHeight());
                childLeft+=childWidth;
            }
        }
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    private float mDownX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocTracker==null){
            mVelocTracker=VelocityTracker.obtain();
        }
        //将当前的触摸事件传递给VelocityTracker对象
        mVelocTracker.addMovement(event);
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mDownX=x;
                break;
            case MotionEvent.ACTION_MOVE:
                int disX= (int) (mDownX-x);
                mDownX=x;
                scrollBy(disX,0);
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker=mVelocTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int  velocityX = (int) velocityTracker.getXVelocity();
                if(velocityX>SNAP_VELOCITY&& mCurScreen<getChildCount()-1){
                    snapToScreen(mCurScreen+1);
                }else{
                    snapToDestination();
                }
                if(mVelocTracker!=null){
                    mVelocTracker.recycle();
                    mVelocTracker=null;
                }
                break;
        }
        return true;
    }

    public void snapToScreen(int whichScreen){
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        setMWidth();
        int scrollX = getScrollX();
        int startWidth = whichScreen * mWidth;
        if(scrollX != startWidth){
            int delta = 0;
            int startX = 0;
            if(whichScreen > mCurScreen){
                setPre();
                delta = startWidth - scrollX;
                startX = mWidth - startWidth + scrollX;
            }else if(whichScreen < mCurScreen){
                setNext();
                delta = -scrollX;
                startX = scrollX + mWidth;
            }else{
                startX = scrollX;
                delta = startWidth - scrollX;
            }
            mScroller.startScroll(startX, 0, delta, 0, Math.abs(delta) * 2);
            invalidate();
        }
    }
    private void setPre(){
        int count = this.getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, count - 1);
    }
    private void setNext(){
        int count = this.getChildCount();
        View view = getChildAt(count - 1);
        removeViewAt(count - 1);
        addView(view, 0);
    }

    public void snapToDestination(){
        setMWidth();
        int destScreen = (getScrollX() + mWidth / 2) / mWidth;
        snapToScreen(destScreen);
    }
    private void setMWidth(){
        if(mWidth==0){
            mWidth=getWidth();
        }
    }
        //实现立体效果 如果不加 是平移的效果
        /*
     * 当进行View滑动时，会导致当前的View无效，该函数的作用是对View进行重新绘制 调用drawScreen函数
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        final long drawingTime = getDrawingTime();
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            drawScreen(canvas, i, drawingTime);
        }
    }

    public void drawScreen(Canvas canvas, int screen, long drawingTime) {
        // 得到当前子View的宽度
        final int width = getWidth();
        final int scrollWidth = screen * width;
        final int scrollX = this.getScrollX();
        // 偏移量不足的时
        if (scrollWidth > scrollX + width || scrollWidth + width < scrollX) {
            return;
        }
        final View child = getChildAt(screen);
        final int faceIndex = screen;
        final float currentDegree = getScrollX() * (angle / getMeasuredWidth());
        final float faceDegree = currentDegree - faceIndex * angle;
        if (faceDegree > 90 || faceDegree < -90) {
            return;
        }
        final float centerX = (scrollWidth < scrollX) ? scrollWidth + width
                : scrollWidth;
        final float centerY = getHeight() / 2;
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;
        canvas.save();
        camera.save();
        camera.rotateY(-faceDegree);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.concat(matrix);
        drawChild(canvas, child, drawingTime);
        canvas.restore();
    }

}
