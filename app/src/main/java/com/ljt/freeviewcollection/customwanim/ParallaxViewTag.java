package com.ljt.freeviewcollection.customwanim;

/**
 * Created by ${JT.L} on 2018/3/9.
 */

public class ParallaxViewTag {
    public int index;
    public float xIn;
    public float xOut;
    public float yIn;
    public float yOut;
    protected float alphaIn;
    protected float alphaOut;

    @Override
    public String toString() {
        return "ParallaxViewTag{" +
                "index=" + index +
                ", xIn=" + xIn +
                ", xOut=" + xOut +
                ", yIn=" + yIn +
                ", yOut=" + yOut +
                '}';
    }
}
