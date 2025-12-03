package org.example.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a participant in a DOU survey.
 * <p>
 * A person contains basic demographic data such as first name, last name,
 * birthdate, city of residence and monthly income.
 * This class also provides functionality to calculate the person's age.
 */
public class Person {

    /** First name of the person. */
    private final String firstName;

    /** Last name of the person. */
    private final String lastName;

    /** Date of birth of the person. */
    private final LocalDate birthDate;

    /** City where the person currently lives. */
    private final String city;

    /** Monthly income in Ukrainian hryvnias (UAH). */
    private final int monthlyIncome;

    /**
     * Creates a new person with specified attributes.
     *
     * @param firstName      the person's first name
     * @param lastName       the person's last name
     * @param birthDate      the person's date of birth
     * @param city           city of residence
     * @param monthlyIncome  monthly income in UAH
     */
    public Person(String firstName, String lastName, LocalDate birthDate,
                  String city, int monthlyIncome) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.city = city;
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * Calculates the number of full years since the person's date of birth.
     *
     * @return age in full years
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * @return the city where the person resides
     */
    public String getCity() {
        return city;
    }

    /**
     * @return monthly income in UAH
     */
    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     * @return the person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", age=" + getAge()
                + ", income=" + monthlyIncome + ", city=" + city;
    }


}
