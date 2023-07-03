package com.shpp.rstefanyshyn;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.activemq.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 Generation products: 335912
11.06.2023 18:29:35 [main] INFO  c.s.r.ProductGenerator Generation products is over: seconds - 356.995
11.06.2023 18:35:43 [main] INFO  c.s.r.ProductGenerator Generation products: 336056
11.06.2023 18:35:43 [main] INFO  c.s.r.ProductGenerator Generation products is over: seconds - 299.885
11.06.2023 18:35:43 [main] WARN  c.s.r.ProductGenerator RPS - 1120.6162362238858,

 */

public class App implements Constant {

   // static BDConnection bdConnection = new BDConnection();
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    static StopWatch stopWatch = new StopWatch();
    static ProductGenerator productGenerator;

    private static MongoDatabase database;
    private static MongoClient mongoClient;
    public static void main(String[] args) {

      BDConnection bdConnection = new BDConnection();
      bdConnection.connect();
        MongoDatabaseManager mongoDatabaseManager=new MongoDatabaseManager(bdConnection.connect());
        MongoDatabaseManager mongoDatabaseManager1=new MongoDatabaseManager(bdConnection.connect());
        MongoDatabase mongoDatabase=MongoDatabaseManager.getDatabase();
        logger.info("MongoBase{} ",mongoDatabase);
        MongoDatabase mongoDatabase1=MongoDatabaseManager.getDatabase();
        logger.info("MongoBase1 {} ",mongoDatabase1);
        MongoDatabase mongoDatabase2=MongoDatabaseManager.getDatabase();
        logger.info("MongoBase2 {} ",mongoDatabase2);
        MongoDatabase mongoDatabase3=mongoDatabaseManager1.getDatabase();
        logger.info("MongoBase3 {} ",mongoDatabase3);
        MongoDatabase mongoDatabase4=mongoDatabaseManager.getDatabase();
        logger.info("MongoBase4 {} ",mongoDatabase4);
        MongoDatabase mongoDatabase5=mongoDatabaseManager.getDatabase();
        logger.info("MongoBase5 {} ",mongoDatabase5.hashCode());
        MongoDatabase mongoDatabase6=mongoDatabaseManager.getDatabase();
        logger.info("MongoBase6 {} ",mongoDatabase6.hashCode());
        TableGen tableGen = new TableGen(mongoDatabase1);
        stopWatch.restart();
        tableGen.reader(FILE_STORE, "Store", "address");
        tableGen.reader(FILE_TYPE, "Type", "type");
        stopWatch.stop();
       logger.info("Time (second) of create table store and type {} ", (stopWatch.taken()) / THOUSAND_TO_TIME);
        String productType = System.getProperty("type", "test").toLowerCase();
        productGenerator =
                new ProductGenerator(mongoDatabase1);
        productGenerator.createProductStream();

        stopWatch.restart();
        FindProduct findProduct = new FindProduct(mongoDatabase1);
        findProduct.find(productType);
        stopWatch.stop();
        logger.warn("Time (second) of find {} ", (stopWatch.taken()) / THOUSAND_TO_TIME);

      bdConnection.disconnect();
    }




}
