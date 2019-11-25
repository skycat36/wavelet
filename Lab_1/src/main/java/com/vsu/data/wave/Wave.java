package com.vsu.data.wave;

public abstract class Wave {

    private double calcNeedPoint(double x, int a, int b){
        return  (x - b) / a;
    }

    public double calcWave(double x, int a, int b){

        return Math.pow(Math.abs(a), -0.5) * this.funcWeve(this.calcNeedPoint(x, a, b));
    }

    protected abstract double funcWeve(double x);
}
