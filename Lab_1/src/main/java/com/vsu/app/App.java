package com.vsu.app;

import com.vsu.data.executers.*;
import com.vsu.data.inaccuracy.*;
import com.vsu.data.wave.Func;
import com.vsu.data.wave.PointWave;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    private final static double PI = Math.PI; //число Пи

    private static int accuracy = 6; // точность

    private static int N = 10; // количество точек

   // private static double a = -92.70999999999862, b = +92.70999999999862, m = 2, n = 3;
   private static double a = -100_000, b = 100_000, m = 2, n = 3;

   //Func func1 = (x)-> Math.exp(-15.0 * Math.abs(x - 0.3)) + Math.pow(x, 2.0);
   //static Func func2 = (x)-> -28.0 * Math.sin(4.0 / (19.0 * PI) * x - 1.0) + 6.0 * Math.cos(1.0 / (40.0 * Math.sqrt(PI)) * x);


    static Func func = (x)-> 8.0 * Math.cos(1.0 / 14.0 * PI * x) - 13.0 * Math.sin(1.0 / 12.0 * PI * x);

    //static Func func = (x)-> -290.0 * Math.sin((PI * x) / 15.0 - PI / 4);

    public static void main(String[] args){

//Вычисление вейвлета DoG
//        Func func = (x)-> -28.0 * Math.sin(4.0 / (19.0 * PI) * x - 1.0) + 6.0 * Math.cos(1.0 / (40.0 * Math.sqrt(PI)) * x);

        //ExecuterDoG haarExecuterDoG = new ExecuterDoG(func, a, b, m, n, accuracy);
        //calcHeapDoG(func2, a, b, m, n, accuracy);

        //249, 247, 243, 241, 180, 184, 235, 237
        //13.5,2.5,-1,3

        calcMatrixWave(new ExecuterWAVE(func, a, b, m, n, accuracy));

        //Сжатие и разложение массива по вейвлету Хоара
        //var2
        //List arr = Arrays.asList(211, 215, 218, 220, 252, 216, 198, 142);

        //var1
        //List arr = Arrays.asList(154,148,146,150,155,151,230,236);

        //var3
        List arr = Arrays.asList(111,119,120,118,128,230,234,232);

        decompositionArrUseHaar(arr, 5, 8.0, new MaxAccarancy());



    }

    private static void printResult(AbstractExecuter abstractExecuter) {
        List<PointWave> res = abstractExecuter.getResultList();


        System.out.printf("Точки: %2sX| Y%-5s | Значение: %7s%n", "", "", "");
        for (PointWave pointWave : res){
            System.out.printf("%8s%2d| %-6d |  %-8f%n", "", pointWave.getPointX(), pointWave.getPointY(), pointWave.getValue());
        }

        System.out.println("Prove: " + abstractExecuter.prove());
    }

    //Вычисление вейвлета для матрицы
    private static void calcMatrixWave(AbstractExecuter abstractExecuter) {

        abstractExecuter.calculeteWave();

        printResult(abstractExecuter);
    }

    //Вычисление вейвлета Хоара
    private static void calcHeapHaar(Func func, double a, double b, double m, int accuracy) {
        ExecuterHaar haarExecuterHaar = new ExecuterHaar(func, a, b, m, accuracy);

        haarExecuterHaar.calculeteWave();

        printResult(haarExecuterHaar);
    }

    //Сжатие массива
    private static void decompositionArrUseHaar(List arr, int countNumbersWhoNeedDelete, double needGoal, Accurancy needMethod){
        ExecuterHaar haarExecuterHaar = new ExecuterHaar();
        System.out.println("Разложение массива по Хоару:");
        List<List<Double>> resultList = haarExecuterHaar.transformHaar(arr);
        for (List arrHaar: resultList){
            arrHaar.stream().forEach(
                    x -> System.out.printf("%6s ", x )
            );
            System.out.println();
        }

        List<Double> compressData = haarExecuterHaar.compressArr(new ArrayList<>(resultList.get(resultList.size()-1)), countNumbersWhoNeedDelete);
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

        int numWhoMayDel = haarExecuterHaar.searchCountElemWhoMayBeDel(needMethod, arr, needGoal);

        System.out.println( "\nДля метода " + needMethod.nameMethod().toLowerCase() + "\nКоличество элементов которых можно убрать не превысив погрешность " + needGoal + ": " + numWhoMayDel);

        proveOnErrors(haarExecuterHaar, arr, repairArr.get(repairArr.size()-1));
    }


    //Вычисление погрешности
    private static void proveOnErrors(AbstractExecuter executer, List<Double> arrReal, List<Double> arrCompress){

        List<Accurancy> accurancyList = Arrays.asList(new IntDeviation(), new PSNR(255), new RMS(), new MaxAccarancy());

        for (Accurancy accurancy: accurancyList){
            System.out.println("\n" + accurancy.nameMethod() + ":");
            executer.setAccurancyMethod(accurancy);
            System.out.println(executer.calculeteAccurancy(arrReal, arrCompress));
        }
    }
}
