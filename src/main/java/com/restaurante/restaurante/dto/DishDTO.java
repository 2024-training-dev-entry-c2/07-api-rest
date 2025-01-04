package com.restaurante.restaurante.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {

    private String name;
    private Double price;
    private Long menuId;
    private String dishType;

}
