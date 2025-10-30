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

    public House() {
    }

    public House(Integer area, String address, Integer streetInFrontOfHouse, Double width,
            Integer height, Integer floorNumber, Integer bedroomNumber,
            Integer bathroomNumber, String direction, Integer law, Double price) {
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
    }

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
    
    @Override
    public String toString() {
        return "House{" +
                "area=" + area +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}