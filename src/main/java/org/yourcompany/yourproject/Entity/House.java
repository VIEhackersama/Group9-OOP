package org.yourcompany.yourproject.Entity;

import ml.dmlc.xgboost4j.java.*;

interface RealEstate {
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
        this.bath_per_bed = this.bedroomNumber / (double)(this.bedroomNumber + 1);
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

    public void setArea(Double area) {
        this.area = area;
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
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
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


    // Dự đoán giá nhà (Minh)-------------------------------------------------
     @Override
    public double predictPrice() throws Exception {

        Booster booster = XGBoost.loadModel("D:\\java\\group-9\\Group9-OOP\\src\\main\\java\\org\\yourcompany\\yourproject\\Entity\\house_price_json.json");

        // CHÚ Ý: address và direction đã là INTEGER (encode sẵn)
        float[] input = new float[]{
                this.addressToInt(),                    // address
                this.streetInFrontOfHouse.floatValue(),// street
                this.width.floatValue(),               // width
                this.height.floatValue(),              // height
                this.floorNumber.floatValue(),         // floor
                this.bedroomNumber.floatValue(),       // bedroom
                this.bathroomNumber.floatValue(),      // bathroom
                this.directionToInt()                  // direction
        };
         DMatrix dmatrix = new DMatrix(input, 1, 8);
        float[][] preds = booster.predict(dmatrix);

        this.price = (double) preds[0][0];
        return this.price;
    }

    private float addressToInt() {
        if (this.address == null) return 0f;
        return (float) Math.abs(this.address.hashCode() % 10000);
    }

    // Hàm mã hóa Hướng sang số
    private float directionToInt() {
        if (this.direction == null) return 0f;
        String dir = this.direction.trim().toLowerCase();

        switch (dir) {
            case "đông": return 1f;
            case "tây": return 2f;
            case "nam": return 3f;
            case "bắc": return 4f;
            case "đông nam": return 5f;
            case "đông bắc": return 6f;
            case "tây nam": return 7f;
            case "tây bắc": return 8f;
            default: return 0f;
        }
    }
}
