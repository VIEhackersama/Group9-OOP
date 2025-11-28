package org.yourcompany.yourproject.Entity;

public class PricePrediction {
    private int id;
    private String productName;     // Tên sản phẩm (Ví dụ: Nhà, Đất, Bất động sản khác...)
    private double currentPrice;    // Giá hiện tại
    private double predictedPrice;  // Giá dự đoán
    private String predictionDate;  // Ngày tạo dự đoán
    private int userId;             // Ai là người dự đoán
    private String note;            // Ghi chú/Lý do

    public PricePrediction() {
    }

    public PricePrediction(int id, String productName, double currentPrice, double predictedPrice, String predictionDate, int userId, String note) {
        this.id = id;
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.predictedPrice = predictedPrice;
        this.predictionDate = predictionDate;
        this.userId = userId;
        this.note = note;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPredictedPrice() {
        return predictedPrice;
    }

    public void setPredictedPrice(double predictedPrice) {
        this.predictedPrice = predictedPrice;
    }

    public String getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(String predictionDate) {
        this.predictionDate = predictionDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
