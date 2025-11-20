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
     * Phương thức đọc file CSV và trả về danh sách các đối tượng House.
     * Hỗ trợ đọc 15 trường thông tin.
     * * @param filePath Đường dẫn đến file CSV
     * @return Danh sách các ngôi nhà
     */
    public List<House> loadHousesFromCsv(String filePath) {
        List<House> houses = new ArrayList<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Bỏ qua dòng tiêu đề (Header)
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Kiểm tra nếu dòng có đủ 15 cột
                if (values.length == 15) {
                    try {
                        // 1. Area (Integer)
                        Integer area = Integer.parseInt(values[0].trim());
                        
                        // 2. Address (String)
                        String address = values[1].trim();
                        
                        // 3. Street In Front (Double)
                        Double streetInFrontOfHouse = Double.parseDouble(values[2].trim());
                        
                        // 4. Width (Double)
                        Double width = Double.parseDouble(values[3].trim());
                        
                        // 5. Height (Double)
                        Double height = Double.parseDouble(values[4].trim());
                        
                        // 6. Floor (Integer)
                        Integer floorNumber = Integer.parseInt(values[5].trim());
                        
                        // 7. Bedroom (Integer)
                        Integer bedroomNumber = Integer.parseInt(values[6].trim());
                        
                        // 8. Bathroom (Integer)
                        Integer bathroomNumber = Integer.parseInt(values[7].trim());
                        
                        // 9. Direction (String)
                        String direction = values[8].trim();
                        
                        // 10. Law (Integer)
                        Integer law = Integer.parseInt(values[9].trim());
                        
                        // 11. Price (Double)
                        Double price = Double.parseDouble(values[10].trim());

                        // 12. Room Density (Double)
                        Double room_density = Double.parseDouble(values[11].trim());
                        
                        // 13. Bath per Bed (Double)
                        Double bath_per_bed = Double.parseDouble(values[12].trim());
                        
                        // 14. Wide Ratio (Double)
                        Double wide_ratio = Double.parseDouble(values[13].trim());
                        
                        // 15. Distance Center (Double)
                        Double distance_center = Double.parseDouble(values[14].trim());

                        // Tạo đối tượng House
                        House house = new House(area, address, streetInFrontOfHouse, width, height,
                                floorNumber, bedroomNumber, bathroomNumber, direction, law, price,
                                room_density, bath_per_bed, wide_ratio, distance_center);
                        
                        houses.add(house);

                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi sai kiểu dữ liệu ở dòng: " + line);
                    }
                } else {
                    // System.err.println("Lỗi định dạng (thiếu cột): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy file: " + filePath);
        } catch (IOException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }

        return houses;
    }
}