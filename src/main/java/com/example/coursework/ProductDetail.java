package com.example.coursework;

public class ProductDetail {
    private Product product;
    private Detail detail;
    private int quantity;

    // Конструктор с 3 параметрами
    public ProductDetail(Product product, Detail detail, int quantity) {
        this.product = product;
        this.detail = detail;
        this.quantity = quantity;
    }

    // Пустой конструктор
    public ProductDetail() {}

    public int getProductId() {
        return product != null ? product.getId() : 0;
    }

    public int getDetailId() {
        return detail != null ? detail.getId() : 0;
    }




    // Геттеры и сеттеры
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Detail getDetail() { return detail; }
    public void setDetail(Detail detail) { this.detail = detail; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}