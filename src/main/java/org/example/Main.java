package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String csvFilePath = "src/main/resources/cities_list.csv";
        List<City> cities = readCitiesFromCSV(csvFilePath);
        printCities(cities);

        sortByCityNameDescending(cities);
        printCities(cities);

        sortByFederalDistrictAndCityNameDescending(cities);
        printCities(cities);

        findCityWithMaxPopulation(cities);

        Map<String, Integer> cityCountByRegion = countCitiesByRegion(cities);

        for (Map.Entry<String, Integer> entry : cityCountByRegion.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

    }

    public static Map<String, Integer> countCitiesByRegion(List<City> cities) {
        Map<String, Integer> cityCountByRegion = new HashMap<>();
        for (City city : cities) {
            String region = city.getRegion();
            cityCountByRegion.put(region, cityCountByRegion.getOrDefault(region, 0) + 1);
        }

        return cityCountByRegion;
    }

    public static void findCityWithMaxPopulation(List<City> cities) {

        City[] citiesArray = cities.toArray(new City[cities.size()]);
        int maxPopulation = 0;
        int maxPopulationIndex = -1;

        for (int i = 0; i < citiesArray.length; i++) {
            if (citiesArray[i].getPopulation() > maxPopulation) {
                maxPopulation = citiesArray[i].getPopulation();
                maxPopulationIndex = i;
            }
        }
            System.out.println("[" + maxPopulationIndex + "] = " + maxPopulation);
    }

    public static void sortByCityNameDescending(List<City> cities) {
        cities.sort(Comparator.comparing(City::getName, Comparator.reverseOrder()));
    }

    public static void sortByFederalDistrictAndCityNameDescending(List<City> cities) {
        cities.sort(
                Comparator.comparing(City::getDistrict)
                        .thenComparing(City::getName, Comparator.reverseOrder())
        );
    }

    private static void printCities(List<City> cities) {
        for (City city : cities) {
            System.out.println(city);
        }

    }

    private static List<City> readCitiesFromCSV(String filePath) {
        List<City> cities = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                List<String> dynamicList = new ArrayList<>();
                String line = scanner.nextLine();
                String[] parts = line.split(";");

                for (int i = 0; i < parts.length; i++) {
                    dynamicList.add(parts[i]);
                }

                if (dynamicList.size() < 6) {
                    dynamicList.add("");
                }

                City city = new City(dynamicList.get(1), dynamicList.get(2),
                        dynamicList.get(3), Integer.parseInt(dynamicList.get(4)),
                        dynamicList.get(5));

                cities.add(city);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cities;
    }

}

