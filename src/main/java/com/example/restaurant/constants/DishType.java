package com.example.restaurant.constants;


public enum DishType {
    COMMON("Comun"),
    POPULAR("Popular");

    private final String name;

    DishType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
