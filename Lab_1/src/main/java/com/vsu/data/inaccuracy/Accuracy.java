package com.vsu.data.inaccuracy;

import java.util.List;

public interface Accuracy {
    double calcAccuracy(List<Double> arrReal, List<Double> arrCompress);
}
