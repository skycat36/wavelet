package com.vsu.data;

import com.vsu.data.wave.DoG;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.PointWave;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

public class ExecuterDoG extends AbstractExecuter {

    private double eps = 0.01;

    private double n;

    private double minInterval;

    public ExecuterDoG(Func func, double a, double b, double m, double n, int accuracy) {
        super(new DoG(), func, a, b, m, accuracy);
        this.n = n;
    }

    @Override
    public void calculeteWave() {
        this.resultList = new ArrayList<PointWave>();

        for (int i = 1; i <= this.m; i++){
            for (int j=1; j <= this.n; j++) {
                this.resultList.add(
                        new PointWave(
                                i, j,
                                Precision.round(
                                        calcIntegralCTwoDimensional(i, j),
                                        this.accuracy
                                )
                        )
                );
            }

        }
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
