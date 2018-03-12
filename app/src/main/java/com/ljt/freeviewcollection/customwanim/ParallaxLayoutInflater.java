package com.ljt.freeviewcollection.customwanim;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ljt.freeviewcollection.R;

/**
 * Created by ${JT.L} on 2018/3/9.
 */

public class ParallaxLayoutInflater extends LayoutInflater{

    private IParallaxView mParallaxView;
    protected ParallaxLayoutInflater(Context context) {
        super(context);
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext,IParallaxView fragment) {
        super(original, newContext);
        this.mParallaxView=fragment;
        setFactory(new ParallaxFactory(mParallaxView,this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this,newContext,mParallaxView);
    }

    public class ParallaxFactory implements  LayoutInflater.Factory2{

         private IParallaxView mParallaxView;
        private LayoutInflater inflater;
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };

        public ParallaxFactory(IParallaxView mParallaxView, LayoutInflater inflater) {
            this.mParallaxView = mParallaxView;
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view=createViewOrFailQuietly(name,context,attrs);

            if(view !=null){
                setViewTag(view,context,attrs);
                mParallaxView.getParallaxViews().add(view);
            }
            return view;
        }
        private void setViewTag(View view,Context context,AttributeSet attrs){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimationView);
            if(a !=null && a.length() >0){
                ParallaxViewTag tag = new ParallaxViewTag();
                tag.xIn=a.getFloat(R.styleable.AnimationView_x_in,0f);
                tag.xOut=a.getFloat(R.styleable.AnimationView_x_out,0f);
                tag.yIn=a.getFloat(R.styleable.AnimationView_y_in,0f);
                tag.yOut=a.getFloat(R.styleable.AnimationView_y_out,0f);

                view.setTag(view.getId(),tag);
                a.recycle();
            }
        }

        private View createViewOrFailQuietly(String name,Context context,AttributeSet atts){
            //自定义控件
            if(name.contains(".")){
                createViewOrFailQuietly(name,null,context,atts);
            }
            //系统控件
            for(String prefix:sClassPrefix){
                View view = createViewOrFailQuietly(name, prefix, context, atts);
                if(view!=null){
                    return view;
                }
            }
            return null;
        }

        private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet atts) {
            try {
                return inflater.createView(name,prefix,atts);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }


        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }
}
