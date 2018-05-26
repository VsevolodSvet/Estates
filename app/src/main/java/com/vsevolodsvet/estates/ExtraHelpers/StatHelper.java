package com.vsevolodsvet.estates.ExtraHelpers;

import com.vsevolodsvet.estates.Objects.Estate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class StatHelper {

    public static HashMap<String, List<Double>> getRegionPriceMValues(List<Estate> estates){
        HashMap<String, List<Double>> regionPrices = new HashMap<String, List<Double>>();

        HashSet<String> regionSet = new HashSet<String>();

        // заполняем набор районов
        for (Estate estate : estates) {
            if (!regionSet.contains(estate.getRegion())) {
                regionSet.add(estate.getRegion());
            }
        }

        // по каждому району выбираем все цены за квадратный метр и добавляем итоговый элемент
        for (String region : regionSet){
            List<Double> regPrices = new ArrayList<Double>();
            for (Estate estate : estates) {
                if (Objects.equals(estate.getRegion(), region) && estate.getPrice_m() != null) regPrices.add(estate.getPrice_m());
            }
            regionPrices.put(region, regPrices);
        }

        return regionPrices;
    }

    /**
     * Для всех методов ниже accuracy - точность (количество знаков после запятой)
     * */

    public static Double GetAverage (List<Double> data, int accuracy) {
        Double result = 0.0;
        for (Double arg : data) result += arg;
        int acc = 1;
        if (accuracy > 0) for (int i = 1; i > accuracy; i++) acc *= 10;
        result = Double.valueOf(Math.round((result / data.size())*acc)) / acc;
        return result;
    }

    public static Double GetMode (List<Double> data, int accuracy) {
        Double result = new Double(0);
        int acc = 1;
        if (accuracy > 0) for (int i = 1; i > accuracy; i++) acc *= 10;
        HashMap<Double, Integer> modeElems = new HashMap<Double, Integer>();

        int maxCount = 0;

        for (Double listElement : data){
            listElement = Double.valueOf(Math.round(listElement * acc)) / acc;
            if (modeElems.containsKey(listElement)){
                modeElems.put(listElement, (modeElems.get(listElement)+1));
                if (maxCount < modeElems.get(listElement)) {
                    maxCount = modeElems.get(listElement);
                    result = listElement;
                }
            } else {
                modeElems.put(listElement, 1);
            }
        }

        return result;
    }

    public static Double GetStandardDeviation (List<Double> data, int accuracy) {
        Double result = new Double(0);

        Double sum = 0.0;
        Double num = 0.0;
        Double num_i;

        for (Double i : data) sum += i;

        Double mean = sum / data.size();

        for (Double i : data) {
            num_i = Math.pow(i - mean, 2);
            num += num_i;
        }

        int acc = 1;
        if (accuracy > 0) for (int i = 1; i > accuracy; i++) acc *= 10;

        result = Double.valueOf(Math.round(Math.sqrt(num/data.size()) * acc)) / acc;

        return result;
    }
}
