package org.example;

import org.example.analysis.OutlierAnalyzer;
import org.example.collector.IncomeStatistics;
import org.example.collector.IncomeStatisticsCollector;
import org.example.gatherer.SkipByCityGatherer;
import org.example.generator.PersonGenerator;
import org.example.model.Person;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main application class for DOU survey data analysis.
 * <p>
 * This program performs comprehensive analysis of survey data including:
 * <ul>
 *   <li>Data collection with filtering by city</li>
 *   <li>Age-based filtering</li>
 *   <li>Grouping by first name</li>
 *   <li>Income statistics calculation</li>
 *   <li>Outlier detection using IQR method</li>
 * </ul>
 * <p>
 * The application accepts command-line arguments for customization or uses default values.
 *
 * @author Boroday Andriy Stanislavovych
 * @version 1.0
 * @see Person
 * @see PersonGenerator
 * @see SkipByCityGatherer
 * @see IncomeStatisticsCollector
 * @see OutlierAnalyzer
 */
public class Main {

    /** Default target city for filtering. */
    private static final String DEFAULT_CITY = "Kyiv";

    /** Default number of matching persons to skip. */
    private static final int DEFAULT_SKIP_COUNT = 50;

    /** Default maximum number of persons to collect. */
    private static final int DEFAULT_LIMIT = 500;

    /** Default minimum age for filtering. */
    private static final int DEFAULT_MIN_AGE = 25;

    /** Default maximum age for filtering. */
    private static final int DEFAULT_MAX_AGE = 50;

