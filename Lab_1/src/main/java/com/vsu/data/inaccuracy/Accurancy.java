package com.vsu.data.inaccuracy;

import java.util.List;

public interface Accurancy {
    double calcAccuracy(List<Double> arrReal, List<Double> arrCompress);
    String nameMethod();
}
