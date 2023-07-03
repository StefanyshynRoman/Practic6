package com.shpp.rstefanyshyn;

import com.mongodb.client.MongoDatabase;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductGenerator implements Constant {
    private static final Logger logger = LoggerFactory.getLogger(ProductGenerator.class);
    static Random random = new Random();
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    MongoDatabase database;



    public ProductGenerator(MongoDatabase database) {
        this.database = database;

    }

    public void createProductStream() {
        int numberOfProducts = Integer.parseInt(NUMBER_PRODUCTS);
        StopWatch stopWatch = new StopWatch();
        stopWatch.restart();
        AtomicInteger generatedProducts = new AtomicInteger(0);
        AtomicInteger validProducts = new AtomicInteger(0);

        MongoCollection<Document> typeCollection = database.getCollection("Type");
        MongoCollection<Document> storeCollection = database.getCollection("Store");
        MongoCollection<Document> productsCollection = database.getCollection("Products");
        productsCollection.drop();

        List<String> types = new ArrayList<>();
        for (Document typeDoc : typeCollection.find()) {
            String type = typeDoc.getString("type");
            types.add(type);
        }
        List<String> stores = new ArrayList<>();
        for (Document storeDoc : storeCollection.find()) {
            String store = storeDoc.getString("address");
            stores.add(store);
        }
        //List<Document> documentArrayList = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
            String randomType = types.get(random.nextInt(types.size()));
            String randomStore = stores.get(random.nextInt(stores.size()));

            Product product = new Product(generateRandomProductName());
            if (validator.validate(product).isEmpty()) {
                Document document = new Document("name", product.getProductName())
                        .append("type", randomType.toLowerCase())
                        .append("address", randomStore)
                        .append("quantity",random.nextInt(100));
              //  documentArrayList.add(document);
                productsCollection.insertOne(document);
                generatedProducts.incrementAndGet();
                validProducts.incrementAndGet();
                if(i%10000==0){
                    logger.info("Products added: {}", i);
                }
            } else {
                generatedProducts.incrementAndGet();
                numberOfProducts++;
            }

        }

        stopWatch.stop();
        numberOfProducts = generatedProducts.get();
        logger.info("Generation products: {}", numberOfProducts);
        logger.info("Generation products is over: seconds - {}", stopWatch.taken() / THOUSAND_TO_TIME);
        logger.warn("RPS - {}, ",THOUSAND_TO_TIME * numberOfProducts / stopWatch.taken());
        logger.warn("All product: {}, Product valid: {}, Product invalid: {}" , numberOfProducts, validProducts.get(), numberOfProducts - validProducts.get());



    }

    public String generateRandomProductName() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int randomLimit = 5;
        Random randomProductName = new Random();
        int targetStringLength = randomProductName.nextInt(randomLimit) + randomLimit;

        return randomProductName.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}