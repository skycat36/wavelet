package com.vsu.data.executers;

import com.vsu.data.inaccuracy.Accurancy;
import com.vsu.data.wave.*;
import org.apache.commons.math3.util.Precision;

import java.util.*;

public class ExecuterHaar extends AbstractExecuter {

    public ExecuterHaar() {
    }

    public ExecuterHaar(Func func, double a, double b, double m, int accuracy) {
        super(new Haar(), func, a, b, m, -1, accuracy);
    }

    @Override
    public void calculeteWave() {
        this.resultList = new ArrayList<PointWave>();

        this.resultList.add(new PointWave(0, 0, this.calcIntegralA0()));


        for (int i = 1; i <= this.m; i++){
            this.n = (int)Math.pow(2.0, i);

            for (int j=1; j <= this.n; j++) {
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

    public List<List<Double>> transformHaar(List arr){
        arr = proveAndConvertToDouble(arr);
        List result = new ArrayList();
        result.add(arr);
        int devide = arr.size();
        for (int i=0; i < Math.sqrt(arr.size()); i++){
            arr = averageArr(arr, devide);
            devide /= 2;
            result.add(arr);
        }
        return result;
    }

    private List<Double> averageArr(List arr, int devideComlete) {
        List<Double> result = new ArrayList<>();

        List<PairNumber> pairNumberList = new ArrayList<>();
        for (int i=0; i < devideComlete; i += 2){
            pairNumberList.add(new PairNumber(Double.parseDouble(arr.get(i).toString()), Double.parseDouble(arr.get(i+1).toString())));
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

    public List<Double> averageRemoteArr(List arr, int devideComlete) {
        List<Double> result = new ArrayList<>();

        List<PairNumber> pairNumberList = new ArrayList<>();
        for (int i=0; i < devideComlete; i ++){
            pairNumberList.add(new PairNumber(Double.parseDouble(arr.get(i).toString()), Double.parseDouble(arr.get(devideComlete + i).toString())));
        }

        for (PairNumber pairNumber: pairNumberList){
            result.add(pairNumber.sum());
            result.add(pairNumber.ruz());
        }

        result.addAll(arr.subList(devideComlete * 2, arr.size()));

        return result;
    }

    public List compressArr(List<Double> arr, int countNumbersWhoNeedDelete){

        if (countNumbersWhoNeedDelete > arr.size() || countNumbersWhoNeedDelete < 0){
            return null;
        }

        while (countNumbersWhoNeedDelete > 0){
            double minEl = Math.abs(arr.get(0));

            for (Double a: arr){
                if (Math.abs(a) < minEl && a != 0){
                    minEl = Math.abs(a);
                }
            }

            for (int i = arr.size() - 1; i > 0; i-- ){
                if (Math.abs(arr.get(i)) == minEl && countNumbersWhoNeedDelete > 0){
                    arr.set(i, 0.0);
                    countNumbersWhoNeedDelete--;
                }
            }
        }
        return arr;
    }


    public List<List<Double>> remoteTransformHaar(List arr){
        arr = proveAndConvertToDouble(arr);
        List result = new ArrayList();
        result.add(arr);
        int devide = 1;
        for (int i=0; i < Math.sqrt(arr.size()); i++){
            arr = averageRemoteArr(arr, devide);
            devide *= 2;
            result.add(arr);
        }
        return result;
    }

    //Поиск количества элементов которые можно безболезненно отбросить(не превычая указанную точность)
    public int searchCountElemWhoMayBeDel(Accurancy accurancy, List<Double> arrReal, double goal){
        arrReal = proveAndConvertToDouble(arrReal);
        List<List<Double>> tempArrTransform = transformHaar(arrReal);

        List<Double> needArrTransform = tempArrTransform.get(tempArrTransform.size()-1);
        int numDel = 0;  // числа которые можно удалить
        List<Double> comprArr = compressArr(new ArrayList<>(needArrTransform), numDel);

        tempArrTransform = remoteTransformHaar(comprArr);
        List<Double> arrRemote = tempArrTransform.get(tempArrTransform.size()-1);

        for (int i = 0; i < comprArr.size(); i++){
            double pogr = accurancy.calcAccuracy(arrReal, arrRemote);
            if (pogr < goal){
                numDel++;
            }else {
                return --numDel;
            }
            comprArr = compressArr(new ArrayList<>(needArrTransform), numDel);
            tempArrTransform = remoteTransformHaar(comprArr);
            arrRemote = tempArrTransform.get(tempArrTransform.size()-1);

        }
        return numDel;
    }
}
