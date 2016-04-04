package com.diplom.naive;

import java.util.Map;
import java.util.Set;

public class Model {

    private Map<String, Integer> objCountMap;
    private Map<String, Map<String, Integer>> attrMap;
    private Integer dictionarySize;

    /**
     * Модель классификатора
     *
     * @param objCountMap количество объектов по класам
     * @param attrMap статистика по аттрибутам в пределах классов
     * @param dictionarySize размер словаря по аттрибутам обуающей выборки
     */
    public Model(Map<String, Integer> objCountMap, Map<String, Map<String, Integer>> attrMap, Integer dictionarySize) {
        this.objCountMap = objCountMap;
        this.attrMap = attrMap;
        this.dictionarySize = dictionarySize;
    }

    private Integer getOrElse(String clazz, String attr, Integer defValue) {
        return attrMap.get(clazz).get(attr) != null ? attrMap.get(clazz).get(attr) : defValue;
    }

    /**
     * @param clazz класс
     * @param attr слово
     * @return логарифм оценки <code>P(w|c)</code> — вероятности атрибута в пределах класса
     */
    public Double getAttrLogProbability(String clazz, String attr) {
        Double res = Math.log((getOrElse(clazz, attr, 0) + 1.0) / dictionarySize);
        return res;
    }

    /**
     * @param clazz класс
     * @return логарифм априорной вероятности класса <code>P(c)</code>
     */
    public Double getClassLogProbobility(String clazz) {
        Integer classCountsSum = 0;
        for (Integer value : objCountMap.values()) {
            classCountsSum += value;
        }
        Double res = Math.log(Double.valueOf(objCountMap.get(clazz)) / (classCountsSum != 0 ? classCountsSum : 1));
        return res;
    }


    /**
     * @return множество всех классов
     */
    public Set<String> getClasses() {
        return objCountMap.keySet();
    }
}
