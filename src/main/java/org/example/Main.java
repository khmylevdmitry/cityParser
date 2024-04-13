package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String csvFilePath = "src/main/resources/cities_list.csv";
        List<City> cities = readCitiesFromCSV(csvFilePath);

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
                String[] parts = line.split(";"); // Разделение строки по запятой

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

