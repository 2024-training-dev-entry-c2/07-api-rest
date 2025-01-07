package com.training.restaurant.models;

public enum CustomerType {

    COMMON("comun"),
    FREQUENT("frecuente");

    private final String clientType;

    CustomerType(String type){
        this.clientType = type;
    }

    public static CustomerType fromString(String value){

        for (CustomerType type: CustomerType.values()){
            if(type.typeToString().equalsIgnoreCase(value)){
                return type;
            }
        }

        throw new IllegalArgumentException("Client type not found");
    }
    public String typeToString() {
        return clientType;
    }
}
