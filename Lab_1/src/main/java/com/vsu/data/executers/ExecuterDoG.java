package com.vsu.data.executers;

import com.vsu.data.wave.DoG;
import com.vsu.data.wave.Func;

public class ExecuterDoG extends AbstractExecuter {

    private double eps = 0.01;

    private double minInterval;

    public ExecuterDoG(Func func, double a, double b, double m, double n, int accuracy) {
        super(new DoG(), func, a, b, m, n, accuracy);
    }

    public double searchOptimizeInterval(){
        this.minInterval = Math.abs(this.b);
        this.calculeteWave();
        double provePars = this.prove();
        double delta = 0.001;
        while ( Math.abs(provePars) > eps){
            if (provePars > 0){
                this.a -= delta;
                this.b += delta;
            }else {
                this.a += delta;
                this.b -= delta;
            }
            this.calculeteWave();
            provePars = this.prove();
        }

        return this.b;
    }
}
