package com.vsu.data.wave;

public class WAVE extends MatherWave {
    @Override
    protected double funcWeve(double x) {
        return -x * Math.exp(- Math.pow(x, 2.0) / 2);
    }
}
