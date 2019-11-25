package com.vsu.data;

import com.vsu.data.wave.*;
import org.apache.commons.math3.util.Precision;

import java.util.*;

public class ExecuterHaar extends AbstractExecuter{

    public ExecuterHaar() {
    }

    public ExecuterHaar(Func func, double a, double b, double m, int accuracy) {
        super(new Haar(), func, a, b, m, accuracy);
    }

    @Override
    public void calculeteWave() {
        this.resultList = new ArrayList<PointWave>();

        this.resultList.add(new PointWave(0, 0, this.calcIntegralA0()));


        for (int i = 1; i <= this.m; i++){
            int n = (int)Math.pow(2.0, i);

            for (int j=1; j <= n; j++) {
                this.resultList.add(
                        new PointWave(
                                i, j,
                                Precision.round(
                                        calcIntegralCTwoDimensional(i, j),
                                        this.accuracy
                                )
                        )
                );
            }

        }
    }

    public List<List<Integer>> transformHaar(List arr){
        List result = new ArrayList();
        result.add(arr);
        int devide = arr.size();
        for (int i=0; i <= Math.sqrt(arr.size()); i++){
            arr = averageArr(arr, devide);
            devide /= 2;
            result.add(arr);
        }
        return result;
    }


    public List<Integer> averageArr(List arr, int devideComlete) {
        List<Integer> result = new ArrayList<>();

        List<PairNumber> pairNumberList = new ArrayList<>();
        for (int i=0; i < devideComlete; i += 2){
            pairNumberList.add(new PairNumber((int)arr.get(i), (int)arr.get(i+1)));
        }

        for (PairNumber pairNumber: pairNumberList){
            result.add(pairNumber.polSum());
        }
        for (PairNumber pairNumber: pairNumberList){
            result.add(pairNumber.polRuz());
        }

        result.addAll(arr.subList(devideComlete, arr.size()));

        return result;
    }

    public List<Integer> averageRemoteArr(List arr, int devideComlete) {
        List<Integer> result = new ArrayList<>();

        List<PairNumber> pairNumberList = new ArrayList<>();
        for (int i=0; i < devideComlete; i += 2){
            pairNumberList.add(new PairNumber((int)arr.get(i), (int)arr.get(i+1)));
        }

        for (PairNumber pairNumber: pairNumberList){
            result.add(pairNumber.sum());
        }
        for (PairNumber pairNumber: pairNumberList){
            result.add(pairNumber.ruz());
        }

        result.addAll(arr.subList(devideComlete, arr.size()));

        return result;
    }

    public List compressArr(List<Integer> arr, int procentCompr){
        int countNumbersWhoNeedDelete = (arr.size() * procentCompr) / 100;

        while (countNumbersWhoNeedDelete > 0){
            int minEl = Math.abs(arr.get(0));

            for (Integer a: arr){
                if (Math.abs(a) < minEl && a != 0){
                    minEl = a;
                }
            }

            for (int i = arr.size() - 1; i > 0; i-- ){
                if (Math.abs(arr.get(i)) == minEl && countNumbersWhoNeedDelete > 0){
                    arr.set(i, 0);
                    countNumbersWhoNeedDelete--;
                }
            }
        }
        return arr;
    }


    public List<List<Integer>> remoteTransformHaar(List arr){
        List result = new ArrayList();
        result.add(arr);
        int devide = 1;
        for (int i=0; i <= Math.sqrt(arr.size()); i++){
            arr = averageRemoteArr(arr, devide);
            devide *= 2;
            result.add(arr);
        }
        return result;
    }
}
