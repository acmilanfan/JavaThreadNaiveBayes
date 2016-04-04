package com.diplom.naive;

import javafx.util.Pair;

import java.util.*;

/**
 * Обучающий алгоритм классификации
 */
public class Learning {

    private List<Pair<String, String>> examples = new ArrayList<Pair<String, String>>();

    private String[] tokenize(String str) {
        return str.split(":");
    }

    private Pair<String[], String> tokenizeTuple(Pair<String, String> tuple) {
        return new Pair<String[], String>(tokenize(tuple.getKey()), tuple.getValue());
    }

    /**
     * Добавление примера
     * @param exStr строка
     * @param clazz класс
     */
    public void addExample(String exStr, String clazz) {
        examples.add(new Pair<String, String>(exStr, clazz));
    }

    public Set<String> getDictionary() {
        Set<String> uniqValues = new HashSet<String>();
        for (Pair<String, String> example : examples) {
            Pair<String[], String> tokenized = tokenizeTuple(example);
            Collections.addAll(uniqValues, tokenized.getKey());
            //uniqValues.add(tokenized.getValue());
        }
        return uniqValues;
    }

    private Map<String, Integer> getObjCountMap() {
        Map<String, Integer> objCountsMap = new HashMap<String, Integer>();
        for (Pair<String, String> example : examples) {
            Integer curVal = objCountsMap.get(example.getValue());
            if (curVal == null) {
                curVal = 1;
            } else {
                curVal++;
            }
            objCountsMap.put(example.getValue(), curVal);
        }
        return objCountsMap;
    }

    private Map<String, Map<String, Integer>> getAttrsMap() {
        Map<String, Map<String, Integer>> result = new HashMap<String, Map<String, Integer>>();
        for (Pair<String, String> example : examples) {
            Map<String, Integer> curClassAttrsMap = result.get(example.getValue());
            if (curClassAttrsMap == null) {
                curClassAttrsMap = new HashMap<String, Integer>();
            }
            for (String attr : tokenizeTuple(example).getKey()) {
                Integer curVal = curClassAttrsMap.get(attr);
                if (curVal == null) {
                    curVal = 1;
                } else {
                    curVal++;
                }
                curClassAttrsMap.put(attr, curVal);
            }
            result.put(example.getValue(), curClassAttrsMap);
        }
        return result;
    }

    public Model getModel() {
        return new Model(getObjCountMap(), getAttrsMap(), getDictionary().size());
    }

    public Classifier getClassifier() {
        return new Classifier(getModel());
    }

    public Double normProb(Map<String, Double> probs, Double prob) {
        Double probSumm = 0.0;
        for (Double curProb : probs.values()) {
            probSumm += Math.exp(curProb);
        }
        return Math.exp(prob) / probSumm;
    }
}
