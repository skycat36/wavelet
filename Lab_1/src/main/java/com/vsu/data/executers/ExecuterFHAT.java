package com.vsu.data.executers;

import com.vsu.data.wave.FHAT;
import com.vsu.data.wave.Func;

public class ExecuterFHAT extends AbstractExecuter {
    public ExecuterFHAT(Func func, double a, double b, double m, double n, int accuracy) {
        super(new FHAT(), func, a, b, m, n, accuracy);
    }
}
