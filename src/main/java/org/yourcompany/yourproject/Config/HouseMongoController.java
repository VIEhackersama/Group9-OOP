package org.yourcompany.yourproject.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import org.yourcompany.yourproject.Entity.House;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HouseMongoController {

    private String connectionString;
    private String databaseName;
    private String collectionName = "nhadat"; 

    public HouseMongoController() {
        loadConfig();
    }

    private void loadConfig() {
        Properties prop = new Properties();
        try (InputStream input = HouseMongoController.class.getResourceAsStream("/db.properties")) {
            prop.load(input);
            this.connectionString = prop.getProperty("mongodb.uri");
            this.databaseName = prop.getProperty("mongodb.database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<House> loadHousesFromDb() {
        List<House> houses = new ArrayList<>();

        if (this.connectionString == null || this.databaseName == null) {
            System.err.println("Chưa cấu hình kết nối Database!");
            return houses;
        }

        try (MongoClient mongoClient = MongoClients.create(this.connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> collection = database.getCollection(this.collectionName);

            // Lấy tất cả documents
            FindIterable<Document> documents = collection.find();

            for (Document doc : documents) {
                try {

                    Integer area = getIntegerSafe(doc, "area");
                    String address = doc.getString("address");

                    Double streetInFrontOfHouse = getDoubleSafe(doc, "street_in_front_of_house");
                    Double width = getDoubleSafe(doc, "width");
                    Double height = getDoubleSafe(doc, "height");

                    Integer floorNumber = getIntegerSafe(doc, "floor_number"); 
                    Integer bedroomNumber = getIntegerSafe(doc, "bedroom_number"); 
                    Integer bathroomNumber = getIntegerSafe(doc, "bathroom_number"); 
                    String direction = doc.getString("direction");
                    Integer law = getIntegerSafe(doc, "law"); 

                    Double price = getDoubleSafe(doc, "price");
                    Double room_density = getDoubleSafe(doc, "room_density");
                    Double bath_per_bed = getDoubleSafe(doc, "bath_per_bed");
                    Double wide_ratio = getDoubleSafe(doc, "wide_ratio");
                    Double distance_center = getDoubleSafe(doc, "distance_center");

                    House house = new House(area, address, streetInFrontOfHouse, width, height,
                            floorNumber, bedroomNumber, bathroomNumber, direction, law, price,
                            room_density, bath_per_bed, wide_ratio, distance_center);

                    houses.add(house);

                } catch (Exception e) {
                    System.err.println("Lỗi khi map dữ liệu document ID: " + doc.get("_id") + " - " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi kết nối MongoDB: " + e.getMessage());
        }

        return houses;
    }
    private Double getDoubleSafe(Document doc, String key) {
        Object value = doc.get(key);
        if (value == null)
            return 0.0;
        if (value instanceof Double)
            return (Double) value;
        if (value instanceof Integer)
            return ((Integer) value).doubleValue();
        if (value instanceof String)
            return Double.parseDouble((String) value);
        return 0.0;
    }
    
    private Integer getIntegerSafe(Document doc, String key) {
        Object value = doc.get(key);
        if (value == null)
            return 0;
        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof Double)
            return ((Double) value).intValue();
        if (value instanceof String)
            return Integer.parseInt((String) value);
        return 0;
    }
}