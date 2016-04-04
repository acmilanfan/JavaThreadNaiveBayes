package com.diplom.naive;

public class Classifier {

    private Model model;

    /**
     * Алгоритм классификации
     * @param model статистическая модель классификатора
     */
    public Classifier(Model model) {
        this.model = model;
    }

    /**
     * Разбивает строку на аттрибуты
     * @param str строка
     * @return
     */
    private String[] tokenize(String str) {
        return str.split(":");
    }

    /**
     * Рассчитывает оценку вероятности в пределах класса
     * @param clazz класс
     * @param str строка
     * @return оценка <code>P(c|d)</code>
     */
    public Double calculateProbability(String clazz, String str) {
        Double attrProbSum = 0.0;
        for (String attr : tokenize(str)) {
            attrProbSum += model.getAttrLogProbability(clazz, attr);
        }
        return attrProbSum + model.getClassLogProbobility(clazz);
    }
}
