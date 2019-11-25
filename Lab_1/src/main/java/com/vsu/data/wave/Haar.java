package com.vsu.data.wave;

public class Haar extends Wave {

    @Override
    public double calcWave(double  x, int i, int j) {
        double del = Math.pow(2.0, i);
        double del2 = Math.pow(2.0, i + 1.0);

        if ( (((j-1.0) / del) < x) & (x <= (((j-1.0) / del) + 1.0 / del2)) ){
            return Math.pow(2.0, i / 2.0);
        }

        if ( (x >= (((j-1.0) / del) + 1.0 / del2)) & (x < (j / del)) ){
            return -Math.pow(2.0, i / 2.0);
        }

        if ( ((j - 1.0) / del) > x | (j / del) < x ){
            return 0;
        }
        return 0;
    }

    @Override
    protected double funcWeve(double x) {
        return x;
    }

}
