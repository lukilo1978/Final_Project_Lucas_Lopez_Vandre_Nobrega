package com.example.assignment2lucaslopez;

public class FoodItem {
    private String name, imageUrl;
    private double calories, carbs, proteins, fats;

    public FoodItem(String name, String imageUrl, double calories, double carbs, double proteins, double fats) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
    }

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public double getCalories() { return calories; }
    public double getCarbs() { return carbs; }
    public double getProteins() { return proteins; }
    public double getFats() { return fats; }
}