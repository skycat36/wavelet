package com.vsu.data.executers;

import com.vsu.data.inaccuracy.Accurancy;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.PointWave;
import com.vsu.data.wave.MatherWave;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExecuter {
    private final static double PI = Math.PI; //число Пи

    //Максимальное разбиение интеграла
    private static final int MAX_EVAL = 1_000_000_000;

    //Границы интегрирования
    protected double a, b;

    //Для вычисления вейвлета заданного как массив
    protected double m, n;

    protected int accuracy; // точность

    protected Accurancy accurancyMethod;

    // Материнский вейвлет
    private MatherWave matherWave;

    // Функция для вейвлета
    private Func func;

    //Точки вычисленного вейвлета
    protected List<PointWave> resultList;

    //Метод вычисления интеграла
    private BaseAbstractUnivariateIntegrator baseAbstractUnivariateIntegrator = new SimpsonIntegrator();

    public AbstractExecuter() {
    }

    public AbstractExecuter(MatherWave matherWave, Func func, double a, double b, double m, double n, int accuracy) {
        this.func = func;
        this.matherWave = matherWave;
        this.a = a;
        this.b = b;
        this.m = m;
        this.n = n;
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

    public void calculeteWave() {
        this.resultList = new ArrayList<PointWave>();

        for (int i = 1; i <= this.m; i++){
            for (int j = 1; j <= this.n; j++) {
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

    //Посчитать норму
    protected double calcNormA0(){
        UnivariateFunction f0 = (x0) -> Math.pow(this.func.value(x0) , 2.0);

        return baseAbstractUnivariateIntegrator.integrate(MAX_EVAL, f0, this.a, this.b);
    }

    //Посчитать погрешность
    public double calculeteAccurancy(List<Double> arrReal, List<Double> arrCompress){
        return this.accurancyMethod.calcAccuracy(proveAndConvertToDouble(arrReal), proveAndConvertToDouble(arrCompress));
    }

    //Проверка Парсиваля
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

    public void setAccurancyMethod(Accurancy accurancyMethod) {
        this.accurancyMethod = accurancyMethod;
    }

    protected List proveAndConvertToDouble(List arr){
        List<Double> result = new ArrayList<>();
        arr.forEach(x -> result.add(Double.parseDouble(x.toString())));
        return result;
    }
}
