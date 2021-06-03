package src.virtuallondon;

import java.util.Random;

public enum Gender {
    MALE("мужской"),
    FEMALE("женский"),
    UNBINARY("небинарнвый");

    String representation;

    Gender(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }

    public static Gender getFromString(String key) {
        return switch (key) {
            case "мужской", "м", "male", "masculine" -> MALE;
            case "женский", "ж", "female", "feminine" -> FEMALE;
            case "неопределённый", "с", "unbinary", "neuter" -> UNBINARY;
            default -> Gender.values()[new Random().nextInt(Gender.values().length)];
        };
    }
}
