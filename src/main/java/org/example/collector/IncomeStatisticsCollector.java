package org.example.collector;

import org.example.model.Person;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A custom {@link Collector} that accumulates monthly income values
 * of {@link Person} objects and produces an {@link IncomeStatistics} object
 * containing min, max, average and standard deviation.
 */
public class IncomeStatisticsCollector
        implements Collector<Person, List<Integer>, IncomeStatistics> {

    @Override
    public Supplier<List<Integer>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Integer>, Person> accumulator() {
        return (list, p) -> list.add(p.getMonthlyIncome());
    }

    @Override
    public BinaryOperator<List<Integer>> combiner() {
        return (l1, l2) -> { l1.addAll(l2); return l1; };
    }

    @Override
    public Function<List<Integer>, IncomeStatistics> finisher() {
        return incomes -> {
            int min = incomes.stream().min(Integer::compareTo).orElse(0);
            int max = incomes.stream().max(Integer::compareTo).orElse(0);
            double avg = incomes.stream().mapToInt(i -> i).average().orElse(0);

            double std = Math.sqrt(
                    incomes.stream()
                            .mapToDouble(i -> Math.pow(i - avg, 2))
                            .sum() / incomes.size()
            );

            return new IncomeStatistics(min, max, avg, std);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
