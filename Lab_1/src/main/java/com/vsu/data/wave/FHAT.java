package com.vsu.data.wave;

public class FHAT extends MatherWave {
    @Override
    protected double funcWeve(double x) {
        x = Math.abs(x);

        if (x <= 1.0 / 3.0){
            return 1.0;
        }
        if (1.0 / 3.0 < x & x <= 1.0){
            return -0.5;
        }
        if (x > 1.0){
            return 0.0;
        }
        return 0.0;
    }
}
