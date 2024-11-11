package com.example.coursework;

public class Product {
    private int id;
    private String name;
    private String description;
    private double weight;
    private String dimension;
    private String material;

    // Конструктор
    public Product(int id, String name, String description, double weight, String dimension, String material) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.dimension = dimension;
        this.material = material;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Product(int id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Weight: " + weight + ", Dimension: " + dimension + ", Material: " + material;
    }
}