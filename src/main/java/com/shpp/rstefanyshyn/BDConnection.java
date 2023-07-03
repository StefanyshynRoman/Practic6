package com.shpp.rstefanyshyn;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BDConnection implements Constant {
    private final Logger logger = LoggerFactory.getLogger(BDConnection.class);
    private MongoClient mongoClient;



    public MongoClient connect()  {
        if (mongoClient == null) {
        mongoClient = MongoClients.create(URL_MONGO);
        logger.warn("Connect to mongoClient {} ", mongoClient);
        }
        return mongoClient;
    }

    public void disconnect() {

        synchronized (BDConnection.class) {
            if (mongoClient != null) {
                mongoClient.close();
                logger.info("Disconnected from the MongoDB database.");
            }
        }
    }

}