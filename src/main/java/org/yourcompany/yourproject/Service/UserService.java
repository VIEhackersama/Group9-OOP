package org.yourcompany.yourproject.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Interface.IUserService;
import org.yourcompany.yourproject.Entity.PricePrediction;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private MongoCollection<User> userCollection;
    private MongoCollection<Document> countersCollection;
    private MongoCollection<PricePrediction> predictionCollection;

    public UserService() {
        // 1. Lấy kết nối từ MongoConfig
        this.userCollection = MongoService.getUserCollection();
        this.countersCollection = MongoService.getCountersCollection();
        this.predictionCollection = MongoService.getPredictionCollection(); 

        // Khởi tạo bộ đếm ID cho User nếu chưa có (bắt đầu từ 0)
        if (countersCollection.find(Filters.eq("_id", "userId")).first() == null) {
            countersCollection.insertOne(new Document("_id", "userId").append("seq", 0));
        }

        // Khởi tạo bộ đếm ID cho PricePrediction nếu chưa có (bắt đầu từ 0)
        if (countersCollection.find(Filters.eq("_id", "predictionId")).first() == null) {
            countersCollection.insertOne(new Document("_id", "predictionId").append("seq", 0));
        }
    }
    @Override
    public User findUserByEmail(String email) {
        return userCollection.find(Filters.eq("email", email)).first();
    }

    @Override
    public void saveUser(User user) {
        userCollection.insertOne(user);
    }

    @Override
    public int getNextUserId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "userId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return counter.getInteger("seq");
    }

    @Override
    public void savePrediction(PricePrediction prediction) {
        predictionCollection.insertOne(prediction);
    }
    @Override
    public int getNextPredictionId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "predictionId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER).upsert(true));
        return counter.getInteger("seq");
    }

    @Override
    public List<PricePrediction> getPredictionsByUserId(int userId) {
        List<PricePrediction> list = new ArrayList<>();
        predictionCollection.find(Filters.eq("userId", userId)).into(list);
        return list;
    }
}