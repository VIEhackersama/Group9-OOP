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
     * * @param filePath Đường dẫn đến file CSV (phải chứa 15 cột)
     * @return Danh sách các ngôi nhà
     */
    public List<House> loadHousesFromCsv(String filePath) {
        List<House> houses = new ArrayList<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Bỏ qua dòng tiêu đề
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 15) {
                    try {
                        Integer area = Integer.parseInt(values[0].trim());
                        String address = values[1].trim();
                        Double streetInFrontOfHouse = Double.parseDouble(values[2].trim());
                        Double width = Double.parseDouble(values[3].trim());
                        Double height = Double.parseDouble(values[4].trim());
                        Integer floorNumber = Integer.parseInt(values[5].trim());
                        Integer bedroomNumber = Integer.parseInt(values[6].trim());
                        Integer bathroomNumber = Integer.parseInt(values[7].trim());
                        String direction = values[8].trim();
                        Integer law = Integer.parseInt(values[9].trim());
                        Double price = Double.parseDouble(values[10].trim());
                        Double room_density = Double.parseDouble(values[11].trim());
                        Double bath_per_bed = Double.parseDouble(values[12].trim());
                        Double wide_ratio = Double.parseDouble(values[13].trim());
                        Double distance_center = Double.parseDouble(values[14].trim());

                        House house = new House(area, address, streetInFrontOfHouse, width, height,
                                floorNumber, bedroomNumber, bathroomNumber, direction, law, price,
                                room_density, bath_per_bed, wide_ratio, distance_center);

                        houses.add(house);

                    } catch (NumberFormatException e) {
                        System.err.println("Type error (lỗi kiểu dữ liệu): " + line);
                    }
                } else {
                    // Cập nhật thông báo lỗi để rõ ràng hơn
                    System.err.println("Format error (mong đợi 15 cột, nhận được " + values.length + "): " + line);
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
