package com.example.coursework;

public class ReadyProduct {
    private int productNumber;
    private int productId;
    private String anyDefects;

    public ReadyProduct(int productNumber, int productId, String anyDefects) {
        this.productNumber = productNumber;
        this.productId = productId;
        this.anyDefects = anyDefects;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getAnyDefects() {
        return anyDefects;
    }

    public void setAnyDefects(String anyDefects) {
        this.anyDefects = anyDefects;
    }

    @Override
    public String toString() {
        return "ReadyProduct{" +
                "productNumber=" + productNumber +
                ", productId=" + productId +
                ", anyDefects='" + anyDefects + '\'' +
                '}';
    }
}