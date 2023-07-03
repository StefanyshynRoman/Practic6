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

public class TxtFileReaderStrategy implements FileReaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(FileReaderStrategy.class);

    @Override
    public void read(String file, String nameDocument, String indexName, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection(nameDocument);
        collection.drop();
        InputStream inputStream = TxtFileReaderStrategy.class.getClassLoader().getResourceAsStream(file);
        assert inputStream != null;
logger.error("TxtFileReaderStrategy {}",file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+");
//                Document document = new Document(indexName, values[0]); // Assuming the first value is the index value
                Document document = null;
                for (int i = 0; i < values.length; i++) {
                    // document.append("column" + i, values[i]); // Storing each value in a separate column
                    document = new Document(indexName, values[i]);
                    collection.insertOne(document);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
