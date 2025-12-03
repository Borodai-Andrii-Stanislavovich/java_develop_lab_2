package org.example.analysis;

import org.example.model.Person;

import java.util.*;

/**
 * Performs outlier detection using the IQR (interquartile range) rule.
 * <p>
 * Based on monthly income (field Г) of {@link Person}.
 */
public class OutlierAnalyzer {

    /**
     * Analyzes income values and returns the number of normal data points
     * and outliers using the classical 1.5 * IQR rule.
     *
     * @param people list of persons to analyze
     * @return map of the form { "data": X, "outliers": Y }
     */
    public static Map<String, Long> analyze(List<Person> people) {

        List<Integer> incomes = people.stream()
                .map(Person::getMonthlyIncome)
                .sorted()
                .toList();

        int q1Index = incomes.size() / 4;
        int q3Index = incomes.size() * 3 / 4;

        double q1 = incomes.get(q1Index);
        double q3 = incomes.get(q3Index);
        double iqr = q3 - q1;

        double lower = q1 - 1.5 * iqr;
        double upper = q3 + 1.5 * iqr;

        long outliers = incomes.stream().filter(i -> i < lower || i > upper).count();
        long data = incomes.size() - outliers;

        return Map.of(
                "data", data,
                "outliers", outliers
        );
    }
}
