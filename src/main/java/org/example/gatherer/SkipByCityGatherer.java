package org.example.gatherer;

import org.example.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Simple Gatherer-like class for collecting elements from stream.
 */
public class SkipByCityGatherer {

    /**
     * Processes an infinite stream: takes only residents of specified city,
     * skips first N, then takes up to limit elements.
     *
     * @param stream     infinite stream of persons
     * @param targetCity city to filter by (e.g., "Kyiv")
     * @param skipCount  how many matching persons to skip
     * @param limit      maximum number of persons to collect
     * @return list of collected persons
     */
    public static List<Person> gather(Stream<Person> stream, String targetCity, int skipCount, int limit) {
        State state = new State(skipCount, limit);

        // Використовуємо iterator для обробки потоку
        stream.takeWhile(person -> accumulate(state, person, targetCity))
                .forEach(person -> {}); // Просто споживаємо потік

        return state.output;
    }

    /**
     * Alternative: Works with existing list instead of stream
     */
    public static List<Person> gather(List<Person> persons, String targetCity, int skipCount, int limit) {
        State state = new State(skipCount, limit);

        for (Person person : persons) {
            if (!accumulate(state, person, targetCity)) {
                break;
            }
        }

        return state.output;
    }

    private static boolean accumulate(State state, Person person, String targetCity) {
        // 1. Фільтруємо ТІЛЬКИ людей з потрібного міста
        if (!person.getCity().equals(targetCity)) {
            return true; // пропускаємо, але продовжуємо обробку
        }

        // 2. Пропускаємо перші N з потрібного міста
        if (state.skipped < state.toSkip) {
            state.skipped++;
            return true; // пропустили, продовжуємо
        }

        // 3. Додаємо до вихідного списку, якщо ще менше limit
        if (state.output.size() < state.limit) {
            state.output.add(person);
            return true; // додали, продовжуємо
        }

        // 4. Якщо вже досягли limit — зупиняємо збір
        return false;
    }

    /**
     * Внутрішній стан збирача
     */
    private static class State {
        final int toSkip;
        final int limit;
        int skipped = 0;
        List<Person> output = new ArrayList<>();

        State(int toSkip, int limit) {
            this.toSkip = toSkip;
            this.limit = limit;
        }
    }
}