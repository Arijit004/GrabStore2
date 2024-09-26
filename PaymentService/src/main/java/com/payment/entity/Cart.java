package com.payment.entity;

import com.payment.dto.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//cart entity...
@Document
public class Cart {
    //id...
    @Id
    private long cartId;
    //cart name...
    private String cartName;
    //user id...
    private int userId;
    //list of products...
    List<Product> allProducts=new ArrayList<>();
    //total price...
    private double totalPrice;

    //getters and setters...
    public long getCartId() {
        return cartId;
    }
    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getCartName() {
        return cartName;
    }
    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }
    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
