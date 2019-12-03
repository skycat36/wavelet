package com.vsu.data.executers;

import com.vsu.data.wave.Func;
import com.vsu.data.wave.MHAT;

public class ExecuterMHAT extends AbstractExecuter {
    public ExecuterMHAT(Func func, double a, double b, double m, double n, int accuracy) {
        super(new MHAT(), func, a, b, m, n, accuracy);
    }
}
