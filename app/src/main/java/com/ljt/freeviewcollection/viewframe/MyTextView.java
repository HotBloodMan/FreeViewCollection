package com.ljt.freeviewcollection.viewframe;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/2/28.
 */

public class MyTextView extends View {

    private static final String TAG = MyTextView.class.getSimpleName();

    /* 4 styleable 的含义是什么？可以不写嘛？我自定义属性，我声明属性就好了，为什么一定要写个styleable呢？
    * */
    private static final int[] mAttr={android.R.attr.text,R.attr.testAttr2};
    private static final int ATTR_ANDROID_TEXT=0;
    private static final int ATTR_TESTATTR=1;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*
        * 1 AttributeSet作用:通过AttributeSet可以获得布局文件中定义的所有属性的key和value
        *
        *2  TypedArray作用:TypedArray其实是用来简化我们的工作的，比如上例，如果布局中的属性的值是引用类型（比如：@dimen/dp100），如果使用AttributeSet去获得最终的像素值，那么需要第一步拿到id，第二步再去解析id。而TypedArray正是帮我们简化了这个过程。
        *
        * 3 如果系统中已经有了语义比较明确的属性，我可以直接使用嘛？
        * */
        int count = attrs.getAttributeCount();
        for(int i=0;i<count;i++){


            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
             Log.d(TAG,TAG+" ----->>> attributeName= "+attributeName+" attributeValue="+attributeValue);
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.test);
        String text = ta.getString(R.styleable.test_android_text);
        int textAttr = ta.getInteger(R.styleable.test_testAttr, -1);
         Log.d(TAG,TAG+" ----->>> text= "+text+" textAttr= "+textAttr);


        TypedArray tas = context.obtainStyledAttributes(attrs,mAttr);
        String textTest = tas.getString(ATTR_ANDROID_TEXT);
        int textAttrTest = tas.getInteger(ATTR_TESTATTR, -1);
        Log.d(TAG,TAG+" ----->>> textTest= "+textTest+" textAttrTest= "+textAttrTest);

        ta.recycle();

    }
}
