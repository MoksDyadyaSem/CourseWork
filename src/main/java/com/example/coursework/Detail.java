package com.example.coursework;

public class Detail {
    private int id;
    private String name;
    private double price;
    private double weight;
    private String dimension;
    private String material;

    public Detail(int id, String name, double price, double weight, String dimension, String material) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.dimension = dimension;
        this.material = material;
    }

    public Detail(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Detail(int id) {
        this.id = id;
    }
// Геттеры и сеттеры для всех полей

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Название: " + name + ", Цена: " + price + ", Вес: " + weight + ", Размеры: " + dimension + ", Материал: " + material;
    }
}