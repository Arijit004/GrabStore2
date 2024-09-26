package com.storeservice.dto;

//this class will be used while updating product details as vendor can't update all fields of a product...
public class ProductDto {
    //fields...
    private String prod_name;
    private String prod_category;
    private int prod_qty;
    private double prod_price;
    private int prod_dis_rate;
    private double prod_dis_price;

    //default...
    public ProductDto() {
    }

    //parameterized...
    public ProductDto(String prod_name, String prod_category, int prod_qty, double prod_price, int prod_dis_rate, double prod_dis_price) {
        this.prod_name = prod_name;
        this.prod_category = prod_category;
        this.prod_qty = prod_qty;
        this.prod_price = prod_price;
        this.prod_dis_rate = prod_dis_rate;
        this.prod_dis_price = prod_dis_price;
    }

    //getters and setters...
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
}
