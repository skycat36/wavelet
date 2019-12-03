package com.vsu.data.wave;

public class MHAT extends MatherWave {

    @Override
    protected double funcWeve(double x) {
        return (1 - Math.pow(x, 2.0)) * Math.exp(- Math.pow(x, 2.0) / 2);
    }
}
