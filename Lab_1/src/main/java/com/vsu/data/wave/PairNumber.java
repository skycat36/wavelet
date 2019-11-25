package com.vsu.data.wave;

public class PairNumber {

    private int a;
    private int b;

    public PairNumber(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int polSum(){
        return (int) (this.a + this.b) / 2;
    }

    public int polRuz(){
        return (int) (this.a - this.b) / 2;
    }
}
