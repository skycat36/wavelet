package com.vsu.data.inaccuracy;

import java.util.List;

public class IntDeviation implements Accurancy {
    @Override
    public double calcAccuracy(List<Double> arrReal, List<Double> repairArr) {
        double result = 0;

        for (int i = 0; i < arrReal.size(); i++){
            result += Math.abs(arrReal.get(i) - repairArr.get(i));
        }

        result /= arrReal.size();
        return result;
    }

    @Override
    public String nameMethod() {
        return "Среднепиксельная ошибка";
    }
}
