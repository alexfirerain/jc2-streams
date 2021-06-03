package src.virtuallondon;

import java.util.Random;

public enum Education {
    NONE("отсутствует"),
    ELEMENTARY("начальное"),
    SECONDARY("среднее общее"),
    FURTHER("среднее полное"),
    HIGHER("высшее");

    String representation;

    Education(String representation) {
        this.representation = representation;
    }

    public static Education getFromString(String key) {
        return switch (key) {
            case "отсутствует", "-", "нет", "none" -> NONE;
            case "начальное", "три класса", "elementary" -> ELEMENTARY;
            case "среднее общее", "восьмилетка", "secondary" -> SECONDARY;
            case "среднее полное", "ПТУ", "средняя школа", "further" -> FURTHER;
            case "вуз", "универ", "высшее", "higher" -> HIGHER;
            default -> Education.values()[new Random().nextInt(Education.values().length)];
        };
    }
}
