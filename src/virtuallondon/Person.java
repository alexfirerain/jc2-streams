package src.virtuallondon;

import static src.virtuallondon.Education.*;
import static src.virtuallondon.Gender.*;

public class Person {
    private String name, surname;
    private Gender gender;
    private int age;
    private Education education;

    public Person(String name, String surname, Gender gender, int age, Education education) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.education = education;
    }

    public boolean isADoubtfulPerson() {
        return age < 10 && education != NONE ||
               age < 15 && education.ordinal() > ELEMENTARY.ordinal() ||
               age < 18 && education.ordinal() > SECONDARY.ordinal() ||
               age < 23 && education == HIGHER ||
               age < 14 && gender == UNBINARY;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d, %s, %s)",
                name, surname, age, gender, education);
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Gender getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public Education getEducation() {
        return education;
    }
}
