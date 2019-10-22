package com.vsu.app;

import com.vsu.data.Haar;
import com.vsu.data.PointHaar;

import java.util.List;

public class App {
    private final static double PI = Math.PI; //число Пи

    private static int accuracy = 3; // точность

    private static int N = 10; // количество точек

    private static int a = 0, b = 1, m = 2;

    public static void main(String[] args){

        Haar haar = new Haar(a, b, m, accuracy);

        haar.calculeteWave();

        List<PointHaar> res = haar.getResultList();


        System.out.println("Точка:                          Значение:");
        for (PointHaar pointHaar: res){
            System.out.println(pointHaar.getNumber() + ":                                 " + pointHaar.getValue());
        }

        System.out.println("Prove: " + haar.prove());
    }
}
