package org.yourcompany.yourproject.Config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.yourcompany.yourproject.Entity.User;
import org.bson.Document;

public class UserDataService {

    private MongoCollection<User> userCollection;
    private MongoCollection<Document> countersCollection;

    public UserDataService() {
        this.userCollection = MongoConfig.getUserCollection();
        this.countersCollection = MongoConfig.getCountersCollection();


        if (countersCollection.find(Filters.eq("_id", "userId")).first() == null) {
            countersCollection.insertOne(new Document("_id", "userId").append("seq", 0));
        }
    }

    /**
     * 
     * @param email Email cần tìm.
     * @return Đối tượng User nếu tìm thấy, ngược lại trả về null.
     */
    public User findUserByEmail(String email) {
        // Tương đương với: SELECT * FROM users WHERE email = ? LIMIT 1
        return userCollection.find(Filters.eq("email", email)).first();
    }

    /**
     * Lưu một user mới vào database.
     * 
     * @param user Đối tượng User cần lưu.
     */
    public void saveUser(User user) {
        userCollection.insertOne(user);
    }

    /**
     * Lấy ID tiếp theo cho user, đảm bảo không trùng lặp.
     * 
     * @return Một số int ID tiếp theo.
     */
    public int getNextUserId() {
        Document counter = countersCollection.findOneAndUpdate(
                Filters.eq("_id", "userId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return counter.getInteger("seq");
    }

}