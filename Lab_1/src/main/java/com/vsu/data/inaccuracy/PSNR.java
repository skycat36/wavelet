package com.vsu.data.inaccuracy;


import java.util.List;

// Peak-Signal-to-Noise-Ratio
public class PSNR implements Accuracy {

    private int maxNumber;

    public PSNR(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Override
    public double calcAccuracy(List<Double> arrReal, List<Double> arrCompress) {

        double result = 0.0;

        for (int i = 0; i < arrReal.size()-1; i++){
            result += Math.pow(Math.abs(arrReal.get(i) - arrCompress.get(i)), 2.0);
        }

        result /= arrReal.size();

        if (result != 0) {
            result = 10.0 * Math.log10(Math.pow(this.maxNumber, 2.0) / result);
        }

        return result;
    }

}
