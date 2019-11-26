package com.vsu.data.inaccuracy;

import java.util.List;

public interface Accuracy {
    double calcAccuracy(List<Integer> arrReal, List<Integer> arrCompress);
}
