package com.vsu.data.executers;

import com.vsu.data.executers.AbstractExecuter;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.WAVE;

public class ExecuterWAVE extends AbstractExecuter {
    public ExecuterWAVE(Func func, double a, double b, double m, double n, int accuracy) {
        super(new WAVE(), func, a, b, m, n, accuracy);
    }
}
