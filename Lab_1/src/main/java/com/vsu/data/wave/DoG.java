package com.vsu.data.wave;

public class DoG extends Wave {

    @Override
    public double funcWeve(double x) {

        double result = Math.exp(-Math.pow(x, 2) / 2.0) - 0.5 * Math.exp(-Math.pow(x, 2.0) / 8.0);

        return result;
    }
}
