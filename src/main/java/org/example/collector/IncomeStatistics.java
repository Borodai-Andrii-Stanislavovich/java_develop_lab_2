package org.example.collector;

/**
 * Holds statistical data for a numeric income field.
 * <p>
 * Contains minimum, maximum, average and standard deviation values.
 */
public class IncomeStatistics {

    /** Minimum observed income. */
    public int min;

    /** Maximum observed income. */
    public int max;

    /** Average income value. */
    public double avg;

    /** Standard deviation of income. */
    public double std;

    /**
     * Constructs a statistics object.
     *
     * @param min minimum income
     * @param max maximum income
     * @param avg average income
     * @param std standard deviation
     */
    public IncomeStatistics(int min, int max, double avg, double std) {
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.std = std;
    }

    @Override
    public String toString() {
        return "IncomeStats{min=%d, max=%d, avg=%.2f, std=%.2f}"
                .formatted(min, max, avg, std);
    }
}
