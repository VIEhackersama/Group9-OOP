package org.yourcompany.yourproject.Entity;

public class House {

    private String id;
    private Integer area;
    private String address;
    private Integer streetInFrontOfHouse;
    private Double width;
    private Integer height;
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

    public House(Integer area, String address, Integer streetInFrontOfHouse, Double width,
                 Integer height, Integer floorNumber, Integer bedroomNumber,
                 Integer bathroomNumber, String direction, Integer law, Double price,
                 Double room_density, Double bath_per_bed, Double wide_ratio, Double distance_center) {

        this.area = area;
        this.address = address;
        this.streetInFrontOfHouse = streetInFrontOfHouse;
        this.width = width;
        this.height = height;
        this.floorNumber = floorNumber;
        this.bedroomNumber = bedroomNumber;
        this.bathroomNumber = bathroomNumber;
        this.direction = direction;
        this.law = law;
        this.price = price;
        this.room_density = room_density;
        this.bath_per_bed = bath_per_bed;
        this.wide_ratio = wide_ratio;
        this.distance_center = distance_center;
    }

    // --- CÁC GETTER/SETTER CŨ (Giữ nguyên) ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStreetInFrontOfHouse() {
        return streetInFrontOfHouse;
    }

    public void setStreetInFrontOfHouse(Integer streetInFrontOfHouse) {
        this.streetInFrontOfHouse = streetInFrontOfHouse;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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

    public Integer getLaw() {
        return law;
    }

    public void setLaw(Integer law) {
        this.law = law;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRoom_density() {
        return room_density;
    }

    public void setRoom_density(Double room_density) {
        this.room_density = room_density;
    }

    public Double getBath_per_bed() {
        return bath_per_bed;
    }

    public void setBath_per_bed(Double bath_per_bed) {
        this.bath_per_bed = bath_per_bed;
    }

    public Double getWide_ratio() {
        return wide_ratio;
    }

    public void setWide_ratio(Double wide_ratio) {
        this.wide_ratio = wide_ratio;
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
}