package org.yourcompany.yourproject.Entity;

public class House {
    private double area; // dien_tich
    private String address; // dia_chi
    private String description; // mo_ta
    private int floorNumber; // so_lau (so_tang)
    private int bedroomNumber; // so_phong_ngu
    private boolean isDinningRoom; // co_phong_an?
    private boolean isKitchen; // co_bep?
    private boolean isTerrace; // co_san_thuong?
    private boolean isCarPack; // co_cho_de_xe_hoi?
    private boolean isOwner; // chinh_chu?
    private String type; // nha_mat_tien / nha_trong_hem
    private String direction; // phuong_huong_nha (nam, bac, dong, tay)
    private int streetInFrontOfHouse; // do_rong_duong_truoc_nha
    private String width; // chieu_dai
    private String height; // chieu_rong
    private String law; // phap_ly
    public House(){};
    public House(double area, String address, String description, int floorNumber, int bedroomNumber,
            boolean isDinningRoom, boolean isKitchen, boolean isTerrace, boolean isCarPack,
            boolean isOwner, String type, String direction, int streetInFrontOfHouse,
            String width, String height, String law) {
        this.area = area;
        this.address = address;
        this.description = description;
        this.floorNumber = floorNumber;
        this.bedroomNumber = bedroomNumber;
        this.isDinningRoom = isDinningRoom;
        this.isKitchen = isKitchen;
        this.isTerrace = isTerrace;
        this.isCarPack = isCarPack;
        this.isOwner = isOwner;
        this.type = type;
        this.direction = direction;
        this.streetInFrontOfHouse = streetInFrontOfHouse;
        this.width = width;
        this.height = height;
        this.law = law;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(int bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public boolean isDinningRoom() {
        return isDinningRoom;
    }

    public void setDinningRoom(boolean dinningRoom) {
        isDinningRoom = dinningRoom;
    }

    public boolean isKitchen() {
        return isKitchen;
    }

    public void setKitchen(boolean kitchen) {
        isKitchen = kitchen;
    }

    public boolean isTerrace() {
        return isTerrace;
    }

    public void setTerrace(boolean terrace) {
        isTerrace = terrace;
    }

    public boolean isCarPack() {
        return isCarPack;
    }

    public void setCarPack(boolean carPack) {
        isCarPack = carPack;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getStreetInFrontOfHouse() {
        return streetInFrontOfHouse;
    }

    public void setStreetInFrontOfHouse(int streetInFrontOfHouse) {
        this.streetInFrontOfHouse = streetInFrontOfHouse;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }
}