package ru.vsu.application;

import ru.vsu.calculete.Example;

import java.util.List;

public class App {

    private final static double PI = Math.PI; //число Пи

    private static int accuracy = 2; // точность

    private static int N = 10;  // количество точек

    public static void main(String[] args){

        Example example = new Example(accuracy, 2* PI, N);

        example.calculeteWave();

        List res = example.getResultList();


        System.out.println("Точка:                          Значение:");
        for (int i = 0; i < N; i++){
            System.out.println(i + ":                                 " + res.get(i));
        }

        System.out.println("Prove: " + example.prove());
    }
}
