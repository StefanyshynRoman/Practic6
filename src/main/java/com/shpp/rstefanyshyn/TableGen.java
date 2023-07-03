package com.shpp.rstefanyshyn;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shpp.rstefanyshyn.strategy.FileReaderStrategy;
import com.shpp.rstefanyshyn.strategy.FileReaderStrategyFactory;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class TableGen implements Constant {
    private final Logger logger = LoggerFactory.getLogger(TableGen.class);
    private MongoDatabase database;

    public TableGen(MongoDatabase database) {
        this.database = database;
    }

    public void reader(String file, String nameDocument, String indexName) {
        FileReaderStrategy readerStrategy = FileReaderStrategyFactory.fabricMethodCreateReaderStrategy(file);

        MongoCollection<Document> collection = database.getCollection(nameDocument);
        collection.drop();

        //InputStream inputStream = new FileInputStream(file);
     //   InputStream inputStream = TableGen.class.getClassLoader().getResourceAsStream(file);
        logger.info("Read from {}", file);
        readerStrategy.read(file, nameDocument, indexName,database);
    }
}