package com.diplom.naive;


import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static Learning learning = new Learning();
    private static FileReader fileReader = new FileReader();

    public static void main(String[] args) {

        if (args.length == 4) {
            fileReader.read(args[2], learning);
            Integer threadNum = Integer.parseInt(args[3]);
            ExecutorService executor = Executors.newFixedThreadPool(threadNum);
            Map<String, Future<Double>> futureMap = new HashMap<String, Future<Double>>();
            Long startTime = System.currentTimeMillis();
            //отправляем данные на расчет вероятностей для каждого класса
            for (String clazz : learning.getModel().getClasses()) {
                CallableWorker worker = new CallableWorker(clazz, fileReader.readAttrs(args[0]), learning);
                Future<Double> future = executor.submit(worker);
                futureMap.put(clazz, future);
            }
            //получаем результаты обработки
            Map<String, Double> results = new HashMap<String, Double>();
            for (String clazz : futureMap.keySet()) {
                try {
                    results.put(clazz, futureMap.get(clazz).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            //находим наибольшую верятность
            Pair<String, Double> maxValue = null;
            for (String key : results.keySet()) {
                if (maxValue == null || maxValue.getValue().compareTo(results.get(key)) < 0 ) {
                    maxValue = new Pair<String, Double>(key, results.get(key));
                }
            }
            Long stopTime = System.currentTimeMillis();

            System.out.println("Время работы - " + (stopTime - startTime) + " ms");
            if (maxValue != null) {
                System.out.println("Наиболее вероятный класс - " + maxValue.getKey());
                Double brob = learning.normProb(results, maxValue.getValue());
                System.out.println("Вероятность - " + brob);
            }

        } else {
            System.out.println("Некорректные параметры запуска программы.");
            System.out.println("Необходимо использовать данный формат: <AtrPath> <Object Name> <Data Path> <Threads Number>");
        }
    }
}
