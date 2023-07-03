package com.shpp.rstefanyshyn;
import jakarta.validation.constraints.*;

public class Product {
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @Pattern(regexp = "^(?!.*a).*$")
    String productName;

    private boolean valid = true;


        public Product(String name) {
        this.productName = name;

    }


    @Override
    public String toString() {
        return "Product{" +
                "name: " + '"' + productName + '"' +
                '}';

    }
}