package virtuallondon;

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
//        switch (key) {
//            case "мужской":
//            case "м":
//            case "male":
//            case "masculine":
//                return MALE;
//            case "женский":
//            case "ж":
//            case "female":
//            case "feminine":
//                return FEMALE;
//            case "неопределённый":
//            case "с":
//            case "unbinary":
//            case "neuter":
//                return UNBINARY;
//            default:
//                return Gender.values()[new Random().nextInt(Gender.values().length)];
//        }
    }
}
