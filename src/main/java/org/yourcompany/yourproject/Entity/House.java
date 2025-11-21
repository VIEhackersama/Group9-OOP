package org.yourcompany.yourproject.Entity;

import ml.dmlc.xgboost4j.java.*;
import java.util.*;

public interface RealEstate {
    double predictPrice() throws Exception;
}


public class House implements RealEstate {

    private String id;
    private Double area;
    private String address;
    private Double streetInFrontOfHouse;
    private Double width;
    private Double height;
    private Integer floorNumber;
    private Integer bedroomNumber;
    private Integer bathroomNumber;
    private String direction;
    private Integer law;
    private Double price;
    private Double room_density;
    private Double bath_per_bed;
    private Double wide_ratio;      // ti le chieu ngang voi chieu dai
    private Double distance_center;   // quang duong den trung tam thanh pho
    private static final Map<String, Integer> tierAddress = new HashMap<>();
    private static final Map<String, Integer> tierDirection = new HashMap<>();
    
    public House() {
    }

    public House(String address, Double streetInFrontOfHouse, Double width,
                 Double height, Integer floorNumber, Integer bedroomNumber,
                 Integer bathroomNumber, String direction, Double distance_center) {

        this.area = width * height;
        this.address = address;
        this.streetInFrontOfHouse = streetInFrontOfHouse;
        this.width = width;
        this.height = height;
        this.floorNumber = floorNumber;
        this.bedroomNumber = bedroomNumber;
        this.bathroomNumber = bathroomNumber;
        this.direction = direction;
        this.room_density = (double) this.bedroomNumber / this.area;
        this.bath_per_bed = this.bathroomNumber / (double)(this.bedroomNumber + 1);
        this.wide_ratio = this.width / this.height;
        this.distance_center = distance_center;
    }

    // --- CÁC GETTER/SETTER CŨ (Giữ nguyên) ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getArea() {
        return area;
    }

    private void recalcArea() {
        this.area = this.width * this.height;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getStreetInFrontOfHouse() {
        return streetInFrontOfHouse;
    }

    public void setStreetInFrontOfHouse(Double streetInFrontOfHouse) {
        this.streetInFrontOfHouse = streetInFrontOfHouse;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
        recalcArea();
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
        recalcArea();
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Integer bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Integer getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Integer bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getRoom_density() {
        return room_density;
    }

    public Double getBath_per_bed() {
        return bath_per_bed;
    }

    public Double getWide_ratio() {
        return wide_ratio;
    }

    public Double getDistance_center() {
        return distance_center;
    }

    public void setDistance_center(Double distance_center) {
        this.distance_center = distance_center;
    }

    @Override
    public String toString() {
        return "House{" +
                "area=" + area +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }


    // chuyen doi address va direction sang int (Minh)
    static {
        tierAddress.put("Phường Ba Đình", 1);
        tierAddress.put("Phường Đống Đa", 1);
        tierAddress.put("Phường Bạch Mai", 1);
        tierAddress.put("Phường Hai Bà Trưng", 1);

        tierAddress.put("Phường Kim Liên", 2);
        tierAddress.put("Phường Cầu Giấy", 2);
        tierAddress.put("Phường Long Biên", 2);

        tierAddress.put("Phường Thanh Xuân", 3);
        tierAddress.put("Phường Thanh Xuân Nam", 3);
        tierAddress.put("Phường Thanh Xuân Bắc", 3);
        tierAddress.put("Phường Thanh Xuân Trung", 3);
        tierAddress.put("Phường Thượng Đình", 3);
        tierAddress.put("Phường Nghĩa Đô", 3);

        tierAddress.put("Phường Vĩnh Tuy", 4);
        tierAddress.put("Phường Tương Mai", 4);
        tierAddress.put("Phường Lĩnh Nam", 4);
        tierAddress.put("Phường Hoàng Mai", 4);
        tierAddress.put("Phường Cầu Diễn", 4);
        tierAddress.put("Phường Từ Liêm", 4);
        tierAddress.put("Phường Việt Hưng", 4);
        tierAddress.put("Phường Định Công", 4);
        tierAddress.put("Phường Khương Mai", 4);
        tierAddress.put("Phường Xuân Phương", 4);
        tierAddress.put("Phường Phúc Lợi", 4);
        tierAddress.put("Phường Phú Diễn", 4);
        tierAddress.put("Phường Khương Đình", 4);

        tierAddress.put("Phường Yên Sở", 5);
        tierAddress.put("Phường Kiến Hưng", 5);
        tierAddress.put("Phường Hoàng Liệt", 5);
        tierAddress.put("Phường Vĩnh Hưng", 5);
        tierAddress.put("Phường Dương Nội", 5);
        tierAddress.put("Xã Hoài Đức", 5);
        tierAddress.put("Xã Bình Minh", 5);
        tierAddress.put("Phường Xuân Canh", 5);
        tierAddress.put("Phường Hà Đông", 5);
    }

    static {
        tierDirection.put("Không xác định", 0);

        tierDirection.put("Tây - Bắc", 1);
        tierDirection.put("Bắc", 1);
        tierDirection.put("Tây", 1);

        tierDirection.put("Đông - Bắc", 2);
        tierDirection.put("Tây - Nam", 2);

        tierDirection.put("Đông", 3);
        tierDirection.put("Nam", 3);
        tierDirection.put("Đông - Nam", 3);
    }


    public static int addresstoint(String address) {
        return tierAddress.getOrDefault(address, -1);
    }

    public static int directiontoint(String direction) {
        return tierDirection.getOrDefault(direction, -1);
    }
    
    // Dự đoán giá nhà (Minh)-------------------------------------------------
     @Override
    public double predictPrice() throws Exception {

        Booster booster = XGBoost.loadModel("house_price.json");

        float[][] input = new float[][]{
            {    
                this.area.floatValue(),
                addresstoint(this.address),                    // address
                this.streetInFrontOfHouse.floatValue(),// street
                this.width.floatValue(),               // width
                this.height.floatValue(),              // height
                this.floorNumber.floatValue(),         // floor
                this.bedroomNumber.floatValue(),       // bedroom
                this.bathroomNumber.floatValue(),      // bathroom
                directiontoint(this.direction),                  // direction
                this.room_density.floatValue(),
                this.bath_per_bed.floatValue(),
                this.wide_ratio.floatValue(),
                this.distance_center.floatValue()
            }
        };
        DMatrix dmatrix = new DMatrix(input, 1, input[0].length);
        float[][] preds = booster.predict(dmatrix);

        this.price = (double) preds[0][0];
        return this.price;
    }

    
}
