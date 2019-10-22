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

    private int a, b, m;

    private int accuracy; // точность

    private List<PointHaar> resultList;

    private BaseAbstractUnivariateIntegrator baseAbstractUnivariateIntegrator = new TrapezoidIntegrator();

    public Haar(int a, int b, int m, int accuracy) {
        this.a = a;
        this.b = b;
        this.m = m;
        this.accuracy = accuracy;
    }

    private double funcF(double x){
        return Math.exp(-15 * Math.abs(x - 0.3)) + Math.pow(x, 2);
    }

    private double funcWeveHaar(double x, int i, int j){

        double del = Math.pow(2, i);
        double del2 = Math.pow(2, i + 1);

        if ( (((j-1) / del) < x) & (x < (((j-1) / del) + 1 / del2)) ){
            return Math.pow(2, i / 2);
        }

        if ( (x > (((j-1) / del) + 1 / del2)) & (x < (j / del)) ){
            return -Math.pow(2, i / 2);
        }

        if ( ((j - 1) / del) > x || (j / del) < x ){
            return 0;
        }
        return 0;
    }


    private double calcIntegralA0(){
        UnivariateFunction f = (x0) -> funcF(x0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, this.a, this.b);
    }

    private double calcIntegralC(int i, int j){
        UnivariateFunction f = (x0) -> this.funcF(x0) * this.funcWeveHaar(x0, i, j);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, this.a, this.b);
    }


    public void calculeteWave(){
        this.resultList = new ArrayList<PointHaar>();

        this.resultList.add(new PointHaar(0, this.calcIntegralA0()));

        int number = 1;

        int n = (int)Math.pow(2, this.m);

        for (int i = 0; i <= this.m; i++){

            for (int j=1; j < n; j++) {


                this.resultList.add(
                        new PointHaar(
                                number,
                                Precision.round(
                                        calcIntegralC(i, j),
                                        this.accuracy
                                )
                        )
                );
                number++;
            }
        }

    }


    private double calcNormA0(){
        UnivariateFunction f0 = (x0) -> Math.pow(this.funcF(x0) , 2);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, this.a, this.b);
    }

    public double prove(){
        double powElem = 0;
        for (PointHaar ch : this.resultList){
            powElem += Math.pow(ch.getValue(), 2);
        }
        return Math.sqrt(calcNormA0()) - Math.sqrt(powElem);
    }

    public List<PointHaar> getResultList() {
        return resultList;
    }
}
