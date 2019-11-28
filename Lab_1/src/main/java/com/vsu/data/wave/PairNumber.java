package com.vsu.data.wave;

public class PairNumber {

    private double a;
    private double b;

    public PairNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }


    public double sum(){
        return (this.a + this.b);
    }

    public double ruz(){
        return (this.a - this.b);
    }

    public double polSum(){
        return (this.a + this.b) / 2;
    }

    public double polRuz(){
        return (this.a - this.b) / 2;
    }
}
