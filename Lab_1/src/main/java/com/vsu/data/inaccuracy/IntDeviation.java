package com.vsu.data.inaccuracy;

import java.util.List;

public class IntDeviation implements Accuracy {
    @Override
    public double calcAccuracy(List<Integer> arrReal, List<Integer> repairArr) {
        double result = 0;

        for (int i = 0; i < arrReal.size(); i++){
            result += Math.abs(arrReal.get(i) - repairArr.get(i));
        }

        result /= arrReal.size();
        return result;
    }
}
