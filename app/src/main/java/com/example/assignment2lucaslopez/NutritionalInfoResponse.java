package com.example.assignment2lucaslopez;

import java.util.List;

public class NutritionalInfoResponse {
    public int id;
    public String name;
    public String image;
    public Nutrition nutrition;

    public static class Nutrition {
        public List<Nutrient> nutrients;

        public static class Nutrient {
            public String name;
            public float amount;
        }
    }
}
