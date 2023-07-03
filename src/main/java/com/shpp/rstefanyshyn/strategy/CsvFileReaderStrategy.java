package com.shpp.rstefanyshyn.strategy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvFileReaderStrategy implements FileReaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(CsvFileReaderStrategy.class);

    @Override
    public void read(String file, String nameDocument, String indexName, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection(nameDocument);
        collection.drop();

        InputStream inputStream = CsvFileReaderStrategy.class.getClassLoader().getResourceAsStream(file);
        assert inputStream != null;
        logger.error("CsvFileReaderStrategy{}", file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Document document = new Document(indexName, line);
                collection.insertOne(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}