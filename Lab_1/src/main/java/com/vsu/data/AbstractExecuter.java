package com.vsu.data;

import com.vsu.data.inaccuracy.Accuracy;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.PointWave;
import com.vsu.data.wave.Wave;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

import java.util.List;

public abstract class AbstractExecuter {
    private final static double PI = Math.PI; //число Пи

    private static final int MAX_EVAL = 1000000000;

    protected double a, b;
    protected double m;

    protected int accuracy; // точность

    protected Accuracy accuracyMethod;

    private Wave matherWave;

    private Func func;

    protected List<PointWave> resultList;

    private BaseAbstractUnivariateIntegrator baseAbstractUnivariateIntegrator = new TrapezoidIntegrator();

    public AbstractExecuter() {
    }

    public AbstractExecuter(Wave wave, Func func, double a, double b, double m, int accuracy) {
        this.func = func;
        this.matherWave = wave;
        this.a = a;
        this.b = b;
        this.m = m;
        this.accuracy = accuracy;
    }

    protected double calcIntegralA0(){
        UnivariateFunction f = (x0) -> this.func.value(x0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, this.a, this.b);
    }

    protected double calcIntegralCTwoDimensional(int i, int j){
        UnivariateFunction f = (x0) -> this.func.value(x0) * this.matherWave.calcWave(x0, i, j);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, this.a, this.b);
    }

    abstract public void calculeteWave();

    protected double calcNormA0(){
        UnivariateFunction f0 = (x0) -> Math.pow(this.func.value(x0) , 2.0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, this.a, this.b);
    }

    public double calculeteAccurancy(List<Integer> arrReal, List<Integer> arrCompress){
        return this.accuracyMethod.calcAccuracy(arrReal, arrCompress);
    }

    public double prove(){
        double powElem = 0;
        for (PointWave ch : this.resultList){
            powElem += Math.pow(ch.getValue(), 2.0);
        }
        return Math.sqrt(calcNormA0()) - Math.sqrt(powElem);
    }

    public List<PointWave> getResultList() {
        return resultList;
    }

    public void setAccuracyMethod(Accuracy accuracyMethod) {
        this.accuracyMethod = accuracyMethod;
    }
}
