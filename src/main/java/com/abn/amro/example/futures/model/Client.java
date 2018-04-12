package com.abn.amro.example.futures.model;

import java.util.HashMap;
import java.util.Map;

public class Client {

    private String clientId;

    private Map<String,Integer> products;

    public Client(String clientId){
        this.clientId = clientId;
        products = new HashMap<String,Integer>();
    }

    public String getClientId() {
        return clientId;
    }


    public Map<String, Integer> getProducts() {
        return products;
    }

    public void addProductWithQuantity(String product, int quantity){
        if(products.containsKey(product)){
            quantity = products.get(product) + quantity;

        }
        products.put(product,quantity);
    }
}
