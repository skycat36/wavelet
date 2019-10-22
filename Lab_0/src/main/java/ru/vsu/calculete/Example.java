package ru.vsu.calculete;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

public class Example{

    private final static double PI = Math.PI; //число Пи

    private static final int MAX_EVAL = 100000000;

    private int accuracy; // точность

    private double L, sqlrtL; //границы интегрирования  sqlrtL= sqrt(2/L)

    private int N; // количество точек

    private BaseAbstractUnivariateIntegrator baseAbstractUnivariateIntegrator = new SimpsonIntegrator();

    private List<Double> resultList;

    public Example(int accuracy, double L, int n) {
        this.accuracy = accuracy;
        this.L = L;
        this.N = n;
        this.sqlrtL = Math.sqrt(L);
    }

    private double calcA0(){
        UnivariateFunction f0 = (x0) -> x0*x0 - 5 * x0;

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, 0, this.L);
    }

    private double calcBn(int i){
        UnivariateFunction f = (x0) -> (x0 * x0 - 5 * x0) * Math.cos(i * x0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, 0, this.L);
    }

    private double calcAn(int i){
        UnivariateFunction f = (x0) -> (x0 * x0 - 5 * x0) * Math.sin(i * x0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f, 0, this.L);
    }

    public void calculeteWave(){
        this.resultList = new ArrayList<Double>();

        this.resultList.add(
                Precision.round(
                        (this.calcA0() / Math.sqrt(this.L)),
                        this.accuracy
                )
        );

        for (int i = 1, j = 1; i < this.N; i = i + 2, j++){
            this.resultList.add(
                    Precision.round(
                            (calcAn(j)) / Math.sqrt(this.L / 2),
                            this.accuracy
                    )
            );

            this.resultList.add(
                    Precision.round(
                            (calcBn(j)) / Math.sqrt(this.L / 2),
                            this.accuracy
                    )
            );
        }

    }

    private double calcNormA0(){
        UnivariateFunction f0 = (x0) -> Math.pow(x0*x0 - 5 * x0 , 2);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, 0, this.L);
    }

    public double prove(){
        double powElem = 0;
        for (Double ch : this.resultList){
            powElem += Math.pow(ch, 2);
        }
        return Math.sqrt(calcNormA0()) - Math.sqrt(powElem);
    }

    public List getResultList() {
        return resultList;
    }
}