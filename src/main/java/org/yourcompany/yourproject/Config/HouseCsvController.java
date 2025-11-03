package org.yourcompany.yourproject.Config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.yourcompany.yourproject.Entity.House;

public class HouseCsvController {

    /**
     * Phương thức chính để đọc file CSV và trả về danh sách các đối tượng House.
     * 
     * @param filePath Đường dẫn đến file CSV (có thể thay đổi)
     * @return Danh sách các ngôi nhà
     */
    public List<House> loadHousesFromCsv(String filePath) {
        List<House> houses = new ArrayList<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 11) {
                    try {
                        Integer area = Integer.parseInt(values[0].trim());
                        String address = values[1].trim();
                        Integer streetInFrontOfHouse = Integer.parseInt(values[2].trim());
                        Double width = Double.parseDouble(values[3].trim());
                        Integer height = Integer.parseInt(values[4].trim());
                        Integer floorNumber = Integer.parseInt(values[5].trim());
                        Integer bedroomNumber = Integer.parseInt(values[6].trim());
                        Integer bathroomNumber = Integer.parseInt(values[7].trim());
                        String direction = values[8].trim();
                        Integer law = Integer.parseInt(values[9].trim());
                        Double price = Double.parseDouble(values[10].trim());

                        House house = new House(area, address, streetInFrontOfHouse, width, height,
                                floorNumber, bedroomNumber, bathroomNumber, direction, law, price);
                        houses.add(house);

                    } catch (NumberFormatException e) {
                        System.err.println("Type error: " + line);
                    }
                } else {
                    System.err.println("Format error: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the path: " + filePath);
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }

        return houses;
    }
}