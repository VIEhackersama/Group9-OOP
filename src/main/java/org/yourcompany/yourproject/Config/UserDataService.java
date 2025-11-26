package org.yourcompany.yourproject.Config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Entity.PricePrediction;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class UserDataService {

    // Khai báo các Collection sẽ dùng
    private MongoCollection<User> userCollection;
    private MongoCollection<Document> countersCollection;
    private MongoCollection<PricePrediction> predictionCollection;

    public UserDataService() {
        // 1. Lấy kết nối từ MongoConfig
        this.userCollection = MongoConfig.getUserCollection();
        this.countersCollection = MongoConfig.getCountersCollection();
        this.predictionCollection = MongoConfig.getPredictionCollection(); 

        // 2. Khởi tạo bộ đếm ID cho User nếu chưa có (bắt đầu từ 0)
        if (countersCollection.find(Filters.eq("_id", "userId")).first() == null) {
            countersCollection.insertOne(new Document("_id", "userId").append("seq", 0));
        }

        // 3. Khởi tạo bộ đếm ID cho PricePrediction nếu chưa có (bắt đầu từ 0)
        if (countersCollection.find(Filters.eq("_id", "predictionId")).first() == null) {
            countersCollection.insertOne(new Document("_id", "predictionId").append("seq", 0));
        }
    }

    // =================================================================
    // PHẦN 1: XỬ LÝ USER (Đăng nhập / Đăng ký)
    // =================================================================

    /**
     * Tìm user bằng email (Dùng cho đăng nhập).
     * @param email Email cần tìm.
     * @return Đối tượng User hoặc null.
     */
    public User findUserByEmail(String email) {
        return userCollection.find(Filters.eq("email", email)).first();
    }

    /**
     * Lưu user mới vào database (Dùng cho đăng ký).
     * @param user Đối tượng User.
     */
    public void saveUser(User user) {
        userCollection.insertOne(user);
    }

    /**
     * Lấy ID tiếp theo cho User (ID tự tăng).
     */
    public int getNextUserId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "userId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return counter.getInteger("seq");
    }

    // =================================================================
    // PHẦN 2: XỬ LÝ DỰ ĐOÁN (Lưu lịch sử / Lấy lịch sử)
    // =================================================================

    /**
     * Lưu kết quả dự đoán nhà vào database.
     * @param prediction Đối tượng dự đoán.
     */
    public void savePrediction(PricePrediction prediction) {
        predictionCollection.insertOne(prediction);
    }

    /**
     * Lấy ID tiếp theo cho bảng dự đoán.
     */
    public int getNextPredictionId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "predictionId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true));
        return counter.getInteger("seq");
    }

    /**
     * (Tùy chọn) Lấy danh sách lịch sử dự đoán của một User cụ thể.
     * @param userId ID của người dùng.
     * @return Danh sách các lần dự đoán.
     */
    public List<PricePrediction> getPredictionsByUserId(int userId) {
        List<PricePrediction> list = new ArrayList<>();
        predictionCollection.find(Filters.eq("userId", userId)).into(list);
        return list;
    }
}