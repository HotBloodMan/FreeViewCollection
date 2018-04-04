package com.ljt.freeviewcollection.zoomimageview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 *
 */

@SuppressLint("AppCompatCustomView")
public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener {
   public static String TAG= ZoomImageView.class.getSimpleName();
    private boolean mOnce = false;
    /**
     * 缩放起始值
     */
    private float mInitScale;
    /**
     * 手指放大缩小的值
     */
    private float mMidScale;
    /**
     * 缩放最大值
     */
    private float mMaxScale;

    /**
     * 控制缩放平移的矩阵
     */
    private Matrix mScaleMatrix;
    /**
     * 检测手指缩放
     */
    private ScaleGestureDetector mScaleGestureDetector;
    /**
     * 图片高度
     */
    private float dh;
    /**
     * 图片宽度
     */
    private float dw;
    /**
     * 控件宽度
     */
    private float width;
    /**
     * 控件高度
     */
    private float height;


    //===================自由移动begin=====================
    /**
     * 记录上一次多点触控的数量
     */

    private float mLastX;
    private float mLastY;
    private int mScaledTouchSlop;
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    //===================自由移动end=====================

    //===================双击缩放begin=====================
    private boolean isScaling;

    //===================双击缩放end=====================

    private GestureDetector mGestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
         Log.d(TAG,TAG+" ----->>>init() ");
        setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isScaling)
                    return true;
                final float scale = getScale();
                final float x = e.getX();
                final float y = e.getY();
                final ValueAnimator animator;
                System.out.println("--------" + x + "--------" + y);

                if (scale < mMidScale) {
                    animator = ValueAnimator.ofFloat(scale, mMidScale);

//                    mScaleMatrix.postScale(mMidScale/getScale(), mMidScale/getScale(), x, y);
//                    checkBorderAndCenterWhenScale();
//                    setImageMatrix(mScaleMatrix);
                } else if (scale >= mMidScale && scale < mMaxScale) {
                    animator = ValueAnimator.ofFloat(scale, mMaxScale);

                } else {
                    animator = ValueAnimator.ofFloat(scale, mInitScale);

                }
                isScaling = true;
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float scaleValue = (float) animation.getAnimatedValue();
//                        mScaleMatrix.reset();
                        setScaleDefault();
                        System.out.println("=========" + x + "=======" + y);
                        mScaleMatrix.postScale(scaleValue, scaleValue, x, y);
                        checkBorderAndCenterWhenScale();
                        setImageMatrix(mScaleMatrix);
                    }
                });
                animator.setDuration(200);
                animator.start();
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isScaling = false;
                        animator.removeAllUpdateListeners();

                    }

                });
                return true;
            }
        });
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
         Log.d(TAG,TAG+" ----->>>onAttachedToWindow() ");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
         Log.d(TAG,TAG+" ----->>>onDetachedFromWindow() ");
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


    /**
     * 获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        Log.d(TAG,TAG+" ----->>>onGlobalLayout() ");
        if (!mOnce) {
            width = getWidth();
            height = getHeight();
            Drawable d = getDrawable();
            if (d == null) return;
            dw = d.getIntrinsicWidth();
            dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }
            if (dw < width && dh > height) {
                scale = height * 1.0f / dh;
            }
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            if (dw < width && dh < height) {
                scale = Math.max(width * 1.0f / dw, height * 1.0f / dh);
            }

            /**
             * 得到初始化缩放比例
             */
            mInitScale = scale;
            mMidScale = 2 * scale;
            mMaxScale = 4 * scale;

            // 将图片移动至控件中心
            float dx = width / 2 - dw / 2;
            float dy = height / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,TAG+" ----->>>onTouchEvent ");
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);

        // 多点触控焦点
        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();

        if (pointerCount == 1) {
            isCanDrag = true;
            x = event.getX();
            y = event.getY();
        } else {
            isCanDrag = false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                break;
            case MotionEvent.ACTION_MOVE:
                float dx = 0;
                float dy = 0;
                if (mLastX != 0 && mLastY != 0) {// 如果不加此判断，手指第一次放下的时候mLastX为0，会导致放下的瞬间图片发生位移
                    dx = x - mLastX;
                    dy = y - mLastY;
                }
                if (isCanDrag && isMoveAction(dx, dy)) {
                    RectF rectF = getDrawableRectF();
                    if (getDrawable() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 图片宽度小于控件宽度，不允许移动
                        if (rectF.width() < width) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        // 图片高度小于控件高度，不允许移动
                        if (rectF.height() < height) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        if (rectF.left == 0 || rectF.right == width) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        // 检查边界
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);

                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastX = mLastY = 0;
                break;

        }
        return true;
    }

    /**
     * 在缩放的时候进行边界控制
     */
    private void checkBorderAndCenterWhenScale() {
        Log.d(TAG,TAG+" ----->>>checkBorderAndCenterWhenScale() ");
        RectF rectF = getDrawableRectF();
        float dx = 0;
        float dy = 0;
        // 缩放时进行边界检测，防止出现白边
        if (rectF.width() > width) {
            if (rectF.left > 0) {
                dx = -rectF.left;
            }
            if (rectF.right < width) {
                dx = width - rectF.right;
            }
        }
        if (rectF.height() > height) {
            if (rectF.top > 0) {
                dy = -rectF.top;
            }
            if (rectF.bottom < height) {
                dy = height - rectF.bottom;
            }
        }
        // 如果宽度或者高度小于控件的宽度或高度，则让其居中
        if (rectF.width() <= width) {
            dx = width / 2f - rectF.right + rectF.width() / 2f;
        }
        if (rectF.height() < height) {
            dy = height / 2f - rectF.bottom + rectF.height() / 2f;
        }

        mScaleMatrix.postTranslate(dx, dy);

    }

    /**
     * 移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {
        Log.d(TAG,TAG+" ----->>>checkBorderWhenTranslate() ");
        RectF rectF = getDrawableRectF();
        float dx = 0, dy = 0;
        if (rectF.left > 0 && isCheckLeftAndRight) {
            dx = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            dx = width - rectF.right;
        }
        if (rectF.top > 0 && isCheckTopAndBottom) {
            dy = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            dy = height - rectF.bottom;
        }
        mScaleMatrix.postTranslate(dx, dy);
    }

    /**
     * 判断手指发生的位移是否达到移动的标准，防止手指抖动导致图片位置不稳定
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        Log.d(TAG,TAG+" ----->>>isMoveAction ");
        return Math.sqrt(dx * dx + dy * dy) >= mScaledTouchSlop;
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {
         Log.d(TAG,TAG+" ----->>>getScale() ");
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 将矩阵的scale置为默认值
     */
    public void setScaleDefault() {
        Log.d(TAG,TAG+" ----->>>setScaleDefault ");
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        values[Matrix.MSCALE_X] = 1;
        values[Matrix.MSCALE_Y] = 1;
        mScaleMatrix.setValues(values);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d(TAG,TAG+" ----->>>onScale ");
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();
        if (getDrawable() == null) return true;

        // 缩放范围控制
        if ((scale >= mInitScale && scale < 1.0f) || (scale <= mMaxScale && scale > 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            checkBorderAndCenterWhenScale();

            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            setImageMatrix(mScaleMatrix);

        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG,TAG+" ----->>>onScaleBegin ");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG,TAG+" ----->>>onScaleEnd ");
    }

    /**
     * 获得图片放大缩小后的宽高，以及l,t,r,b
     *
     * @return
     */
    private RectF getDrawableRectF() {
        Log.d(TAG,TAG+" ----->>>getDrawableRectF ");
        Matrix matrix = this.mScaleMatrix;
        Drawable d = getDrawable();
        RectF rectF = new RectF();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }
}