    /**
     * Main entry point of the DOU survey analysis application.
     * <p>
     * The application performs the following steps:
     * <ol>
     *   <li>Parses command-line arguments or uses default values</li>
     *   <li>Generates infinite stream of random Person objects</li>
     *   <li>Filters and collects persons from specified city</li>
     *   <li>Applies age-based filtering</li>
     *   <li>Groups filtered persons by first name</li>
     *   <li>Calculates income statistics</li>
     *   <li>Performs outlier analysis using IQR method</li>
     * </ol>
     * <p>
     * Command-line arguments:
     * <ul>
     *   <li>{@code -c, --city CITY} - Target city for filtering (default: "Kyiv")</li>
     *   <li>{@code -s, --skip COUNT} - Number of matching persons to skip (default: 50)</li>
     *   <li>{@code -l, --limit COUNT} - Maximum persons to collect (default: 500)</li>
     *   <li>{@code --min-age AGE} - Minimum age for filtering (default: 25)</li>
     *   <li>{@code --max-age AGE} - Maximum age for filtering (default: 50)</li>
     *   <li>{@code -h, --help} - Display help information</li>
     * </ul>
     *
     * @param args command-line arguments for customizing the analysis.
     *             If no arguments provided, default values are used.
     *
     * @throws NumberFormatException if numeric arguments have invalid format
     * @throws IllegalArgumentException if parameters have invalid values (negative counts,
     *         invalid age range, etc.)
     *
     * @see #printHelp() for detailed usage information
     *
     * @example <pre>
     * // Using default values
     * java -jar dou-analyzer.jar
     *
     * // Custom parameters
     * java -jar dou-analyzer.jar --city Lviv --skip 20 --limit 300 --min-age 20 --max-age 40
     *
     * // Short form
     * java -jar dou-analyzer.jar -c Odesa -s 10 -l 150
     * </pre>
     */
    public static void main(String[] args) {

        String city = DEFAULT_CITY;
        int skipCount = DEFAULT_SKIP_COUNT;
        int limit = DEFAULT_LIMIT;
        int minAge = DEFAULT_MIN_AGE;
        int maxAge = DEFAULT_MAX_AGE;

        try {
            if (args.length > 0) {
                System.out.println("=== DOU Survey Analysis ===");
                System.out.println("Using command-line arguments:");

                for (int i = 0; i < args.length; i++) {
                    switch (args[i]) {
                        case "--city":
                        case "-c":
                            if (i + 1 < args.length) {
                                city = args[++i];
                                System.out.println("  City: " + city);
                            }
                            break;
                        case "--skip":
                        case "-s":
                            if (i + 1 < args.length) {
                                skipCount = Integer.parseInt(args[++i]);
                                System.out.println("  Skip first: " + skipCount);
                            }
                            break;
                        case "--limit":
                        case "-l":
                            if (i + 1 < args.length) {
                                limit = Integer.parseInt(args[++i]);
                                System.out.println("  Collect max: " + limit);
                            }
                            break;
                        case "--min-age":
                            if (i + 1 < args.length) {
                                minAge = Integer.parseInt(args[++i]);
                                System.out.println("  Minimum age: " + minAge);
                            }
                            break;
                        case "--max-age":
                            if (i + 1 < args.length) {
                                maxAge = Integer.parseInt(args[++i]);
                                System.out.println("  Maximum age: " + maxAge);
                            }
                            break;
                        case "--help":
                        case "-h":
                            printHelp();
                            return;
                        default:
                            System.out.println("Unknown argument: " + args[i]);
                            printHelp();
                            return;
                    }
                }
            } else {
                // No arguments provided, use defaults
                System.out.println("=== DOU Survey Analysis ===");
                System.out.println("Using default values:");
                System.out.println("  City: " + city);
                System.out.println("  Skip first: " + skipCount);
                System.out.println("  Collect maximum: " + limit);
                System.out.println("  Age range: " + minAge + "-" + maxAge + " years");
                System.out.println("\nTo customize parameters use:");
                System.out.println("  java -jar program.jar --city Kyiv --skip 30 --limit 200 --min-age 20 --max-age 40");
                System.out.println("  or: java -jar program.jar -c Kyiv -s 30 -l 200");
                System.out.println("  --help or -h for help\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: numeric argument has invalid format");
            printHelp();
            return;
        } catch (Exception e) {
            System.err.println("Error processing arguments: " + e.getMessage());
            printHelp();
            return;
        }

        // Validate parameters
        if (skipCount < 0) {
            System.err.println("Error: skip count cannot be negative");
            return;
        }
        if (limit <= 0) {
            System.err.println("Error: limit must be greater than 0");
            return;
        }
        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            System.err.println("Error: invalid age range");
            return;
        }

        try {
            // Step 1: Generate infinite stream of persons
            Stream<Person> stream = PersonGenerator.infinite();

            System.out.println("\n--- Step 1: Data Collection ---");
            System.out.println("Filtering by city: " + city);
            System.out.println("Skipping first " + skipCount + " residents of " + city);

            // Step 2: Collect persons using custom gatherer
            List<Person> people = SkipByCityGatherer.gather(stream, city, skipCount, limit);

            System.out.println("Collected " + people.size() + " residents of " + city +
                    " (skipped first " + skipCount + ")");

            // Step 3: Age-based filtering
            System.out.println("\n--- Step 2: Age Filtering ---");
            System.out.println("Age range: " + minAge + "-" + maxAge + " years");

            int finalMinAge = minAge;
            int finalMaxAge = maxAge;
            List<Person> filtered = people.stream()
                    .filter(p -> p.getAge() >= finalMinAge && p.getAge() <= finalMaxAge)
                    .collect(Collectors.toList());

            System.out.println("After age filtering: " + filtered.size() + " persons");

            // Step 4: Grouping by first name
            System.out.println("\n--- Step 3: Grouping ---");
            Map<String, List<Person>> groupedByName = filtered.stream()
                    .collect(Collectors.groupingBy(Person::getFirstName));

            System.out.println("Grouped by first name into " + groupedByName.size() + " groups");

            // Display top 5 groups by size
            groupedByName.entrySet().stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                    .limit(5)
                    .forEach(entry ->
                            System.out.println("  " + entry.getKey() + ": " + entry.getValue().size() + " persons")
                    );

            // Step 5: Income statistics calculation
            System.out.println("\n--- Step 4: Income Statistics ---");
            IncomeStatistics stats = filtered.stream()
                    .collect(new IncomeStatisticsCollector());

            System.out.println("Income Statistics: " + stats);

            // Step 6: Outlier analysis
            System.out.println("\n--- Step 5: Outlier Analysis (IQR method) ---");
            Map<String, Long> outliers = OutlierAnalyzer.analyze(filtered);
            System.out.println("Normal values: " + outliers.get("data"));
            System.out.println("Outliers: " + outliers.get("outliers"));

            double outlierPercentage = !filtered.isEmpty() ?
                    (outliers.get("outliers") * 100.0 / filtered.size()) : 0;
            System.out.printf("Outlier percentage: %.2f%%\n", outlierPercentage);

            // Display sample data
            System.out.println("\n=== Sample of Collected Data (first 5) ===");
            if (!people.isEmpty()) {
                people.stream().limit(5).forEach(p ->
                        System.out.println("  " + p.getFirstName() + " " + p.getLastName() +
                                ", age: " + p.getAge() +
                                ", income: " + p.getMonthlyIncome() + " UAH" +
                                ", city: " + p.getCity())
                );
            } else {
                System.out.println("  No data found");
            }

            System.out.println("\n=== Analysis Complete ===");

        } catch (Exception e) {
            System.err.println("Error during program execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays help information for command-line usage.
     * <p>
     * Prints detailed information about available command-line arguments,
     * their purpose, default values, and usage examples.
     */
    private static void printHelp() {
        System.out.println("\nUsage: java -jar program.jar [arguments]");
        System.out.println("\nArguments:");
        System.out.println("  -c, --city CITY        City for filtering (default: " + DEFAULT_CITY + ")");
        System.out.println("  -s, --skip COUNT       Number to skip (default: " + DEFAULT_SKIP_COUNT + ")");
        System.out.println("  -l, --limit COUNT      Maximum to collect (default: " + DEFAULT_LIMIT + ")");
        System.out.println("      --min-age AGE      Minimum age (default: " + DEFAULT_MIN_AGE + ")");
        System.out.println("      --max-age AGE      Maximum age (default: " + DEFAULT_MAX_AGE + ")");
        System.out.println("  -h, --help             Show this help message");
        System.out.println("\nExamples:");
        System.out.println("  java -jar program.jar --city Lviv --skip 20 --limit 300");
        System.out.println("  java -jar program.jar -c Odesa -s 10 -l 150 --min-age 30 --max-age 60");
        System.out.println("  java -jar program.jar                    # use default values");
    }
}

