package src.plainvsstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4);
        System.out.println(numbers);

        List<Integer> posNumbers = new ArrayList<>();
        for (Integer n : numbers)
            if (n > 0) posNumbers.add(n);
        System.out.println(posNumbers);

        List<Integer> evenPosNumbers = new ArrayList<>();
        for (Integer n : posNumbers)
            if (n % 2 == 0) evenPosNumbers.add(n);
        System.out.println(evenPosNumbers);

        evenPosNumbers.sort(Comparator.naturalOrder());
        System.out.println(evenPosNumbers);
    }
}
