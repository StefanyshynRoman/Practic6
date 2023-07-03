package com.shpp.rstefanyshyn.strategy;

import com.mongodb.client.MongoDatabase;

public interface FileReaderStrategy {
    void read(String file, String nameDocument, String indexName, MongoDatabase database);

}

