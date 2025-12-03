package org.example.generator;

import org.example.model.Person;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Generates an infinite stream of randomly created {@link Person} objects.
 * <p>
 * Generated values are realistic: ages range from 18 to 57 years,
 * monthly income varies between 20,000 and 100,000 UAH,
 * and cities/names are chosen from predefined lists.
 */
public class PersonGenerator {

    private static final String[] FIRST_NAMES = {
            "Andriy", "Olena", "Ihor", "Oksana", "Taras", "Sofia"
    };

    private static final String[] LAST_NAMES = {
            "Shevchenko", "Ivanov", "Petrenko", "Koval", "Rudenko"
    };

    private static final String[] CITIES = {
            "Kyiv", "Lviv", "Odesa", "Dnipro", "Kharkiv"
    };

    private static final Random r = new Random();

    /**
     * Creates an infinite stream of randomly generated {@link Person} objects.
     *
     * @return infinite {@link Stream} of persons
     */
    public static Stream<Person> infinite() {
        return Stream.generate(() -> new Person(
                FIRST_NAMES[r.nextInt(FIRST_NAMES.length)],
                LAST_NAMES[r.nextInt(LAST_NAMES.length)],
                LocalDate.now().minusYears(18 + r.nextInt(40)),
                CITIES[r.nextInt(CITIES.length)],
                20000 + r.nextInt(80000)
        ));
    }
}
