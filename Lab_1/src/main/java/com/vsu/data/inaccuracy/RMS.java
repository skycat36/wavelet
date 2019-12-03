package com.vsu.data.inaccuracy;

import java.util.List;

public class RMS implements Accurancy {

    @Override
    public double calcAccuracy(List<Double> arrReal, List<Double> arrCompress) {
        double result = 0;

        for (int i = 0; i < arrReal.size()-1; i++){
            result += Math.pow(Math.abs(arrReal.get(i) - arrCompress.get(i)), 2.0);
        }
        result /= arrReal.size();

        result = Math.sqrt(result);
        return result;
    }

    @Override
    public String nameMethod() {
        return "Среднеквадратичное отклонение (Евклидова норма)";
    }
}
