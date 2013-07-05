package sk.portugal.leksi.model.extra;

import sk.portugal.leksi.model.enums.ComparisonDegree;

/**
 */
public class Comparison {

    private ComparisonDegree degree;
    private String positive;

    public Comparison(ComparisonDegree degree, String positive) {
        this.degree = degree;
        this.positive = positive;
    }

    public ComparisonDegree getDegree() {
        return degree;
    }

    public String getPositive() {
        return positive;
    }

}
