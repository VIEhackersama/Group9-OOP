package org.yourcompany.yourproject.Config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.yourcompany.yourproject.Entity.User;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.InputStream;
import java.util.Properties;
import org.yourcompany.yourproject.App;
public class MongoConfig {

    private static final String PROPERTIES_FILE = "db.properties";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Khối static này sẽ chạy một lần khi class được load
    // để thiết lập kết nối
    static {
        try (InputStream input = App.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Couldn't find the properties");
            }
            prop.load(input);
            String uri = prop.getProperty("mongodb.uri");
            String dbName = prop.getProperty("mongodb.database");

            // --- Cấu hình quan trọng cho POJO (Plain Old Java Object) ---
            // Điều này cho phép driver tự động map class User của bạn
            // với các document trong MongoDB
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    pojoCodecRegistry);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(uri))
                    .codecRegistry(codecRegistry) 
                    .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(dbName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB error", e);
        }
    }

    // Lấy collection 'users' và tự động map với class User
    public static MongoCollection<User> getUserCollection() {
        return database.getCollection("users", User.class);
    }

    public static MongoCollection<org.bson.Document> getCountersCollection() {
        return database.getCollection("counters");
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}