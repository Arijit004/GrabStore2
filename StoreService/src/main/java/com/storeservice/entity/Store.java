package com.storeservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Store {
    //fields...
    //declaring primary key...
    @Id
    //it'll generate automatically if we don't provide any value as id...
    private int store_id;
    private String store_name;
    //user_id will be stored as vendor_id...
    //those users who have the role of "vendor" can perform operations related to store...
    private int vendor_id;
    private List<Product> allProducts;

    //default constructor...
    public Store() {
    }

    //parameterized constructor...
    public Store(int store_id, String store_name, int vendor_id, List<Product> allProducts) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.vendor_id=vendor_id;
        this.allProducts = allProducts;
    }

    //getters and setters...
    public int getStore_id() {
        return store_id;
    }
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getVendor_id() {
        return vendor_id;
    }
    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }
    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }
}
