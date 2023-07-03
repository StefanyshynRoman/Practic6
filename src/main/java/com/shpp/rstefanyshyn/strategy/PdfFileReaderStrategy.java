package com.shpp.rstefanyshyn.strategy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PdfFileReaderStrategy implements FileReaderStrategy {
    private final Logger logger = LoggerFactory.getLogger(PdfFileReaderStrategy.class);

    @Override
    public void read(String file, String nameDocument, String indexName, MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection(nameDocument);
        collection.drop();
        InputStream inputStream = PdfFileReaderStrategy.class.getClassLoader().getResourceAsStream(file);
        assert inputStream != null;
        logger.error("PdfFileReaderStrategy {}",file);
        try (PDDocument pdddocument = PDDocument.load(new File
                ("C:\\Users\\roman\\IdeaProjects\\Practic6\\src\\main\\resources\\"+file))) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfContent = textStripper.getText(pdddocument);

            String[] lines = pdfContent.split("\\r?\\n");
            for (String line : lines) {
                String[] values = line.split("\\s+"); // Assuming values are separated by spaces
                Document document =null;
                for (int i = 0; i < values.length; i++) {
                    document = new Document(indexName, values[i]);
                    collection.insertOne(document);                }
              //  collection.insertOne(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
