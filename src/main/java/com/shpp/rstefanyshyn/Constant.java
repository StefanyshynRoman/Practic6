package com.shpp.rstefanyshyn;


public interface Constant {
    GetProperty PROPERTY = new GetProperty("config.properties");
    GetProperty PROPERTY_CONNECTION = new GetProperty("connection.properties");

    String NUMBER_PRODUCTS = PROPERTY.getValueFromProperty("maximum");
    String FILE_STORE=PROPERTY.getValueFromProperty("file_store");;
    String FILE_TYPE= PROPERTY.getValueFromProperty("file_type");

    String URL_MONGO =PROPERTY_CONNECTION.getValueFromProperty("url");
   String DATABASE_NAME="inventory_storage5";

    double THOUSAND_TO_TIME =1000;

}
