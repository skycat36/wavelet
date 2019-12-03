package com.vsu.data.inaccuracy;

import java.util.List;

public class MaxAccarancy implements Accurancy {

    @Override
    public double calcAccuracy(List<Double> arrReal, List<Double> arrCompress) {
        double max = Math.abs(arrReal.get(0) - arrCompress.get(0));

        for (int i = 1; i < arrReal.size()-1; i++){
            if (Math.abs(arrReal.get(i) - arrCompress.get(i)) > max) {
               max = Math.abs(arrReal.get(i) - arrCompress.get(i));
            }
        }
        return max;
    }

    @Override
    public String nameMethod() {
        return "Максимальное отклонение";
    }
}
