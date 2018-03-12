package com.ljt.freeviewcollection.customwanim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${JT.L} on 2018/3/9.
 */

public class PageFragment extends Fragment implements IParallaxView {

    private List<View> parallaxViews=new ArrayList<>();

    public static PageFragment newInstance(int resId) {

        Bundle args = new Bundle();
        args.putInt("layoutId",resId);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle arg = getArguments();
        int layoutId = arg.getInt("layoutId");
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater, getActivity(), this);
        return parallaxLayoutInflater.inflate(layoutId,null);
    }
    @Override
    public List<View> getParallaxViews() {
        return parallaxViews;
    }
}
