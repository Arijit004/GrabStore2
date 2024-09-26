package com.payment.dto;

//product object...
//here we'll receive all the product info from store service...
public class Product {
    //fields...
    private int prod_id;
    private String prod_name;
    private String prod_category;
    private int prod_qty;
    private double prod_price;
    private int prod_dis_rate;
    private double prod_dis_price;
    private int store_id;

    //default constructor...
    public Product() {
    }

    //parameterized constructor...
    public Product(int prod_id, String prod_name, String prod_category, int prod_qty, double prod_price, int prod_dis_rate, double prod_dis_price, int store_id) {
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_category=prod_category;
        this.prod_qty = prod_qty;
        this.prod_price = prod_price;
        this.prod_dis_rate = prod_dis_rate;
        this.prod_dis_price = prod_dis_price;
        this.store_id=store_id;
    }

    //getters and setters...
    public int getProd_id() {
        return prod_id;
    }
    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }
    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_category() {
        return prod_category;
    }
    public void setProd_category(String prod_category) {
        this.prod_category = prod_category;
    }

    public int getProd_qty() {
        return prod_qty;
    }
    public void setProd_qty(int prod_qty) {
        this.prod_qty = prod_qty;
    }

    public double getProd_price() {
        return prod_price;
    }
    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_dis_rate() {
        return prod_dis_rate;
    }
    public void setProd_dis_rate(int prod_dis_rate) {
        this.prod_dis_rate = prod_dis_rate;
    }

    public double getProd_dis_price() {
        return prod_dis_price;
    }
    public void setProd_dis_price(double prod_dis_price) {
        this.prod_dis_price = prod_dis_price;
    }

    public int getStore_id() {
        return store_id;
    }
    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "prod_id=" + prod_id +
                ", prod_name='" + prod_name + '\'' +
                ", prod_category=" + prod_category +
                ", prod_qty=" + prod_qty +
                ", prod_price=" + prod_price +
                ", prod_dis_rate=" + prod_dis_rate +
                ", prod_dis_price=" + prod_dis_price +
                ", store_id=" + store_id +
                '}';
    }
}

