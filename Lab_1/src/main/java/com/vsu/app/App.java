package com.vsu.app;

import com.vsu.data.AbstractExecuter;
import com.vsu.data.ExecuterDoG;
import com.vsu.data.ExecuterHaar;
import com.vsu.data.inaccuracy.IntDeviation;
import com.vsu.data.inaccuracy.PSNR;
import com.vsu.data.wave.DoG;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.PointWave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class App {
    private final static double PI = Math.PI; //число Пи

    private static int accuracy = 6; // точность

    private static int N = 10; // количество точек

   // private static double a = -92.70999999999862, b = +92.70999999999862, m = 2, n = 3;
   private static double a = -1000000, b = 1000000, m = 100, n = 100;
   static int mh = 100;

    public static void main(String[] args){

//Вычисление вейвлета DoG
//        Func func = (x)-> -28.0 * Math.sin(4.0 / (19.0 * PI) * x - 1.0) + 6.0 * Math.cos(1.0 / (40.0 * Math.sqrt(PI)) * x);
//
//        ExecuterDoG executerDoG = new ExecuterDoG(func, a, b, m, n, accuracy);
//        executerDoG.calculeteWave();

        ExecuterHaar haarExecuterHaar = new ExecuterHaar();
        //249, 247, 243, 241, 180, 184, 235, 237
        //13.5,2.5,-1,3
        List arr = Arrays.asList(249, 247, 243, 241, 180, 184, 235, 237);

        System.out.println("Разложение массива по Хоару:");
        List<List<Double>> resultList = haarExecuterHaar.transformHaar(arr);
        for (List arrHaar: resultList){
            arrHaar.stream().forEach(
                    x -> System.out.printf("%6s ", x )
            );
            System.out.println();
        }


        List<Double> compressData = haarExecuterHaar.compressArr(new ArrayList<>(resultList.get(resultList.size()-1)), 40);
        System.out.println("Сжатый массив данных:");
        compressData.stream().forEach(x -> System.out.printf("%6s ", x ));

        List<List<Double>> repairArr = haarExecuterHaar.remoteTransformHaar(compressData);
        System.out.println("\nВостановленный массив:");
        for (List arrHaar: repairArr){
            arrHaar.stream().forEach(
                    x -> System.out.printf("%6s ", x )
            );
            System.out.println();
        }

        System.out.println("\nСреднепиксельная ошибка:");
        haarExecuterHaar.setAccuracyMethod(new IntDeviation());
        System.out.println(haarExecuterHaar.calculeteAccurancy(arr, repairArr.get(repairArr.size()-1)));

        System.out.println("\nПиковое отношение сигнала к шуму:");
        haarExecuterHaar.setAccuracyMethod(new PSNR(255));
        System.out.println(haarExecuterHaar.calculeteAccurancy(arr, repairArr.get(repairArr.size()-1)));
    }

    private static void printResult(AbstractExecuter abstractExecuter) {
        List<PointWave> res = abstractExecuter.getResultList();


        System.out.printf("Точки: %2sX| Y%-5s | Значение: %7s%n", "", "", "");
        for (PointWave pointWave : res){
            System.out.printf("%8s%2d| %-6d |  %-8f%n", "", pointWave.getPointX(), pointWave.getPointY(), pointWave.getValue());
        }

        System.out.println("Prove: " + abstractExecuter.prove());
    }

    //Вычисление вейвлета Хоара
    private static ExecuterHaar getExecuterHaar() {
        Func func = (x)-> Math.exp(-15.0 * Math.abs(x - 0.3)) + Math.pow(x, 2.0);

        ExecuterHaar haarExecuterHaar = new ExecuterHaar(func, a, b, m, accuracy);

        haarExecuterHaar.calculeteWave();
        return haarExecuterHaar;
    }
}
