package com.vsu.data;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

public class Haar {

    private final static double PI = Math.PI; //число Пи

    private static final int MAX_EVAL = 1000000000;

    private int n, a, b, m;

    private int accuracy; // точность

    private List<Double> resultList;

    private BaseAbstractUnivariateIntegrator baseAbstractUnivariateIntegrator = new TrapezoidIntegrator();

    public Haar(int n, int a, int b, int m, int accuracy) {
        this.n = n;
        this.a = a;
        this.b = b;
        this.m = m;
        this.accuracy = accuracy;
    }

    private double funcF(double x){
        return Math.exp(-15 * Math.abs(x - 0.3)) + Math.pow(x, 2);
    }

    private double funcWeveHaar(double x, double i){

        double del = Math.pow(2, this.m);
        double del2 = Math.pow(2, this.m + 1);

        if ( (((i-1) / del) < x) & (x < (((i-1) / del) + 1 / del2)) ){
            return Math.pow(2, this.m / 2);
        }

        if ( (x > (((i-1) / del) + 1 / del2)) & (x < (i / del)) ){
            return -Math.pow(2, this.m / 2);
        }

        if ( ((i - 1) / del) < x || (i / del) > x ){
            return 0;
        }
        return 0;
    }



    private double calcIntegralC(int i){
        UnivariateFunction f = (x0) -> this.funcF(x0) * this.funcWeveHaar(x0, i);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, this.a, this.b);
    }


    public void calculeteWave(){
        this.resultList = new ArrayList<Double>();

        for (int i = 1; i <= this.n; i++){
            this.resultList.add(
                    Precision.round(
                            calcIntegralC(i),
                            this.accuracy
                    )
            );
        }

    }


    private double calcNormA0(){
        UnivariateFunction f0 = (x0) -> Math.pow(this.funcF(x0) , 2);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, this.a, this.b);
    }

    public double prove(){
        double powElem = 0;
        for (Double ch : this.resultList){
            powElem += Math.pow(ch, 2);
        }
        return Math.sqrt(calcNormA0()) - Math.sqrt(powElem);
    }

    public List<Double> getResultList() {
        return resultList;
    }
}
