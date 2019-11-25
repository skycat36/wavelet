package com.vsu.data.wave;

public class PointWave {

    private int pointX;
    private int pointY;
    private double value;

    public PointWave(int pointX, int pointY, double value) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.value = value;
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
