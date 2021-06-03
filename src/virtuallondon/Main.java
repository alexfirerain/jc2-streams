package src.virtuallondon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static src.virtuallondon.Education.*;
import static src.virtuallondon.Gender.*;

/** Подразумевается чтение статистики из файла формата psf (population statistics file)
 * каждая строка которого состоит из термина (одно слово) и (через пробел) его частотности
 * на любую выборку, размер которой в итоге принимается равным сумме всех частоностей в файле.
 * Символ '#' в начале строки помечает её как комментарий.
 */
public class Main {
    public static void main(String[] args) {

        Collection<Person> virtualLondon = generateCityBase(10_000_000, 100,
                "res\\stat\\maleNames.psf",
                "res\\stat\\femaleNames.psf",
                "res\\stat\\surnames.psf",
                "res\\stat\\genders.psf",
                "res\\stat\\educations.psf");

        System.out.println("Количество лиц младше 18 лет: " +
                virtualLondon.stream().filter(who -> who.getAge() < 18).count());

        List<String> conscripts = virtualLondon.stream().
                                    filter(who -> who.getAge() > 17 && who.getAge() < 27).
                                    filter(who -> who.getGender() == MALE).
                                    map(Person::getSurname).
                                    collect(Collectors.toList());
        System.out.println("Количество лиц мужского пола старше 17 и младше 27: " + conscripts.size());
        TreeMap<String, Integer> conscriptsAbstract = new TreeMap<>();
        for (String surname : conscripts)
            if (!conscriptsAbstract.containsKey(surname)) conscriptsAbstract.put(surname, 1);
            else conscriptsAbstract.put(surname, conscriptsAbstract.get(surname) + 1);
        System.out.println("Их фамилии:");
        for (Map.Entry<String, Integer> surname : conscriptsAbstract.entrySet())
            System.out.println(surname.getKey() + " (" + surname.getValue() + ")");

        List<Person> employables = virtualLondon.stream().
                filter(who -> who.getEducation() == HIGHER).
                filter(who -> who.getAge() > 16).
                filter(who -> who.getGender() == FEMALE ? who.getAge() < 60 : who.getAge() < 65).
                sorted(Comparator.comparing(Person::getSurname)).
                collect(Collectors.toList());
        System.out.println("Количество лиц трудоспособного возраста с высшим образованием: " + employables.size());
    }


    private static Collection<Person> generateCityBase(int size, int maxAge,
                                                 String maleNameStatFile, String femaleNameStatFile, String surnameStatFile,
                                                 String genderStatFile, String educationStatFile) {
        System.out.printf("Генерация базы населения объёмом %d человек возраста до %d лет на основе статистических данных из:%n" +
                        "%s, %s, %s, %s и %s:%n",
                size, maxAge, maleNameStatFile, femaleNameStatFile, surnameStatFile, genderStatFile, educationStatFile);

        TreeMap<Integer, String>
                maleNameDispersion = buildMapOnStatFile(maleNameStatFile),
                femaleNameDispersion = buildMapOnStatFile(femaleNameStatFile),
                surnameDispersion = buildMapOnStatFile(surnameStatFile),
                genderDispersion = buildMapOnStatFile(genderStatFile),
                educationDispersion = buildMapOnStatFile(educationStatFile);

        Collection<Person> virtualCity = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            int age = new Random().nextInt(maxAge);
            Gender gender = Gender.getFromString(pickDatumOnStat(genderDispersion));
            Education education = Education.getFromString(pickDatumOnStat(educationDispersion));
            Person johnDoe = new Person("John", "Doe", gender, age, education);
            if (johnDoe.isADoubtfulPerson()) {
                i--;
                continue;
            }
            johnDoe.setName(switch (gender) {
                case MALE -> pickDatumOnStat(maleNameDispersion);
                case FEMALE -> pickDatumOnStat(femaleNameDispersion);
                case UNBINARY -> pickDatumOnStat(Math.random() > 0.5 ? maleNameDispersion : femaleNameDispersion);
            });
            johnDoe.setSurname(pickDatumOnStat(surnameDispersion));
            virtualCity.add(johnDoe);

            if ((i + 1) % (size / 500) == 0) System.out.print(".");
            if ((i + 1) % (size / 10) == 0) System.out.println();
        }
        System.out.println("Генерация базы данных завершена");
        return virtualCity;
    }

    private static TreeMap<Integer, String> buildMapOnStatFile(String statFile) {
        TreeMap<Integer, String> statBase = new TreeMap<>();
        try (Scanner reader = new Scanner(new File(statFile))) {
            int total = 0;
            while (reader.hasNext()) {
                String[] line = reader.nextLine().trim().split(" ");    // завязано на формат psf
                if (line.length != 2 || line[0].startsWith("#")) continue;
                int value;
                try {
                    value = Integer.parseInt(line[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }
                total += value;
                statBase.put(total, line[0].trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + statFile + " не найден");
        }
        return statBase;
    }

    private static String pickDatumOnStat(TreeMap<Integer, String> statBase) {
        return statBase.get(
                statBase.ceilingKey(
                        new Random().nextInt(
                                statBase.lastKey()
                        )
                )
        );
    }

}
