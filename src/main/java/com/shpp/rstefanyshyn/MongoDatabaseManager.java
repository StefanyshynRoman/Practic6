package com.shpp.rstefanyshyn;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDatabaseManager implements Constant {
    private static MongoDatabase database;
    private final Logger logger = LoggerFactory.getLogger(MongoDatabaseManager.class);
    private static MongoClient mongoClient;
    private MongoDatabaseManager() {
        database =  mongoClient.getDatabase(DATABASE_NAME);
    }
    public MongoDatabaseManager( MongoClient mongoClient) {
        this.mongoClient = mongoClient;

    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            synchronized (MongoDatabaseManager.class) {
                if (database == null) {
                new MongoDatabaseManager();
                }
            }
        }
        return database;
    }
}
