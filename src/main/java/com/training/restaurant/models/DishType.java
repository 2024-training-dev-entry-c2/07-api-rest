package com.training.restaurant.models;

public enum DishType {
    COMMON("comun"),
    POPULAR("popular");

    private final String dishType;

    DishType(String type){
        this.dishType = type;
    }

    public static DishType fromString(String value){

        for (DishType type: DishType.values()){
            if(type.typeToString().equalsIgnoreCase(value)){
                return type;
            }
        }

        throw new IllegalArgumentException("Dish type not found");
    }
    public String typeToString() {
        return dishType;
    }
}
