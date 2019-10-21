package com.vsu.app;

import com.vsu.data.Haar;

import java.util.List;

public class App {
    private final static double PI = Math.PI; //число Пи

    private static int accuracy = 3; // точность

    private static int N = 10; // количество точек

    private static int a = 0, b = 1, m = 1;

    public static void main(String[] args){

        Haar haar = new Haar(N, a, b, m, accuracy);

        haar.calculeteWave();

        List res = haar.getResultList();


        System.out.println("Точка:                          Значение:");
        for (int i = 0; i < N; i++){
            System.out.println(i + ":                                 " + res.get(i));
        }

        System.out.println("Prove: " + haar.prove());
    }
}
