package com.diplom.naive;


import java.util.concurrent.Callable;

public class CallableWorker implements Callable<Double> {

    private String clazz;
    private String attrs;
    private Learning learning;

    public CallableWorker(String clazz, String attrs, Learning learning) {
        this.clazz = clazz;
        this.attrs = attrs;
        this.learning = learning;
    }

    public Double call() throws Exception {
        return learning.getClassifier().calculateProbability(clazz, attrs);
    }
}
